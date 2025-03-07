package trade.paradex.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpResponse {

    private final int statusCode;
    private final String body;

}
