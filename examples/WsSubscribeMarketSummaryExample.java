import com.fasterxml.jackson.databind.JsonNode;
import trade.paradex.ParadexClient;
import trade.paradex.api.dto.ParadexMarketSummaryDTO;
import trade.paradex.model.ParadexEnvironment;
import trade.paradex.utils.JsonUtils;
import trade.paradex.ws.ParadexSubscriptionListener;
import trade.paradex.ws.ParadexWebsocketClient;

public class WsSubscribeMarketSummaryExample {

    public static void main(String[] args) throws Exception {
        ParadexEnvironment environment = ParadexEnvironment.TESTNET; // or ParadexEnvironment.MAINNET
        ParadexClient client = ParadexClient.builder(environment)
                .build();

        ParadexWebsocketClient websocketClient = new ParadexWebsocketClient(environment.getWsUrl() + "/v1", null, client);

        ParadexSubscriptionListener subscriptionListener = new MarketSummarySubscriptionListener();
        websocketClient.subscribe("markets_summary", subscriptionListener);
        websocketClient.connectBlocking();

        Thread.sleep(10_000); // await 10 seconds

        websocketClient.shutdown(); // close ws connection and shutdown the client
    }

    private static class MarketSummarySubscriptionListener implements ParadexSubscriptionListener {

        @Override
        public void onMessage(String channel, JsonNode data) {
            System.out.println("Channel: " + channel);

            String rawJson = data.toString(); // to get raw json if needed
            System.out.println(rawJson);

            String symbol = data.get("symbol").asText(); // get some json field if needed
            System.out.println(symbol);

            ParadexMarketSummaryDTO marketSummaryDTO = JsonUtils.treeToValue(data, ParadexMarketSummaryDTO.class); // deserialize to object
            System.out.println(marketSummaryDTO);
        }
    }

}
