package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadexLiquidationDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("created_at")
    private long createdAt;

}
