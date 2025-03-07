package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexFundingDataDTO {

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("funding_index")
    private double fundingIndex;

    @JsonProperty("funding_premium")
    private double fundingPremium;

    @JsonProperty("funding_rate")
    private double fundingRate;

    @JsonProperty("market")
    private String market;

}
