import trade.paradex.ParadexClient;
import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexCreateOrderRequestDTO;
import trade.paradex.api.dto.request.ParadexModifyOrderRequestDTO;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ModifyOrderExample {

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

        double orderPrice = BigDecimal.valueOf(currentPrice)  // -3% from current price
                .multiply(BigDecimal.valueOf(0.97))
                .setScale(1, RoundingMode.HALF_DOWN)
                .doubleValue();
        ParadexCreateOrderRequestDTO createLimitOrderRequest = ParadexCreateOrderRequestDTO.builder()
                .market("BTC-USD-PERP")
                .orderType(OrderType.LIMIT)
                .size(0.2)
                .price(orderPrice)
                .orderSide(OrderSide.BUY)
                .build();
        ParadexOrderDTO limitOrder = client.orderAPI().createOrder(account, createLimitOrderRequest);
        System.out.println(limitOrder);

        double newOrderPrice = orderPrice - 1000d;
        double newOrderSize = 0.205;
        ParadexModifyOrderRequestDTO modifyOrderRequest = ParadexModifyOrderRequestDTO.builder()
                .id(limitOrder.getId())
                .market(limitOrder.getMarket())
                .orderType(OrderType.LIMIT)
                .size(newOrderSize)
                .price(newOrderPrice)
                .orderSide(OrderSide.BUY)
                .build();
        ParadexModifyOrderDTO modifyOrder = client.orderAPI().modifyOrder(account, modifyOrderRequest);
        System.out.println(modifyOrder);
    }
}
