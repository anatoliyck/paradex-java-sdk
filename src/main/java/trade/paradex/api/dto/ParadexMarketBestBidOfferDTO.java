package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexMarketBestBidOfferDTO {

    @JsonProperty("ask")
    private double ask;

    @JsonProperty("ask_size")
    private double askSize;

    @JsonProperty("bid")
    private double bid;

    @JsonProperty("bid_size")
    private double bidSize;

    @JsonProperty("last_updated_at")
    private long lastUpdatedAt;

    @JsonProperty("market")
    private String market;

    @JsonProperty("seq_no")
    private long seqNo;
}
