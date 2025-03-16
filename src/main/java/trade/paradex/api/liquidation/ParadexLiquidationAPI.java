package trade.paradex.api.liquidation;

import trade.paradex.api.dto.ParadexLiquidationDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.model.ParadexAccount;

public interface ParadexLiquidationAPI {

    /**
     * Retrieves a list of liquidations associated to the requester’s account.
     *
     * @param account {@link ParadexAccount}
     * @param from    start time (unix time millisecond)
     * @param to      end time (unix time millisecond)
     * @return List of {@link ParadexLiquidationDTO}
     */
    ParadexResultsResponseDTO<ParadexLiquidationDTO> getLiquidations(ParadexAccount account, long from, long to);

}
