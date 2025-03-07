package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexBalanceDTO {

    @JsonProperty("token")
    private String token;

    @JsonProperty("size")
    private double amount;

    @JsonProperty("last_updated_at")
    private long lastUpdatedAt;

}
