package trade.paradex.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import trade.paradex.BaseIntegrationTest;
import trade.paradex.api.dto.OrderSide;
import trade.paradex.api.dto.ParadexOrderDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.utils.JsonUtils;

import java.util.List;

public class OrderAPIIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Get `/v1/orders` API")
    public void testGetOrders() {
        String orderId = "21838123812382138213";
        ParadexOrderDTO expectedOrder = ParadexOrderDTO.builder()
                .id(orderId)
                .account(PARADEX_TEST_ACCOUNT.getAddress())
                .flags(List.of("POST_ONLY"))
                .market("ETH-USD-PERP")
                .price(1500.55)
                .type("LIMIT")
                .side(OrderSide.BUY)
                .size(1.58)
                .remainingSize(0.23)
                .publishedAt(239123912932931L)
                .lastUpdatedAt(219312831283823L)
                .build();

        HttpRequest authHttpRequest = mockAuthAPI().getLeft();

        HttpRequest orderHttpRequest = HttpRequest.request()
                .withMethod("GET")
                .withPath("/v1/orders")
                .withQueryStringParameter("market", "ETH-USD-PERP");
        MOCK_SERVER_CLIENT.when(orderHttpRequest)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody(JsonUtils.serialize(new ParadexResultsResponseDTO<>(List.of(expectedOrder))))
                );

        ParadexResultsResponseDTO<ParadexOrderDTO> orders = PARADEX_CLIENT.orderAPI().getOrders(PARADEX_TEST_ACCOUNT, "ETH-USD-PERP");

        Assertions.assertEquals(List.of(expectedOrder), orders.getResults());

        MOCK_SERVER_CLIENT.verify(authHttpRequest, VerificationTimes.once());
        MOCK_SERVER_CLIENT.verify(orderHttpRequest, VerificationTimes.once());
    }

}
