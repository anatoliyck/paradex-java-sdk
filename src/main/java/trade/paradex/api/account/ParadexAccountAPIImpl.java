package trade.paradex.api.account;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.api.auth.ParadexAuthAPI;
import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexOrdersFillRequestDTO;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.model.ParadexAccount;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.ParadexUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ParadexAccountAPIImpl extends ParadexBaseAPI implements ParadexAccountAPI {

    private final String url;
    private final ParadexAuthAPI authAPI;
    private final HttpClientResolver httpClientResolver;

    @Override
    public ParadexAccountInfoDTO getAccountInfo(ParadexAccount account, String subAccountAddress) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = subAccountAddress != null ? Map.of("subaccount_address", subAccountAddress) : null;

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/account", headers, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, AccountInfoTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexBalanceDTO> getBalances(ParadexAccount account) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/balance", headers, null);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetBalancesTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexPositionDTO> getPositions(ParadexAccount account) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/positions", headers, null);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetPositionsTypeReference.INSTANCE);
    }

    @Override
    public ParadexPagedResultsResponseDTO<ParadexOrderFillDTO> getOrdersFill(ParadexAccount account, ParadexOrdersFillRequestDTO requestDTO) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = prepareQueryParams(requestDTO);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/fills", headers, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetOrdersFillTypeReference.INSTANCE);
    }

    private Map<String, String> prepareQueryParams(ParadexOrdersFillRequestDTO requestDTO) {
        Map<String, String> queryParams = new HashMap<>();
        if (requestDTO.getCursor() != null) {
            queryParams.put("cursor", requestDTO.getCursor());
        }
        if (requestDTO.getStartAt() != null) {
            queryParams.put("start_at", String.valueOf(requestDTO.getStartAt()));
        }
        if (requestDTO.getEndAt() != null) {
            queryParams.put("end_at", String.valueOf(requestDTO.getEndAt()));
        }
        if (requestDTO.getMarket() != null) {
            queryParams.put("market", requestDTO.getMarket());
        }
        queryParams.put("page_size", String.valueOf(requestDTO.getPageSize()));

        return queryParams;
    }

    private static class AccountInfoTypeReference extends TypeReference<ParadexAccountInfoDTO> {
        public static final AccountInfoTypeReference INSTANCE = new AccountInfoTypeReference();
    }

    private static class GetBalancesTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexBalanceDTO>> {
        public static final GetBalancesTypeReference INSTANCE = new GetBalancesTypeReference();
    }

    private static class GetPositionsTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexPositionDTO>> {
        public static final GetPositionsTypeReference INSTANCE = new GetPositionsTypeReference();
    }

    private static class GetOrdersFillTypeReference extends TypeReference<ParadexPagedResultsResponseDTO<ParadexOrderFillDTO>> {
        public static final GetOrdersFillTypeReference INSTANCE = new GetOrdersFillTypeReference();
    }
}
