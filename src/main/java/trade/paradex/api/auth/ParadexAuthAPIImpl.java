package trade.paradex.api.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.model.ParadexAccount;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.Pair;
import trade.paradex.utils.ParadexUtils;

import java.util.Map;

@RequiredArgsConstructor
public class ParadexAuthAPIImpl extends ParadexBaseAPI implements ParadexAuthAPI {

    private final String url;
    private final String chainId;
    private final HttpClientResolver httpClientResolver;

    @Override
    public String auth(ParadexAccount account) {
        HttpClient httpClient = httpClientResolver.resolve(account);
        long timestamp = System.currentTimeMillis() / 1000;
        long expiry = timestamp + 24 * 60 * 60;

        var authMessage = createAuthMessage(timestamp, expiry, chainId);

        Pair<String, String> pair = ParadexUtils.getSignature(account, authMessage);

        var requestHeaders = Map.of(
                "PARADEX-STARKNET-ACCOUNT", account.getAddress(),
                "PARADEX-STARKNET-SIGNATURE", pair.getLeft(),
                "PARADEX-STARKNET-MESSAGE-HASH", pair.getRight(),
                "PARADEX-TIMESTAMP", String.valueOf(timestamp),
                "PARADEX-SIGNATURE-EXPIRATION", String.valueOf(expiry)
        );
        var response = httpClient.post(url + "/v1/auth", requestHeaders, null);

        String body = processResponse(response);

        Map<String, Object> data = JsonUtils.deserialize(body, MapTypeReference.INSTANCE);
        return (String) data.get("jwt_token");
    }

    private String createAuthMessage(long timestamp, long expiration, String chainId) {
        return String.format("{\n" +
                "  \"message\": {\n" +
                "    \"method\": \"POST\",\n" +
                "    \"path\": \"/v1/auth\",\n" +
                "    \"body\": \"\",\n" +
                "    \"timestamp\": %s,\n" +
                "    \"expiration\": %s\n" +
                "  },\n" +
                "  \"domain\": {\n" +
                "    \"name\": \"Paradex\",\n" +
                "    \"chainId\": \"%s\",\n" +
                "    \"version\": \"1\"\n" +
                "  },\n" +
                "  \"primaryType\": \"Request\",\n" +
                "  \"types\": {\n" +
                "    \"StarkNetDomain\": [\n" +
                "      {\n" +
                "        \"name\": \"name\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"chainId\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"version\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"Request\": [\n" +
                "      {\n" +
                "        \"name\": \"method\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"path\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"body\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"timestamp\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"expiration\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}", timestamp, expiration, chainId);
    }

    private static class MapTypeReference extends TypeReference<Map<String, Object>> {
        public static final MapTypeReference INSTANCE = new MapTypeReference();
    }
}
