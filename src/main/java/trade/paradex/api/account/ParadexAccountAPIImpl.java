package trade.paradex.api.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.api.auth.ParadexAuthAPI;
import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexOrdersFillRequestDTO;
import trade.paradex.api.dto.request.ParadexUpdateAccountMarginDTO;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.model.ParadexAccount;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.ParadexUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
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

        return JsonUtils.deserialize(body, GetAccountInfoTypeReference.INSTANCE);
    }

    @Override
    public ParadexAccountMarginDTO getAccountMargin(ParadexAccount account, String market) {
        if (market == null || market.isEmpty()) {
            throw new IllegalArgumentException("market cannot be null or empty");
        }

        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = Map.of("market", market);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/account/margin", headers, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetAccountMarginTypeReference.INSTANCE);
    }

    @Override
    public ParadexAccountMarginDTO updateAccountMargin(ParadexAccount account, ParadexUpdateAccountMarginDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("requestDTO cannot be null");
        }

        if (requestDTO.getMarket() == null || requestDTO.getMarket().isEmpty()) {
            throw new IllegalArgumentException("market cannot be null or empty");
        }

        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        String fullUrl = url + "/v1/account/margin/" + URLEncoder.encode(requestDTO.getMarket(), StandardCharsets.UTF_8);

        String requestBody = prepareBody(requestDTO);
        var response = httpClient.post(fullUrl, headers, requestBody);

        String responseBody = processResponse(response);

        return deserializeUpdateMarginBody(responseBody);
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

    private ParadexAccountMarginDTO deserializeUpdateMarginBody(String body) {
        JsonNode node = JsonUtils.readTree(body);

        ParadexAccountMarginDTO.MarginConfigDTO config = ParadexAccountMarginDTO.MarginConfigDTO.builder()
                .market(node.get("market").asText())
                .leverage(node.get("leverage").asInt())
                .marginType(MarginType.valueOf(node.get("margin_type").asText()))
                .build();
        return ParadexAccountMarginDTO.builder()
                .account(node.get("account").asText())
                .configs(List.of(config))
                .build();
    }

    private String prepareBody(ParadexUpdateAccountMarginDTO requestDTO) {
        var data = Map.of(
                "leverage", requestDTO.getLeverage(),
                "margin_type", requestDTO.getMarginType()
        );

        return JsonUtils.serialize(data);
    }

    private static class GetAccountInfoTypeReference extends TypeReference<ParadexAccountInfoDTO> {
        private static final GetAccountInfoTypeReference INSTANCE = new GetAccountInfoTypeReference();
    }

    private static class GetAccountMarginTypeReference extends TypeReference<ParadexAccountMarginDTO> {
        private static final GetAccountMarginTypeReference INSTANCE = new GetAccountMarginTypeReference();
    }

    private static class GetBalancesTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexBalanceDTO>> {
        private static final GetBalancesTypeReference INSTANCE = new GetBalancesTypeReference();
    }

    private static class GetPositionsTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexPositionDTO>> {
        private static final GetPositionsTypeReference INSTANCE = new GetPositionsTypeReference();
    }

    private static class GetOrdersFillTypeReference extends TypeReference<ParadexPagedResultsResponseDTO<ParadexOrderFillDTO>> {
        private static final GetOrdersFillTypeReference INSTANCE = new GetOrdersFillTypeReference();
    }
}
