package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexMarketBestBidOfferDTO {

    @JsonProperty("ask")
    private BigDecimal ask;

    @JsonProperty("ask_size")
    private BigDecimal askSize;

    @JsonProperty("bid")
    private BigDecimal bid;

    @JsonProperty("bid_size")
    private BigDecimal bidSize;

    @JsonProperty("last_updated_at")
    private long lastUpdatedAt;

    @JsonProperty("market")
    private String market;

    @JsonProperty("seq_no")
    private long seqNo;
}
