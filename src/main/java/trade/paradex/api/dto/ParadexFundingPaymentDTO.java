package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadexFundingPaymentDTO {

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("fill_id")
    private String fillId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("index")
    private double index;

    @JsonProperty("market")
    private String market;

    @JsonProperty("payment")
    private BigDecimal payment;

}
