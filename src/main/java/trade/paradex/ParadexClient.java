package trade.paradex;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import trade.paradex.api.account.ParadexAccountAPI;
import trade.paradex.api.account.ParadexAccountAPIImpl;
import trade.paradex.api.auth.CacheableParadexAuthAPI;
import trade.paradex.api.auth.ParadexAuthAPI;
import trade.paradex.api.auth.ParadexAuthAPIImpl;
import trade.paradex.api.market.ParadexMarketAPI;
import trade.paradex.api.market.ParadexMarketAPIImpl;
import trade.paradex.api.order.ParadexOrderAPI;
import trade.paradex.api.order.ParadexOrderAPIImpl;
import trade.paradex.api.vault.ParadexVaultAPI;
import trade.paradex.api.vault.ParadexVaultAPIImpl;
import trade.paradex.http.DefaultHttpClient;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.http.resolver.StaticHttpClientResolver;
import trade.paradex.model.ParadexEnvironment;

import java.util.Objects;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParadexClient {

    private final ParadexAuthAPI authAPI;
    private final ParadexAccountAPI accountAPI;
    private final ParadexMarketAPI marketAPI;
    private final ParadexOrderAPI orderAPI;
    private final ParadexVaultAPI vaultAPI;

    public static ParadexClientBuilder builder(ParadexEnvironment environment) {
        return builder(environment.getUrl(), environment.getChainId());
    }

    public static ParadexClientBuilder builder(String url, String chainId) {
        return new ParadexClientBuilder(
                Objects.requireNonNull(url, "url cannot be null"),
                Objects.requireNonNull(chainId, "chainId cannot be null")
        );
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ParadexClientBuilder {

        private static final HttpClient DEFAULT_CLIENT = new DefaultHttpClient();

        private final String url;
        private final String chainId;
        private HttpClient httpClient = DEFAULT_CLIENT;
        private HttpClientResolver httpClientResolver;

        public ParadexClientBuilder httpClient(HttpClient httpClient) {
            this.httpClient = Objects.requireNonNull(httpClient, "httpClient cannot be null");
            return this;
        }

        public ParadexClientBuilder httpClientResolver(HttpClientResolver httpClientResolver) {
            this.httpClientResolver = Objects.requireNonNull(httpClientResolver, "httpClientResolver cannot be null");
            return this;
        }

        public ParadexClient build() {
            HttpClientResolver httpClientResolver = this.httpClientResolver != null
                    ? this.httpClientResolver
                    : new StaticHttpClientResolver(httpClient);

            ParadexAuthAPIImpl authApi = new CacheableParadexAuthAPI(url, chainId, httpClientResolver);
            ParadexAccountAPI accountApi = new ParadexAccountAPIImpl(url, authApi, httpClientResolver);
            ParadexMarketAPI marketApi = new ParadexMarketAPIImpl(url, httpClient);
            ParadexOrderAPI orderApi = new ParadexOrderAPIImpl(url, chainId, authApi, httpClientResolver);
            ParadexVaultAPI vaultApi = new ParadexVaultAPIImpl(url, authApi, httpClient, httpClientResolver);

            return new ParadexClient(authApi, accountApi, marketApi, orderApi, vaultApi);
        }
    }
}

