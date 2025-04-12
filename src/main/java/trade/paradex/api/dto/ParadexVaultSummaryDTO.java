package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexVaultSummaryDTO {

    @JsonProperty("address")
    private String address;

    @JsonProperty("last_month_return")
    private BigDecimal lastMonthReturn;

    @JsonProperty("max_drawdown")
    private BigDecimal maxDrawdown;

    @JsonProperty("max_drawdown_24h")
    private BigDecimal maxDrawdown24h;

    @JsonProperty("max_drawdown_30d")
    private BigDecimal maxDrawdown30d;

    @JsonProperty("max_drawdown_7d")
    private BigDecimal maxDrawdown7d;

    @JsonProperty("net_deposits")
    private BigDecimal netDeposits;

    @JsonProperty("num_depositors")
    private int numDepositors;

    @JsonProperty("owner_equity")
    private BigDecimal ownerEquity;

    @JsonProperty("pnl_24h")
    private BigDecimal pnl24h;

    @JsonProperty("pnl_30d")
    private BigDecimal pnl30d;

    @JsonProperty("pnl_7d")
    private BigDecimal pnl7d;

    @JsonProperty("roi_24h")
    private BigDecimal roi24h;

    @JsonProperty("roi_30d")
    private BigDecimal roi30d;

    @JsonProperty("roi_7d")
    private BigDecimal roi7d;

    @JsonProperty("total_pnl")
    private BigDecimal totalPnl;

    @JsonProperty("total_roi")
    private BigDecimal totalRoi;

    @JsonProperty("tvl")
    private BigDecimal tvl;

    @JsonProperty("volume")
    private BigDecimal volume;

    @JsonProperty("volume_24h")
    private BigDecimal volume24h;

    @JsonProperty("volume_30d")
    private BigDecimal volume30d;

    @JsonProperty("volume_7d")
    private BigDecimal volume7d;

    @JsonProperty("vtoken_price")
    private BigDecimal vtokenPrice;

    @JsonProperty("vtoken_supply")
    private BigDecimal vtokenSupply;

}
