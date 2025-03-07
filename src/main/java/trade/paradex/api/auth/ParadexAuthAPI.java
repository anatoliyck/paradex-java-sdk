package trade.paradex.api.auth;

import trade.paradex.model.ParadexAccount;

public interface ParadexAuthAPI {

    String auth(ParadexAccount account);

}
