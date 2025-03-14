package trade.paradex.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import trade.paradex.BaseIntegrationTest;
import trade.paradex.api.dto.MarginType;
import trade.paradex.api.dto.ParadexAccountInfoDTO;
import trade.paradex.api.dto.ParadexAccountMarginDTO;
import trade.paradex.api.dto.request.ParadexUpdateAccountMarginDTO;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.Pair;

import java.util.List;
import java.util.Map;

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
                .withHeader("Authorization", "Bearer " + mockAuthData.getRight())
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
        MOCK_SERVER_CLIENT.verify(httpRequest, VerificationTimes.once());
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
                .withHeader("Authorization", "Bearer " + mockAuthData.getRight())
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
        MOCK_SERVER_CLIENT.verify(httpRequest, VerificationTimes.once());
    }

    @Test
    @DisplayName("Call `/v1/account/margin` to get margin info")
    public void testCallGetAccountMargin() {
        Pair<HttpRequest, String> mockAuthData = mockAuthAPI();

        String market = "ETH-USD-PERP";
        ParadexAccountMarginDTO expectedAccountMargin = ParadexAccountMarginDTO.builder()
                .account(PARADEX_TEST_ACCOUNT.getAddress())
                .configs(List.of(
                        ParadexAccountMarginDTO.MarginConfigDTO.builder()
                                .market(market)
                                .leverage(15)
                                .marginType(MarginType.CROSS)
                                .build())
                )
                .build();

        HttpRequest httpRequest = HttpRequest.request()
                .withHeader("Authorization", "Bearer " + mockAuthData.getRight())
                .withMethod("GET")
                .withPath("/v1/account/margin")
                .withQueryStringParameter("market", market);
        MOCK_SERVER_CLIENT.when(httpRequest)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody(JsonUtils.serialize(expectedAccountMargin))
                );

        ParadexAccountMarginDTO accountMargin = PARADEX_CLIENT.accountAPI().getAccountMargin(PARADEX_TEST_ACCOUNT, market);
        assertEquals(expectedAccountMargin, accountMargin);

        MOCK_SERVER_CLIENT.verify(mockAuthData.getLeft(), VerificationTimes.once());
        MOCK_SERVER_CLIENT.verify(httpRequest, VerificationTimes.once());
    }

    @Test
    @DisplayName("Call `/v1/account/margin/{market}` to update margin")
    public void testCallUpdateAccountMargin() {
        Pair<HttpRequest, String> mockAuthData = mockAuthAPI();

        String market = "ETH-USD-PERP";
        ParadexAccountMarginDTO expectedAccountMargin = ParadexAccountMarginDTO.builder()
                .account(PARADEX_TEST_ACCOUNT.getAddress())
                .configs(List.of(
                        ParadexAccountMarginDTO.MarginConfigDTO.builder()
                                .market(market)
                                .leverage(17)
                                .marginType(MarginType.CROSS)
                                .build())
                )
                .build();

        var expectedRequestData = Map.of(
                "leverage", 17,
                "margin_type", MarginType.CROSS
        );
        var mockResponseData = Map.of(
                "account", PARADEX_TEST_ACCOUNT.getAddress(),
                "leverage", 17,
                "market", market,
                "margin_type", MarginType.CROSS
        );

        HttpRequest httpRequest = HttpRequest.request()
                .withHeader("Authorization", "Bearer " + mockAuthData.getRight())
                .withMethod("POST")
                .withPath("/v1/account/margin/ETH-USD-PERP")
                .withBody(JsonUtils.serialize(expectedRequestData));
        MOCK_SERVER_CLIENT.when(httpRequest)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody(JsonUtils.serialize(mockResponseData))
                );

        ParadexUpdateAccountMarginDTO updateAccountMarginRequest = ParadexUpdateAccountMarginDTO.builder()
                .market(market)
                .leverage(17)
                .marginType(MarginType.CROSS)
                .build();
        ParadexAccountMarginDTO accountMargin = PARADEX_CLIENT.accountAPI().updateAccountMargin(PARADEX_TEST_ACCOUNT, updateAccountMarginRequest);
        assertEquals(expectedAccountMargin, accountMargin);

        MOCK_SERVER_CLIENT.verify(mockAuthData.getLeft(), VerificationTimes.once());
        MOCK_SERVER_CLIENT.verify(httpRequest, VerificationTimes.once());
    }
}
