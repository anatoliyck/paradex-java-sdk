package trade.paradex.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import trade.paradex.BaseIntegrationTest;
import trade.paradex.api.dto.ParadexLiquidationDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.Pair;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LiquidationAPIIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Call `/v1/liquidations`")
    public void testCallGetLiquidationList() {
        Pair<HttpRequest, String> mockAuthData = mockAuthAPI();

        ParadexLiquidationDTO liquidation = ParadexLiquidationDTO.builder()
                .id("testId")
                .createdAt(21931283128322L)
                .build();

        HttpRequest httpRequest = HttpRequest.request()
                .withHeader("Authorization", "Bearer " + mockAuthData.getRight())
                .withMethod("GET")
                .withPath("/v1/liquidations")
                .withQueryStringParameter("from", "11111111333333")
                .withQueryStringParameter("to", "33333333333333");
        MOCK_SERVER_CLIENT.when(httpRequest)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody(JsonUtils.serialize(new ParadexResultsResponseDTO<>(List.of(liquidation))))
                );

        ParadexResultsResponseDTO<ParadexLiquidationDTO> liquidations = PARADEX_CLIENT.liquidationAPI().getLiquidations(PARADEX_TEST_ACCOUNT, 11111111333333L, 33333333333333L);
        assertEquals(List.of(liquidation), liquidations.getResults());

        MOCK_SERVER_CLIENT.verify(mockAuthData.getLeft(), VerificationTimes.once());
        MOCK_SERVER_CLIENT.verify(httpRequest, VerificationTimes.once());
    }
}
