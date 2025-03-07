package trade.paradex.api.account;

import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexOrdersFillRequestDTO;
import trade.paradex.model.ParadexAccount;

public interface ParadexAccountAPI {

    /**
     * Get main account info when `subAccountAddress` param is {@literal null},
     * otherwise returns account info for sub account.
     *
     * @param account           {@link ParadexAccount}
     * @param subAccountAddress sub account address.
     * @return {@link ParadexAccountInfoDTO}
     */
    ParadexAccountInfoDTO getAccountInfo(ParadexAccount account, String subAccountAddress);

    /**
     * Get account balances
     *
     * @param account {@link ParadexAccount}
     * @return List of {@link ParadexBalanceDTO}
     */
    ParadexResultsResponseDTO<ParadexBalanceDTO> getBalances(ParadexAccount account);

    /**
     * Get all positions owned by account.
     *
     * @param account {@link ParadexAccount}
     * @return List of {@link ParadexPositionDTO}
     */
    ParadexResultsResponseDTO<ParadexPositionDTO> getPositions(ParadexAccount account);

    /**
     * Returns a list of matched orders (i.e. fills) that have been sent to chain for settlement.
     *
     * @param account    {@link ParadexAccount}
     * @param requestDTO {@link ParadexOrdersFillRequestDTO}
     * @return List of {@link ParadexOrderFillDTO}
     */
    ParadexPagedResultsResponseDTO<ParadexOrderFillDTO> getOrdersFill(ParadexAccount account, ParadexOrdersFillRequestDTO requestDTO);

}
