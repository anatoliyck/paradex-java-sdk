import trade.paradex.ParadexClient;
import trade.paradex.api.dto.OrderSide;
import trade.paradex.api.dto.OrderType;
import trade.paradex.api.dto.ParadexMarketSummaryDTO;
import trade.paradex.api.dto.ParadexOrderDTO;
import trade.paradex.api.dto.request.ParadexCreateOrderRequestDTO;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CancelOrderExample {

    private static final String ACCOUNT_ADDRESS = "...";     // Paradex account address
    private static final String ACCOUNT_PRIVATE_KEY = "..."; // Paradex account private key

    public static void main(String[] args) {
        ParadexAccount account = new ParadexAccount(ACCOUNT_ADDRESS, ACCOUNT_PRIVATE_KEY);

        ParadexClient client = ParadexClient.builder(ParadexEnvironment.TESTNET) // or ParadexEnvironment.MAINNET
                .build();

        List<ParadexMarketSummaryDTO> markets = client.marketAPI().getMarketSummary("BTC-USD-PERP", null, null).getResults();
        if (markets.size() != 1) {
            throw new IllegalArgumentException("Expected exactly one market but result: " + markets.size());
        }

        ParadexMarketSummaryDTO btcMarketSummary = markets.get(0);
        double currentPrice = btcMarketSummary.getMarkPrice().doubleValue();

        double orderPrice = BigDecimal.valueOf(currentPrice)  // -5% from current price
                .multiply(BigDecimal.valueOf(0.95))
                .setScale(1, RoundingMode.HALF_DOWN)
                .doubleValue();
        ParadexCreateOrderRequestDTO createLimitOrderRequest = ParadexCreateOrderRequestDTO.builder()
                .market("BTC-USD-PERP")
                .orderType(OrderType.LIMIT)
                .size(0.25)
                .price(orderPrice)
                .orderSide(OrderSide.BUY)
                .build();
        ParadexOrderDTO limitOrder = client.orderAPI().createOrder(account, createLimitOrderRequest);
        System.out.println(limitOrder);

        client.orderAPI().cancelOrder(account, limitOrder.getId());
    }
}
