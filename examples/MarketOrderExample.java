import trade.paradex.ParadexClient;
import trade.paradex.api.dto.OrderSide;
import trade.paradex.api.dto.OrderType;
import trade.paradex.api.dto.ParadexOrderDTO;
import trade.paradex.api.dto.request.ParadexCreateOrderRequestDTO;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;

public class MarketOrderExample {

    private static final String ACCOUNT_ADDRESS = "...";     // Paradex account address
    private static final String ACCOUNT_PRIVATE_KEY = "..."; // Paradex account private key

    public static void main(String[] args) {
        ParadexAccount account = new ParadexAccount(ACCOUNT_ADDRESS, ACCOUNT_PRIVATE_KEY);

        ParadexClient client = ParadexClient.builder(ParadexEnvironment.TESTNET) // or ParadexEnvironment.MAINNET
                .build();

        ParadexCreateOrderRequestDTO createLimitOrderRequest = ParadexCreateOrderRequestDTO.builder()
                .market("BTC-USD-PERP")
                .orderType(OrderType.MARKET)
                .size(0.2)
                .orderSide(OrderSide.SELL)
                .build();
        ParadexOrderDTO limitOrder = client.orderAPI().createOrder(account, createLimitOrderRequest);
        System.out.println(limitOrder);
    }
}
