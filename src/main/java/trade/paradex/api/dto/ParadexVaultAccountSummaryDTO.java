package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexVaultAccountSummaryDTO {

    @JsonProperty("address")
    private String address;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("deposited_amount")
    private double depositedAmount;

    @JsonProperty("total_pnl")
    private double totalPnl;

    @JsonProperty("total_roi")
    private double totalRoi;

    @JsonProperty("vtoken_amount")
    private double vtokenAmount;

}
