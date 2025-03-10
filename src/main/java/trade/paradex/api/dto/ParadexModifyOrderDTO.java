package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParadexModifyOrderDTO extends ParadexOrderDTO {

    @JsonProperty("request_info")
    private ModifyRequestInfoDTO requestInfo;

    @Data
    private static class ModifyRequestInfoDTO {

        @JsonProperty("id")
        private String id;

        @JsonProperty("message")
        private String message;

        @JsonProperty("request_type")
        private String requestType;

        @JsonProperty("status")
        private String status;

    }
}
