package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexFundingDataDTO {

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("funding_index")
    private BigDecimal fundingIndex;

    @JsonProperty("funding_premium")
    private BigDecimal fundingPremium;

    @JsonProperty("funding_rate")
    private BigDecimal fundingRate;

    @JsonProperty("market")
    private String market;

}
