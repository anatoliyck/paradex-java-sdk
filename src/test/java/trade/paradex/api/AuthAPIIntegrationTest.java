package trade.paradex.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import trade.paradex.BaseIntegrationTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AuthAPIIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Call `/v1/auth` API")
    public void callAuthAPI() {
        String jwt = JWT.create().withExpiresAt(Instant.now().plusSeconds(300))
                .sign(Algorithm.none());

        HttpRequest httpRequest = HttpRequest.request()
                .withMethod("POST")
                .withPath("/v1/auth");
        MOCK_SERVER_CLIENT.when(httpRequest)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withBody("{\"jwt_token\":\"" + jwt + "\"}")
                );

        String authJwt = PARADEX_CLIENT.authAPI().auth(PARADEX_TEST_ACCOUNT);
        assertEquals(jwt, authJwt);

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
