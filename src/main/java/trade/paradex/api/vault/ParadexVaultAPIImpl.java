package trade.paradex.api.vault;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.api.auth.ParadexAuthAPI;
import trade.paradex.api.dto.*;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.model.ParadexAccount;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.ParadexUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ParadexVaultAPIImpl extends ParadexBaseAPI implements ParadexVaultAPI {

    private final String url;
    private final ParadexAuthAPI authAPI;
    private final HttpClient httpClient;
    private final HttpClientResolver httpClientResolver;

    @Override
    public ParadexResultsResponseDTO<ParadexVaultDTO> getVaults(String address) {
        Map<String, String> queryParams = address != null ? Map.of("address", address) : null;

        var response = httpClient.get(url + "/v1/vaults", null, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetVaultsTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexVaultBalanceDTO> getVaultBalance(String address, String strategyAddress) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("address", address);
        if (strategyAddress != null) {
            queryParams.put("strategy", strategyAddress);
        }

        var response = httpClient.get(url + "/v1/vaults/balance", null, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetVaultBalanceTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexPositionDTO> getVaultPositions(String address, String strategyAddress) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("address", address);
        if (strategyAddress != null) {
            queryParams.put("strategy", strategyAddress);
        }

        var response = httpClient.get(url + "/v1/vaults/positions", null, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetVaultPositionsTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexVaultAccountSummaryDTO> getVaultAccountSummary(ParadexAccount account, String address) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = address != null ? Map.of("address", address) : null;

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/vaults/account-summary", headers, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetVaultAccountSummaryTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexVaultSummaryDTO> getVaultSummary(String address) {
        Map<String, String> queryParams = address != null ? Map.of("address", address) : null;

        var response = httpClient.get(url + "/v1/vaults/summary", null, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetVaultSummaryTypeReference.INSTANCE);
    }

    public static class GetVaultsTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexVaultDTO>> {
        public static final GetVaultsTypeReference INSTANCE = new GetVaultsTypeReference();
    }

    public static class GetVaultBalanceTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexVaultBalanceDTO>> {
        public static final GetVaultBalanceTypeReference INSTANCE = new GetVaultBalanceTypeReference();
    }

    public static class GetVaultPositionsTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexPositionDTO>> {
        public static final GetVaultPositionsTypeReference INSTANCE = new GetVaultPositionsTypeReference();
    }

    public static class GetVaultAccountSummaryTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexVaultAccountSummaryDTO>> {
        public static final GetVaultAccountSummaryTypeReference INSTANCE = new GetVaultAccountSummaryTypeReference();
    }

    public static class GetVaultSummaryTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexVaultSummaryDTO>> {
        public static final GetVaultSummaryTypeReference INSTANCE = new GetVaultSummaryTypeReference();
    }
}
