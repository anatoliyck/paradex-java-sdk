package trade.paradex.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsocketParadexMessageDTO {

    @Builder.Default
    private String jsonrpc = "2.0";
    private int id;
    private String method;
    private Map<String, Object> params;

}