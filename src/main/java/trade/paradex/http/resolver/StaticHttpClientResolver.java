package trade.paradex.http.resolver;

import lombok.AllArgsConstructor;
import trade.paradex.http.HttpClient;
import trade.paradex.model.ParadexAccount;

@AllArgsConstructor
public class StaticHttpClientResolver implements HttpClientResolver {

    private final HttpClient client;

    @Override
    public HttpClient resolve(ParadexAccount account) {
        return client;
    }

}
