package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexOrderFillDTO {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("fee")
    private BigDecimal fee;

    @JsonProperty("fee_currency")
    private String feeCurrency;

    @JsonProperty("fill_type")
    private String fillType;

    @JsonProperty("id")
    private String id;

    @JsonProperty("liquidity")
    private String liquidity;

    @JsonProperty("market")
    private String market;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("realized_funding")
    private BigDecimal realizedFunding;

    @JsonProperty("realized_pnl")
    private BigDecimal realizedPnl;

    @JsonProperty("remaining_size")
    private BigDecimal remainingSize;

    @JsonProperty("side")
    private OrderSide side;

    @JsonProperty("size")
    private BigDecimal size;

}
