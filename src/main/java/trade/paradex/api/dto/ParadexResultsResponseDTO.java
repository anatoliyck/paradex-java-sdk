package trade.paradex.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ParadexResultsResponseDTO<T> {

    List<T> results;

}
