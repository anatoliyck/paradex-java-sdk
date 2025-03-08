package trade.paradex.api.auth;

import trade.paradex.model.ParadexAccount;

public interface ParadexAuthAPI {

    /**
     * Authenticate using signed payload to get a JWT for usage in other endpoints.
     *
     * @param account {@link ParadexAccount}
     * @return JWT auth token
     */
    String auth(ParadexAccount account);

}
