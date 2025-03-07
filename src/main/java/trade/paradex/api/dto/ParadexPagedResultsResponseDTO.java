package trade.paradex.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParadexPagedResultsResponseDTO<T> extends ParadexResultsResponseDTO<T> {

    private String next;
    private String prev;

}
