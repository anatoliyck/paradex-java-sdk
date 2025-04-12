package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
public class ParadexPositionDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("average_entry_price")
    private BigDecimal averageEntryPrice;

    @JsonProperty("average_entry_price_usd")
    private BigDecimal averageEntryPriceUsd;

    @JsonProperty("average_exit_price")
    private BigDecimal averageExitPrice;

    @JsonProperty("cached_funding_index")
    private BigDecimal cachedFundingIndex;

    @JsonProperty("closed_at")
    private Long closedAt;

    @JsonProperty("cost")
    private BigDecimal cost;

    @JsonProperty("cost_usd")
    private BigDecimal costUsd;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("last_fill_id")
    private String lastFillId;

    @JsonProperty("last_updated_at")
    private Long lastUpdatedAt;

    @JsonProperty("leverage")
    private BigDecimal leverage;

    @JsonProperty("liquidation_price")
    private BigDecimal liquidationPrice;

    @JsonProperty("market")
    private String market;

    @JsonProperty("realized_positional_pnl")
    private BigDecimal realizedPositionalPnl;

    @JsonProperty("seq_no")
    private long seqNo;

    @JsonProperty("side")
    private PositionSide side;

    @JsonProperty("size")
    private BigDecimal size;

    @JsonProperty("status")
    private PositionStatus status;

    @JsonProperty("unrealized_funding_pnl")
    private BigDecimal unrealizedFundingPnl;

    @JsonProperty("unrealized_pnl")
    private BigDecimal unrealizedPnl;

    @JsonProperty("realized_positional_funding_pnl")
    private BigDecimal realizedPositionalFundingPnl;

}
