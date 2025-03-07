package trade.paradex.api.vault;

import trade.paradex.api.dto.*;
import trade.paradex.model.ParadexAccount;

public interface ParadexVaultAPI {

    /**
     * Get list of available vaults.
     *
     * @param address address of specific vault. Optional
     * @return List of {@link ParadexVaultDTO}
     */
    ParadexResultsResponseDTO<ParadexVaultDTO> getVaults(String address);

    /**
     * Get spot balance of a vault.
     *
     * @param address         vault address
     * @param strategyAddress strategy address. Optional
     * @return List of {@link ParadexVaultBalanceDTO}
     */
    ParadexResultsResponseDTO<ParadexVaultBalanceDTO> getVaultBalance(String address, String strategyAddress);

    /**
     * Get positions opened by the given vault.
     *
     * @param address         vault address
     * @param strategyAddress strategy address. Optional
     * @return List of {@link ParadexPositionDTO}
     */
    ParadexResultsResponseDTO<ParadexPositionDTO> getVaultPositions(String address, String strategyAddress);

    /**
     * Get account summary metrics for all vaults or for the single vault specified by address.
     *
     * @param account vault address. Optional
     * @return List of {@link ParadexVaultAccountSummaryDTO}
     */
    ParadexResultsResponseDTO<ParadexVaultAccountSummaryDTO> getVaultAccountSummary(ParadexAccount account, String address);

    /**
     * Get vaults summary metrics for all vaults or for the single vault specified by address.
     *
     * @param address vault address. Optional
     * @return List of {@link ParadexVaultSummaryDTO}
     */
    ParadexResultsResponseDTO<ParadexVaultSummaryDTO> getVaultSummary(String address);

}
