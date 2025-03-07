package trade.paradex.api.dto;

import lombok.Data;

@Data
public class ParadexErrorResponseDTO {

    private String error;
    private String message;

}
