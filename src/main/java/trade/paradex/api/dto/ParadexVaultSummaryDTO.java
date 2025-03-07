package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexVaultSummaryDTO {

    @JsonProperty("address")
    private String address;

    @JsonProperty("last_month_return")
    private double lastMonthReturn;

    @JsonProperty("max_drawdown")
    private double maxDrawdown;

    @JsonProperty("max_drawdown_24h")
    private double maxDrawdown24h;

    @JsonProperty("max_drawdown_30d")
    private double maxDrawdown30d;

    @JsonProperty("max_drawdown_7d")
    private double maxDrawdown7d;

    @JsonProperty("net_deposits")
    private double netDeposits;

    @JsonProperty("num_depositors")
    private int numDepositors;

    @JsonProperty("owner_equity")
    private double ownerEquity;

    @JsonProperty("pnl_24h")
    private double pnl24h;

    @JsonProperty("pnl_30d")
    private double pnl30d;

    @JsonProperty("pnl_7d")
    private double pnl7d;

    @JsonProperty("roi_24h")
    private double roi24h;

    @JsonProperty("roi_30d")
    private double roi30d;

    @JsonProperty("roi_7d")
    private double roi7d;

    @JsonProperty("total_pnl")
    private double totalPnl;

    @JsonProperty("total_roi")
    private double totalRoi;

    @JsonProperty("tvl")
    private double tvl;

    @JsonProperty("volume")
    private double volume;

    @JsonProperty("volume_24h")
    private double volume24h;

    @JsonProperty("volume_30d")
    private double volume30d;

    @JsonProperty("volume_7d")
    private double volume7d;

    @JsonProperty("vtoken_price")
    private double vtokenPrice;

    @JsonProperty("vtoken_supply")
    private double vtokenSupply;

}
