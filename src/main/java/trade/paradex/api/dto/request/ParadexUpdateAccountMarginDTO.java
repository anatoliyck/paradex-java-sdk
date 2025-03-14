package trade.paradex.api.dto.request;

import lombok.Builder;
import lombok.Value;
import trade.paradex.api.dto.MarginType;

@Value
@Builder
public class ParadexUpdateAccountMarginDTO {

    String market;
    int leverage;
    MarginType marginType;

}
