package trade.paradex.http.resolver;

import trade.paradex.http.HttpClient;
import trade.paradex.model.ParadexAccount;

public interface HttpClientResolver {

    HttpClient resolve(ParadexAccount account);

}
