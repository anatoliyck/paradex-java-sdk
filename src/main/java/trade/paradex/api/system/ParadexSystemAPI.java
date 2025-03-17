package trade.paradex.api.system;

import trade.paradex.api.dto.ParadexSystemConfigDTO;

public interface ParadexSystemAPI {

    /**
     * Get clearing and settlement layer config for Paradex.
     *
     * @return {@link ParadexSystemConfigDTO}
     */
    ParadexSystemConfigDTO getSystemConfig();

}
