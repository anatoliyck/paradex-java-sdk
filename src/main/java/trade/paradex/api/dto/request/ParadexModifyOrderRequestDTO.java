package trade.paradex.api.dto.request;

import lombok.Builder;
import lombok.Value;
import trade.paradex.api.dto.OrderSide;
import trade.paradex.api.dto.OrderType;

@Value
@Builder
public class ParadexModifyOrderRequestDTO {

    /**
     * Paradex order id
     */
    String id;
    String market;
    double price;
    OrderSide orderSide;
    double size;
    OrderType orderType;

}
