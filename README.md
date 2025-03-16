# paradex-java-sdk

SDK for Paradex API trading with Java.

See [Paradex API Reference](https://docs.paradex.trade/api-reference/general-information)

## Installation

### Building From Source

Build jar with following command

```shell
mvn clean install
```

### Maven Repository

TODO...

## Usage Examples

See more complex [examples](examples). You can also checkout the repo and run any of the [examples](examples).

### API

```java
import trade.paradex.ParadexClient;
import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexCreateOrderRequestDTO;
import trade.paradex.api.order.ParadexOrderAPI;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;

import java.util.List;

public class Example {

    private static final String ACCOUNT_ADDRESS = "...";     // Paradex account address
    private static final String ACCOUNT_PRIVATE_KEY = "..."; // Paradex account private key

    public static void main(String[] args) {
        ParadexAccount account = new ParadexAccount(ACCOUNT_ADDRESS, ACCOUNT_PRIVATE_KEY);

        ParadexClient client = ParadexClient.builder(ParadexEnvironment.TESTNET) // or ParadexEnvironment.MAINNET 
                .build();

        // Create market order
        ParadexCreateOrderRequestDTO createMarketOrderRequest = ParadexCreateOrderRequestDTO.builder()
                .market("BTC-USD-PERP")
                .orderType(OrderType.MARKET)
                .size(0.25)
                .orderSide(OrderSide.BUY)
                .build();
        ParadexOrderDTO marketOrder = client.orderAPI().createOrder(account, createMarketOrderRequest);
        System.out.println(marketOrder);

        // Create limit order
        ParadexCreateOrderRequestDTO createLimitOrderRequest = ParadexCreateOrderRequestDTO.builder()
                .market("BTC-USD-PERP")
                .orderType(OrderType.LIMIT)
                .size(0.25)
                .price(80000.0d)
                .orderSide(OrderSide.BUY)
                .build();
        ParadexOrderDTO limitOrder = client.orderAPI().createOrder(account, createLimitOrderRequest);
        System.out.println(limitOrder);

        // Get account positions
        ParadexResultsResponseDTO<ParadexPositionDTO> positions = client.accountAPI().getPositions(account);
        System.out.println(positions);
    }
}
```

### Websocket

```java
import com.fasterxml.jackson.databind.JsonNode;
import trade.paradex.ParadexClient;
import trade.paradex.api.dto.ParadexMarketSummaryDTO;
import trade.paradex.model.ParadexEnvironment;
import trade.paradex.utils.JsonUtils;
import trade.paradex.ws.ParadexSubscriptionListener;
import trade.paradex.ws.ParadexWebsocketClient;

public class WebsocketExample {

    public static void main(String[] args) throws Exception {
        ParadexEnvironment environment = ParadexEnvironment.TESTNET; // or ParadexEnvironment.MAINNET
        ParadexClient client = ParadexClient.builder(environment)
                .build();

        ParadexWebsocketClient websocketClient = new ParadexWebsocketClient(environment.getWsUrl() + "/v1", client);

        ParadexSubscriptionListener subscriptionListener = new ParadexSubscriptionListener() {
            @Override
            public void onMessage(String channel, JsonNode data) {
                System.out.println("Channel: " + channel + ", data: " + data); // print channel and data to console
            }
        };
        websocketClient.subscribe("markets_summary", subscriptionListener);
        websocketClient.connectBlocking();

        Thread.sleep(10_000); // await 10 seconds

        websocketClient.shutdown(); // close ws connection and shutdown the client
    }
}
```

## Supported API

| List of implemented API                                                                                  |
|----------------------------------------------------------------------------------------------------------|
| [Account API](src%2Fmain%2Fjava%2Ftrade%2Fparadex%2Fapi%2Faccount%2FParadexAccountAPI.java)              | 
| [Auth API](src%2Fmain%2Fjava%2Ftrade%2Fparadex%2Fapi%2Fauth%2FParadexAuthAPI.java)                       |
| [Market API](src%2Fmain%2Fjava%2Ftrade%2Fparadex%2Fapi%2Fmarket%2FParadexMarketAPI.java)                 |
| [Liquidations API](src%2Fmain%2Fjava%2Ftrade%2Fparadex%2Fapi%2Fliquidation%2FParadexLiquidationAPI.java) |
| [Order API](src%2Fmain%2Fjava%2Ftrade%2Fparadex%2Fapi%2Forder%2FParadexOrderAPI.java)                    |
| [Vault API](src%2Fmain%2Fjava%2Ftrade%2Fparadex%2Fapi%2Fvault%2FParadexVaultAPI.java)                    |
