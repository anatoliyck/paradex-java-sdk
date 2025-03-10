import com.fasterxml.jackson.databind.JsonNode;
import trade.paradex.ParadexClient;
import trade.paradex.api.dto.ParadexOrderDTO;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;
import trade.paradex.utils.JsonUtils;
import trade.paradex.ws.ParadexSubscriptionListener;
import trade.paradex.ws.ParadexWebsocketClient;

public class WsSubscribeAccountOrdersExample {

    private static final String ACCOUNT_ADDRESS = "...";     // Paradex account address
    private static final String ACCOUNT_PRIVATE_KEY = "..."; // Paradex account private key

    public static void main(String[] args) throws Exception {
        ParadexAccount account = new ParadexAccount(ACCOUNT_ADDRESS, ACCOUNT_PRIVATE_KEY);

        ParadexEnvironment environment = ParadexEnvironment.TESTNET; // or ParadexEnvironment.MAINNET
        ParadexClient client = ParadexClient.builder(environment)
                .build();

        ParadexWebsocketClient websocketClient = new ParadexWebsocketClient(environment.getWsUrl() + "/v1", account, client);

        OrdersSubscriptionListener subscriptionListener = new OrdersSubscriptionListener();
        websocketClient.subscribe("orders.ALL", subscriptionListener);
        websocketClient.connectBlocking();

        Thread.sleep(10_000); // await 10 seconds

        websocketClient.shutdown(); // close ws connection and shutdown the client
    }

    private static class OrdersSubscriptionListener implements ParadexSubscriptionListener {

        @Override
        public void onMessage(String channel, JsonNode data) {
            System.out.println("Channel: " + channel);

            String rawJson = data.toString(); // to get raw json if needed
            System.out.println(rawJson);

            String orderId = data.get("id").asText(); // get some json field if needed
            System.out.println(orderId);

            ParadexOrderDTO orderDTO = JsonUtils.treeToValue(data, ParadexOrderDTO.class); // deserialize to object
            System.out.println(orderDTO);
        }
    }

}
