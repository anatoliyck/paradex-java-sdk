package trade.paradex.api.system;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.api.dto.ParadexSystemConfigDTO;
import trade.paradex.http.HttpClient;
import trade.paradex.utils.JsonUtils;

@RequiredArgsConstructor
public class ParadexSystemAPIImpl extends ParadexBaseAPI implements ParadexSystemAPI {

    private final String url;
    private final HttpClient httpClient;

    @Override
    public ParadexSystemConfigDTO getSystemConfig() {
        var response = httpClient.get(url + "/v1/system/config", null, null);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetSystemConfigTypeReference.INSTANCE);
    }

    private static class GetSystemConfigTypeReference extends TypeReference<ParadexSystemConfigDTO> {
        private static final GetSystemConfigTypeReference INSTANCE = new GetSystemConfigTypeReference();
    }
}
