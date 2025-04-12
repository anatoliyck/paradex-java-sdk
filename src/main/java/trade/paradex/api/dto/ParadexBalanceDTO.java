package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexBalanceDTO {

    @JsonProperty("token")
    private String token;

    @JsonProperty("size")
    private BigDecimal amount;

    @JsonProperty("last_updated_at")
    private long lastUpdatedAt;

}
