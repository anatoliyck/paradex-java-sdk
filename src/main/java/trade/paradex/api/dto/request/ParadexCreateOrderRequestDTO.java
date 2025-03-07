package trade.paradex.api.dto.request;

import lombok.Builder;
import lombok.Value;
import trade.paradex.api.dto.Instruction;
import trade.paradex.api.dto.OrderSide;
import trade.paradex.api.dto.OrderType;

import java.util.List;

@Value
@Builder
public class ParadexCreateOrderRequestDTO {

    String market;
    OrderType orderType;
    Double price;
    OrderSide orderSide;
    double size;
    List<String> flags;
    @Builder.Default
    Instruction instruction = Instruction.GTC;
    String clientId;
    Long recvWindow;
    String stp;
    Double triggerPrice;
}
