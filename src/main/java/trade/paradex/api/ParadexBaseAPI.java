package trade.paradex.api;

import com.fasterxml.jackson.core.type.TypeReference;
import trade.paradex.api.dto.ParadexErrorResponseDTO;
import trade.paradex.exception.ParadexAPIException;
import trade.paradex.http.response.HttpResponse;
import trade.paradex.utils.JsonUtils;

public abstract class ParadexBaseAPI {

    public String processResponse(HttpResponse response) {
        if (response.getStatusCode() >= 400) {
            ParadexErrorResponseDTO errorResponse = JsonUtils.deserialize(response.getBody(), ParadexErrorResponseTypeReference.INSTANCE);
            throw new ParadexAPIException(errorResponse.getError(), errorResponse.getMessage());
        }
        return response.getBody();
    }

    public static class ParadexErrorResponseTypeReference extends TypeReference<ParadexErrorResponseDTO> {
        public static final ParadexErrorResponseTypeReference INSTANCE = new ParadexErrorResponseTypeReference();
    }
}