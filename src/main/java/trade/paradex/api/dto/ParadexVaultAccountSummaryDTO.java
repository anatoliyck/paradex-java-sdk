package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexVaultAccountSummaryDTO {

    @JsonProperty("address")
    private String address;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("deposited_amount")
    private BigDecimal depositedAmount;

    @JsonProperty("total_pnl")
    private BigDecimal totalPnl;

    @JsonProperty("total_roi")
    private BigDecimal totalRoi;

    @JsonProperty("vtoken_amount")
    private BigDecimal vtokenAmount;

}
