package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadexAccountMarginDTO {

    @JsonProperty("account")
    private String account;

    @JsonProperty("configs")
    private List<MarginConfigDTO> configs;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MarginConfigDTO {

        @JsonProperty("market")
        private String market;

        @JsonProperty("leverage")
        private int leverage;

        @JsonProperty("margin_type")
        private MarginType marginType;

    }
}
