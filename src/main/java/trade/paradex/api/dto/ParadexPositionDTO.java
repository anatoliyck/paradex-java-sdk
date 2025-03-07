package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexPositionDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("average_entry_price")
    private Double averageEntryPrice;

    @JsonProperty("average_entry_price_usd")
    private Double averageEntryPriceUsd;

    @JsonProperty("average_exit_price")
    private Double averageExitPrice;

    @JsonProperty("cached_funding_index")
    private Double cachedFundingIndex;

    @JsonProperty("closed_at")
    private Long closedAt;

    @JsonProperty("cost")
    private Double cost;

    @JsonProperty("cost_usd")
    private Double costUsd;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("last_fill_id")
    private String lastFillId;

    @JsonProperty("last_updated_at")
    private Long lastUpdatedAt;

    @JsonProperty("leverage")
    private Double leverage;

    @JsonProperty("liquidation_price")
    private Double liquidationPrice;

    @JsonProperty("market")
    private String market;

    @JsonProperty("realized_positional_pnl")
    private String realizedPositionalPnl;

    @JsonProperty("seq_no")
    private long seqNo;

    @JsonProperty("side")
    private PositionSide side;

    @JsonProperty("size")
    private double size;

    @JsonProperty("status")
    private PositionStatus status;

    @JsonProperty("unrealized_funding_pnl")
    private double unrealizedFundingPnl;

    @JsonProperty("unrealized_pnl")
    private double unrealizedPnl;

    @JsonProperty("realized_positional_funding_pnl")
    private double realizedPositionalFundingPnl;

}
