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
import trade.paradex.api.liquidation.ParadexLiquidationAPI;
import trade.paradex.api.liquidation.ParadexLiquidationAPIImpl;
import trade.paradex.api.market.ParadexMarketAPI;
import trade.paradex.api.market.ParadexMarketAPIImpl;
import trade.paradex.api.order.ParadexOrderAPI;
import trade.paradex.api.order.ParadexOrderAPIImpl;
import trade.paradex.api.system.ParadexSystemAPI;
import trade.paradex.api.system.ParadexSystemAPIImpl;
import trade.paradex.api.vault.ParadexVaultAPI;
import trade.paradex.api.vault.ParadexVaultAPIImpl;
import trade.paradex.http.DefaultHttpClient;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.http.resolver.StaticHttpClientResolver;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;

import java.util.Objects;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParadexClient {

    private final ParadexAuthAPI authAPI;
    private final ParadexAccountAPI accountAPI;
    private final ParadexMarketAPI marketAPI;
    private final ParadexLiquidationAPI liquidationAPI;
    private final ParadexOrderAPI orderAPI;
    private final ParadexSystemAPI systemAPI;
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
        private boolean useCacheableJWT = true;
        private HttpClient httpClient = DEFAULT_CLIENT;
        private HttpClientResolver httpClientResolver;

        /**
         * Configure whether to use a cached JWT token or not.
         * By default, uses {@link CacheableParadexAuthAPI} that cache JWT token for each {@link ParadexAccount}.
         *
         * @param useCacheableJWT flag to cache JWT token or not.
         * @return returns current {@link ParadexClientBuilder}
         */
        public ParadexClientBuilder useCacheableJWT(boolean useCacheableJWT) {
            this.useCacheableJWT = useCacheableJWT;
            return this;
        }

        /**
         * Configure a custom {@link HttpClient} to use for private/public API calls.
         *
         * @param httpClient {@link HttpClient}
         * @return returns current {@link ParadexClientBuilder}
         */
        public ParadexClientBuilder httpClient(HttpClient httpClient) {
            this.httpClient = Objects.requireNonNull(httpClient, "httpClient cannot be null");
            return this;
        }

        /**
         * Configure a custom {@link HttpClientResolver} for resolving {@link HttpClient} for particular {@link ParadexAccount}.
         * For example to use proxied http client for some {@link ParadexAccount} etc.
         * By default uses {@link StaticHttpClientResolver} with given {@link ParadexClientBuilder#httpClient}.
         *
         * @param httpClientResolver {@link HttpClientResolver}
         * @return returns current {@link ParadexClientBuilder}
         */
        public ParadexClientBuilder httpClientResolver(HttpClientResolver httpClientResolver) {
            this.httpClientResolver = Objects.requireNonNull(httpClientResolver, "httpClientResolver cannot be null");
            return this;
        }

        public ParadexClient build() {
            HttpClientResolver httpClientResolver = this.httpClientResolver != null
                    ? this.httpClientResolver
                    : new StaticHttpClientResolver(httpClient);

            ParadexAuthAPI authApi = useCacheableJWT
                    ? new CacheableParadexAuthAPI(url, chainId, httpClientResolver)
                    : new ParadexAuthAPIImpl(url, chainId, httpClientResolver);
            ParadexAccountAPI accountApi = new ParadexAccountAPIImpl(url, authApi, httpClientResolver);
            ParadexMarketAPI marketApi = new ParadexMarketAPIImpl(url, httpClient);
            ParadexLiquidationAPI liquidationApi = new ParadexLiquidationAPIImpl(url, authApi, httpClientResolver);
            ParadexOrderAPI orderApi = new ParadexOrderAPIImpl(url, chainId, authApi, httpClientResolver);
            ParadexSystemAPI systemApi = new ParadexSystemAPIImpl(url, httpClient);
            ParadexVaultAPI vaultApi = new ParadexVaultAPIImpl(url, authApi, httpClient, httpClientResolver);

            return new ParadexClient(authApi, accountApi, marketApi, liquidationApi, orderApi, systemApi, vaultApi);
        }
    }
}

