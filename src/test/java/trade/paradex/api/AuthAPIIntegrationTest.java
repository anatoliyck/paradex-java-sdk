package trade.paradex.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import trade.paradex.BaseIntegrationTest;
import trade.paradex.utils.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AuthAPIIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Call `/v1/auth` API")
    public void testCallAuthAPI() {
        Pair<HttpRequest, String> mockAuthData = mockAuthAPI();
        String jwt = mockAuthData.getRight();

        String authJwt = PARADEX_CLIENT.authAPI().auth(PARADEX_TEST_ACCOUNT);
        assertEquals(jwt, authJwt);

        HttpRequest httpRequest = mockAuthData.getLeft();
        HttpRequest[] recordedRequests = MOCK_SERVER_CLIENT.retrieveRecordedRequests(httpRequest);
        assertEquals(1, recordedRequests.length);

        HttpRequest recordedRequest = recordedRequests[0];
        assertFalse(recordedRequest.getHeader("PARADEX-STARKNET-SIGNATURE").isEmpty());
        assertEquals(PARADEX_TEST_ACCOUNT.getAddress(), recordedRequest.getFirstHeader("PARADEX-STARKNET-ACCOUNT"));
        assertFalse(recordedRequest.getHeader("PARADEX-SIGNATURE-EXPIRATION").isEmpty());
        assertFalse(recordedRequest.getHeader("PARADEX-TIMESTAMP").isEmpty());
        assertFalse(recordedRequest.getHeader("PARADEX-STARKNET-MESSAGE-HASH").isEmpty());
    }
}
