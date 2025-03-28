package trade.paradex.api.account;

import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexHistoryRequestDTO;
import trade.paradex.api.dto.request.ParadexUpdateAccountMarginDTO;
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
     * Get margin configuration for an account and market.
     *
     * @param account {@link ParadexAccount}
     * @param market  market(e.g. BTC-USD-PERP)
     * @return {@link ParadexAccountMarginDTO}
     */
    ParadexAccountMarginDTO getAccountMargin(ParadexAccount account, String market);

    /**
     * Set margin configuration for an account and market.
     *
     * @param account    {@link ParadexAccount}
     * @param requestDTO {@link ParadexUpdateAccountMarginDTO}
     * @return {@link ParadexAccountMarginDTO}
     */
    ParadexAccountMarginDTO updateAccountMargin(ParadexAccount account, ParadexUpdateAccountMarginDTO requestDTO);

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
     * @param requestDTO {@link ParadexHistoryRequestDTO}
     * @return List of {@link ParadexOrderFillDTO}
     */
    ParadexPagedResultsResponseDTO<ParadexOrderFillDTO> getOrdersFill(ParadexAccount account, ParadexHistoryRequestDTO requestDTO);

    /**
     * List funding payments made by/to the requester’s account.
     * Funding payments are periodic payments between traders to make the perpetual futures contract price is close to the index price.
     *
     * @param account    {@link ParadexAccount}
     * @param requestDTO {@link ParadexHistoryRequestDTO}
     * @return List of {@link ParadexFundingPaymentDTO}
     */
    ParadexPagedResultsResponseDTO<ParadexFundingPaymentDTO> getFundingPaymentHistory(ParadexAccount account, ParadexHistoryRequestDTO requestDTO);

}
