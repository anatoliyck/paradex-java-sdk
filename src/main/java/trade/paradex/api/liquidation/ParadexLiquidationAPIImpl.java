package trade.paradex.api.liquidation;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.api.auth.ParadexAuthAPI;
import trade.paradex.api.dto.ParadexLiquidationDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.model.ParadexAccount;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.ParadexUtils;

import java.util.Map;

@RequiredArgsConstructor
public class ParadexLiquidationAPIImpl extends ParadexBaseAPI implements ParadexLiquidationAPI {

    private final String url;
    private final ParadexAuthAPI authAPI;
    private final HttpClientResolver httpClientResolver;

    @Override
    public ParadexResultsResponseDTO<ParadexLiquidationDTO> getLiquidations(ParadexAccount account, long from, long to) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = Map.of(
                "from", String.valueOf(from),
                "to", String.valueOf(to)
        );

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/liquidations", headers, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetLiquidationTypeReference.INSTANCE);
    }

    private static class GetLiquidationTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexLiquidationDTO>> {
        private static final GetLiquidationTypeReference INSTANCE = new GetLiquidationTypeReference();
    }
}
