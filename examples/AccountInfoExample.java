import trade.paradex.ParadexClient;
import trade.paradex.api.dto.ParadexAccountInfoDTO;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;

public class AccountInfoExample {

    private static final String ACCOUNT_ADDRESS = "...";     // Paradex account address
    private static final String ACCOUNT_PRIVATE_KEY = "..."; // Paradex account private key

    public static void main(String[] args) {
        ParadexAccount account = new ParadexAccount(ACCOUNT_ADDRESS, ACCOUNT_PRIVATE_KEY);

        ParadexClient client = ParadexClient.builder(ParadexEnvironment.TESTNET) // or ParadexEnvironment.MAINNET
                .build();

        ParadexAccountInfoDTO accountInfo = client.accountAPI().getAccountInfo(account, null);
        System.out.println(accountInfo);
    }
}
