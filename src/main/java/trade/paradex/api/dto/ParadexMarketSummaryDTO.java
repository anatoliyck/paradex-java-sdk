package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexMarketSummaryDTO {

    @JsonProperty("ask")
    private BigDecimal ask;

    @JsonProperty("ask_iv")
    private BigDecimal askIv;

    @JsonProperty("bid")
    private BigDecimal bid;

    @JsonProperty("bid_iv")
    private BigDecimal bidIv;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("delta")
    private BigDecimal delta;

    @JsonProperty("funding_rate")
    private BigDecimal fundingRate;

    @JsonProperty("future_funding_rate")
    private BigDecimal futureFundingRate;

    @JsonProperty("last_iv")
    private BigDecimal lastIv;

    @JsonProperty("last_traded_price")
    private BigDecimal lastTradedPrice;

    @JsonProperty("mark_iv")
    private BigDecimal markIv;

    @JsonProperty("mark_price")
    private BigDecimal markPrice;

    @JsonProperty("open_interest")
    private BigDecimal openInterest;

    @JsonProperty("price_change_rate_24h")
    private BigDecimal priceChangeRate24h;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("total_volume")
    private BigDecimal totalVolume;

    @JsonProperty("underlying_price")
    private BigDecimal underlyingPrice;

    @JsonProperty("volume_24h")
    private BigDecimal volume24h;


}
