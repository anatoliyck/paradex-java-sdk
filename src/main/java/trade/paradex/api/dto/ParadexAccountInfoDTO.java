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
public class ParadexAccountInfoDTO {

    @JsonProperty("account")
    private String account;

    @JsonProperty("account_value")
    private BigDecimal accountValue;

    @JsonProperty("free_collateral")
    private BigDecimal freeCollateral;

    @JsonProperty("initial_margin_requirement")
    private BigDecimal initialMarginRequirement;

    @JsonProperty("maintenance_margin_requirement")
    private BigDecimal maintenanceMarginRequirement;

    @JsonProperty("margin_cushion")
    private BigDecimal marginCushion;

    @JsonProperty("seq_no")
    private long seqNo;

    @JsonProperty("settlement_asset")
    private String settlementAsset;

    @JsonProperty("status")
    private String status;

    @JsonProperty("total_collateral")
    private BigDecimal totalCollateral;

    @JsonProperty("updated_at")
    private long updatedAt;

}
