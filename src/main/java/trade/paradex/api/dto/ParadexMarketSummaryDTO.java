package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexMarketSummaryDTO {

    @JsonProperty("ask")
    private double ask;

    @JsonProperty("ask_iv")
    private double askIv;

    @JsonProperty("bid")
    private double bid;

    @JsonProperty("bid_iv")
    private double bidIv;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("delta")
    private Double delta;

    @JsonProperty("funding_rate")
    private double fundingRate;

    @JsonProperty("future_funding_rate")
    private double futureFundingRate;

    @JsonProperty("last_iv")
    private double lastIv;

    @JsonProperty("last_traded_price")
    private double lastTradedPrice;

    @JsonProperty("mark_iv")
    private double markIv;

    @JsonProperty("mark_price")
    private double markPrice;

    @JsonProperty("open_interest")
    private double openInterest;

    @JsonProperty("price_change_rate_24h")
    private double priceChangeRate24h;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("total_volume")
    private double totalVolume;

    @JsonProperty("underlying_price")
    private double underlyingPrice;

    @JsonProperty("volume_24h")
    private double volume24h;


}
