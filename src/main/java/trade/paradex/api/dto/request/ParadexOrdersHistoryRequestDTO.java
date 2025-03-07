package trade.paradex.api.dto.request;

import lombok.Builder;
import lombok.Value;
import trade.paradex.api.dto.OrderSide;
import trade.paradex.api.dto.OrderType;

@Value
@Builder
public class ParadexOrdersHistoryRequestDTO {

    String clientId;
    String cursor;
    Long startAt;
    Long endAt;
    String market;
    @Builder.Default
    int pageSize = 100;
    String status;
    OrderSide orderSide;
    OrderType orderType;
    
}
