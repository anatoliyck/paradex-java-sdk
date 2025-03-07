package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexAccountInfoDTO {

    @JsonProperty("account")
    private String account;

    @JsonProperty("account_value")
    private double accountValue;

    @JsonProperty("free_collateral")
    private double freeCollateral;

    @JsonProperty("initial_margin_requirement")
    private double initialMarginRequirement;

    @JsonProperty("maintenance_margin_requirement")
    private double maintenanceMarginRequirement;

    @JsonProperty("margin_cushion")
    private double marginCushion;

    @JsonProperty("seq_no")
    private long seqNo;

    @JsonProperty("settlement_asset")
    private String settlementAsset;

    @JsonProperty("status")
    private String status;

    @JsonProperty("total_collateral")
    private double totalCollateral;

    @JsonProperty("updated_at")
    private long updatedAt;

}
