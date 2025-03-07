package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexOrderFillDTO {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("fee")
    private double fee;

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
    private double price;

    @JsonProperty("realized_funding")
    private double realizedFunding;

    @JsonProperty("realized_pnl")
    private double realizedPnl;

    @JsonProperty("remaining_size")
    private double remainingSize;

    @JsonProperty("side")
    private OrderSide side;

    @JsonProperty("size")
    private double size;

}
