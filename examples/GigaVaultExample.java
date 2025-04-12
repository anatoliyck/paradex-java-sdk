import trade.paradex.ParadexClient;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.api.dto.ParadexVaultDTO;
import trade.paradex.model.ParadexEnvironment;

import java.math.BigDecimal;

public class GigaVaultExample {

    private static final String GIGAVAULT_ADDRESS = "0x5f43c92dbe4e995115f351254407e7e84abf04cbe32a536345b9d6c36bc750f";

    /**
     * Get available Gigavault liquidity for deposit.
     */
    public static void main(String[] args) {
        ParadexClient client = ParadexClient.builder(ParadexEnvironment.MAINNET)
                .build();

        ParadexResultsResponseDTO<ParadexVaultDTO> vaults = client.vaultAPI().getVaults(GIGAVAULT_ADDRESS);
        if (vaults.getResults().size() != 1) {
            throw new IllegalArgumentException("Expected exactly one vault but: " + vaults.getResults().size());
        }

        ParadexVaultDTO gigavault = vaults.getResults().get(0);

        BigDecimal amount = client.vaultAPI().getVaultBalance(GIGAVAULT_ADDRESS, null).getResults().get(0).getSize();
        for (var strategy : gigavault.getStrategies()) {
            amount = amount.add(client.vaultAPI().getVaultBalance(GIGAVAULT_ADDRESS, strategy.getAddress()).getResults().get(0).getSize());
        }

        BigDecimal availableLiquidityForDeposit = gigavault.getMaxTvl().subtract(amount);
        if (availableLiquidityForDeposit.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("No available liquidity for deposit...");
        } else {
            System.out.println("Available liquidity for deposit: " + availableLiquidityForDeposit);
        }
    }
}
