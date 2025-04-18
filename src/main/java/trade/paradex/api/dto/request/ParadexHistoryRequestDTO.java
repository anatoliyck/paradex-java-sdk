package trade.paradex.api.dto.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParadexHistoryRequestDTO {

    String cursor;
    Long startAt;
    Long endAt;
    String market;
    @Builder.Default
    int pageSize = 100;

}
