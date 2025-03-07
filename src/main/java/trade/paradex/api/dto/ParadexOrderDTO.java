package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadexOrderDTO {

    @JsonProperty("account")
    private String account;

    @JsonProperty("avg_fill_price")
    private Double avgFillPrice;

    @JsonProperty("cancel_reason")
    private String cancelReason;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("flags")
    private List<String> flags;

    @JsonProperty("id")
    private String id;

    @JsonProperty("instruction")
    private String instruction;

    @JsonProperty("last_updated_at")
    private Long lastUpdatedAt;

    @JsonProperty("market")
    private String market;

    @JsonProperty("price")
    private double price;

    @JsonProperty("published_at")
    private Long publishedAt;

    @JsonProperty("received_at")
    private Long receivedAt;

    @JsonProperty("remaining_size")
    private double remainingSize;

    @JsonProperty("seq_no")
    private long seqNo;

    @JsonProperty("side")
    private OrderSide side;

    @JsonProperty("size")
    private double size;

    @JsonProperty("status")
    private String status;

    @JsonProperty("stp")
    private String stp;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("trigger_price")
    private Double triggerPrice;

    @JsonProperty("type")
    private String type;

}