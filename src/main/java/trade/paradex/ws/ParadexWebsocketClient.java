package trade.paradex.ws;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;
import trade.paradex.ParadexClient;
import trade.paradex.model.ParadexAccount;
import trade.paradex.utils.JsonUtils;
import trade.paradex.ws.dto.WebsocketParadexMessageDTO;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@EqualsAndHashCode(of = "account", callSuper = false)
public class ParadexWebsocketClient extends WebSocketClient {

    @Getter
    private final ParadexAccount account;
    private final ParadexClient paradexClient;

    private final AtomicInteger subscriptionId = new AtomicInteger();
    private final Map<String, ParadexSubscriptionListener> subscriptions = new ConcurrentHashMap<>();

    private final AtomicBoolean closed = new AtomicBoolean();

    private final Lock subscriptionLock = new ReentrantLock();
    private final Lock reconnectLock = new ReentrantLock();

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ParadexWebsocketClient(String wsUrl,
                                  ParadexAccount account,
                                  ParadexClient paradexClient) {
        super(URI.create(wsUrl));
        this.account = account;
        this.paradexClient = paradexClient;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.trace("Websocket is connected.");
        if (account != null) {
            WebsocketParadexMessageDTO authMessage = WebsocketParadexMessageDTO.builder()
                    .id(subscriptionId.getAndIncrement())
                    .method("auth")
                    .params(Map.of("bearer", paradexClient.authAPI().auth(account)))
                    .build();

            send(JsonUtils.serialize(authMessage));
        }

        if (!subscriptions.isEmpty()) {
            subscriptions.forEach(this::subscribe);
        }
    }

    @Override
    public void onMessage(String message) {
        log.trace("Received message: {}", message);

        JsonNode jsonNode = JsonUtils.readTree(message);

        JsonNode errorNode = jsonNode.get("error");
        if (errorNode != null && !errorNode.isNull()) {
            String accountAddress = account != null ? account.getAddress() : null;
            log.error("Account: {} received error: {}", accountAddress, errorNode);
            return;
        }

        JsonNode methodNode = jsonNode.get("method");
        if (methodNode != null && !methodNode.isNull()
                && Objects.equals(methodNode.asText(), "subscription")) {
            JsonNode paramsNode = jsonNode.get("params");

            String channel = paramsNode.get("channel").asText();
            ParadexSubscriptionListener listener = subscriptions.get(channel);
            if (listener != null) {
                JsonNode dataNode = paramsNode.get("data");
                listener.onMessage(dataNode);
            } else {
                log.error("Listener not found by channel: {}, message: {}", channel, message);
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        String accountAddress = account != null ? account.getAddress() : null;
        log.warn("Closed connection for account: {}, code: {}, reason: {}, remote: {}", accountAddress, code, reason, remote);

        if (!closed.get() && (code != CloseFrame.NORMAL || remote)) {
            scheduleReconnect();
        }
    }

    @Override
    public void onError(Exception ex) {
        log.error("Websocket client error", ex);
    }

    /**
     * Shutdown websocket client.
     * </p>
     * Default {@link WebSocket#close()} only closes websocket connection but this client uses auto reconnect mechanism.
     */
    @SneakyThrows
    public void shutdown() {
        log.info("Shutdown websocket client...");

        if (closed.compareAndSet(false, true)) {
            executorService.shutdown();
        }

        if (isOpen()) {
            close(CloseFrame.NORMAL);
        }
    }

    /**
     * Subscribes to given public/private channel(e.g. `markets_summary`, `orders.ALL` etc.).
     *
     * @param channel  channel name to subscribe
     * @param listener {@link ParadexSubscriptionListener} custom listener for specific channel
     * @return the same instance
     */
    public ParadexWebsocketClient subscribe(String channel, ParadexSubscriptionListener listener) {
        subscriptionLock.lock();
        try {
            subscribeInternal(channel, listener);
        } finally {
            subscriptionLock.unlock();
        }

        return this;
    }

    /**
     * Unsubscribes from the given public/private channel(e.g. `markets_summary`, `orders.ALL` etc.).
     *
     * @param channel channel name to unsubscribe
     * @return the same instance
     */
    public ParadexWebsocketClient unsubscribe(String channel) {
        subscriptionLock.lock();
        try {
            unsubscribeInternal(channel);
        } finally {
            subscriptionLock.unlock();
        }

        return this;
    }

    private void subscribeInternal(String channel, ParadexSubscriptionListener listener) {
        subscriptions.put(channel, listener);

        if (isOpen()) {
            WebsocketParadexMessageDTO subscribe = WebsocketParadexMessageDTO.builder()
                    .id(subscriptionId.getAndIncrement())
                    .method("subscribe")
                    .params(Map.of("channel", channel))
                    .build();

            send(JsonUtils.serialize(subscribe));
        }
    }

    private void unsubscribeInternal(String channel) {
        if (subscriptions.remove(channel) == null) {
            return;
        }

        if (isOpen()) {
            WebsocketParadexMessageDTO unsubscribe = WebsocketParadexMessageDTO.builder()
                    .id(subscriptionId.getAndIncrement())
                    .method("unsubscribe")
                    .params(Map.of("channel", channel))
                    .build();

            send(JsonUtils.serialize(unsubscribe));
        }
    }

    private void scheduleReconnect() {
        executorService.schedule(() -> {
            if (reconnectLock.tryLock()) {
                try {
                    log.info("Trying to reconnect websocket...");
                    reconnectBlocking();
                } catch (Exception ex) {
                    log.error("Unable to reconnect websocket. Error: {}", ex.getMessage());
                    scheduleReconnect();
                } finally {
                    reconnectLock.unlock();
                }
            }

        }, 100, TimeUnit.MILLISECONDS);
    }
}

