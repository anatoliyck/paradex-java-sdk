import trade.paradex.ParadexClient;
import trade.paradex.api.dto.MarginType;
import trade.paradex.api.dto.ParadexAccountMarginDTO;
import trade.paradex.api.dto.request.ParadexUpdateAccountMarginDTO;
import trade.paradex.model.ParadexAccount;
import trade.paradex.model.ParadexEnvironment;

public class AccountMarginExample {

    private static final String ACCOUNT_ADDRESS = "..";     // Paradex account address
    private static final String ACCOUNT_PRIVATE_KEY = ".."; // Paradex account private key

    public static void main(String[] args) {
        ParadexAccount account = new ParadexAccount(ACCOUNT_ADDRESS, ACCOUNT_PRIVATE_KEY);

        ParadexClient client = ParadexClient.builder(ParadexEnvironment.TESTNET) // or ParadexEnvironment.MAINNET
                .build();

        ParadexAccountMarginDTO accountMargin = client.accountAPI().getAccountMargin(account, "SUI-USD-PERP");
        System.out.println(accountMargin);

        // update SUI market to x7 leverage
        ParadexUpdateAccountMarginDTO updateAccountMarginRequest = ParadexUpdateAccountMarginDTO.builder()
                .market("SUI-USD-PERP")
                .leverage(7)
                .marginType(MarginType.CROSS)
                .build();
        ParadexAccountMarginDTO updatedAccountMargin = client.accountAPI().updateAccountMargin(account, updateAccountMarginRequest);
        System.out.println(updatedAccountMargin);
    }
}
