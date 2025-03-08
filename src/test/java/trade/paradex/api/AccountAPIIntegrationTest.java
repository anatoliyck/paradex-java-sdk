package trade.paradex.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import trade.paradex.BaseIntegrationTest;
import trade.paradex.api.dto.ParadexAccountInfoDTO;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountAPIIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Call `/v1/account` for main account")
    public void testCallAccountInfo() {
        Pair<HttpRequest, String> mockAuthData = mockAuthAPI();

        ParadexAccountInfoDTO expectedAccountInfo = ParadexAccountInfoDTO.builder()
                .account(PARADEX_TEST_ACCOUNT.getAddress())
                .accountValue(1230.3205)
                .freeCollateral(329.323)
                .seqNo(481284821482822L)
                .initialMarginRequirement(504)
                .settlementAsset("USDC")
                .totalCollateral(30502.39)
                .build();

        HttpRequest httpRequest = HttpRequest.request()
                .withMethod("GET")
                .withPath("/v1/account");
        MOCK_SERVER_CLIENT.when(httpRequest)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody(JsonUtils.serialize(expectedAccountInfo))
                );

        ParadexAccountInfoDTO accountInfo = PARADEX_CLIENT.accountAPI().getAccountInfo(PARADEX_TEST_ACCOUNT, null);
        assertEquals(expectedAccountInfo, accountInfo);

        MOCK_SERVER_CLIENT.verify(mockAuthData.getLeft(), VerificationTimes.once());

        HttpRequest[] httpRequests = MOCK_SERVER_CLIENT.retrieveRecordedRequests(httpRequest);
        assertEquals(1, httpRequests.length);

        HttpRequest recordedRequest = httpRequests[0];
        assertEquals("Bearer " + mockAuthData.getRight(), recordedRequest.getFirstHeader("Authorization"));
    }

    @Test
    @DisplayName("Call `/v1/account` for sub account")
    public void testCallSubAccountInfo() {
        Pair<HttpRequest, String> mockAuthData = mockAuthAPI();

        String subAccountAddr = "0xTest";
        ParadexAccountInfoDTO expectedAccountInfo = ParadexAccountInfoDTO.builder()
                .account(subAccountAddr)
                .seqNo(11111111111111L)
                .settlementAsset("USDC")
                .build();

        HttpRequest httpRequest = HttpRequest.request()
                .withMethod("GET")
                .withPath("/v1/account")
                .withQueryStringParameter("subaccount_address", subAccountAddr);
        MOCK_SERVER_CLIENT.when(httpRequest)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody(JsonUtils.serialize(expectedAccountInfo))
                );

        ParadexAccountInfoDTO accountInfo = PARADEX_CLIENT.accountAPI().getAccountInfo(PARADEX_TEST_ACCOUNT, subAccountAddr);
        assertEquals(expectedAccountInfo, accountInfo);

        MOCK_SERVER_CLIENT.verify(mockAuthData.getLeft(), VerificationTimes.once());

        HttpRequest[] httpRequests = MOCK_SERVER_CLIENT.retrieveRecordedRequests(httpRequest);
        assertEquals(1, httpRequests.length);

        HttpRequest recordedRequest = httpRequests[0];
        assertEquals("Bearer " + mockAuthData.getRight(), recordedRequest.getFirstHeader("Authorization"));
        assertEquals(subAccountAddr, recordedRequest.getFirstQueryStringParameter("subaccount_address"));
    }
}
