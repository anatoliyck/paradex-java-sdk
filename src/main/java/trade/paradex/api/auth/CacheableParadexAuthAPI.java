package trade.paradex.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.model.ParadexAccount;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheableParadexAuthAPI extends ParadexAuthAPIImpl {

    private final Map<ParadexAccount, JwtTokenData> jwtTokenCache = new ConcurrentHashMap<>();

    public CacheableParadexAuthAPI(String url, String chainId, HttpClientResolver httpClientResolver) {
        super(url, chainId, httpClientResolver);
    }

    @Override
    public String auth(ParadexAccount account) {
        JwtTokenData jwtTokenData = jwtTokenCache.compute(account, (k, v) -> {
            if (v == null || v.isExpired()) {
                v = JwtTokenData.of(super.auth(k));
            }

            return v;
        });

        return jwtTokenData.getJwt();
    }

    @Data
    @AllArgsConstructor
    private static class JwtTokenData {

        private static final int EXPIRE_TIME_GAP_SECONDS = 10;

        private final String jwt;
        private final Instant expiresAt;

        public boolean isExpired() {
            return Instant.now().minusSeconds(EXPIRE_TIME_GAP_SECONDS).isAfter(expiresAt);
        }

        public static JwtTokenData of(String jwt) {
            DecodedJWT decodedJWT = JWT.decode(jwt);
            Instant expireAt = decodedJWT.getExpiresAtAsInstant();
            return new JwtTokenData(jwt, expireAt);
        }
    }
}
