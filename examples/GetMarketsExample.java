import trade.paradex.ParadexClient;
import trade.paradex.api.dto.ParadexMarketDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.model.ParadexEnvironment;

public class GetMarketsExample {

    public static void main(String[] args) {
        ParadexClient client = ParadexClient.builder(ParadexEnvironment.TESTNET) // or ParadexEnvironment.MAINNET
                .build();

        ParadexResultsResponseDTO<ParadexMarketDTO> markets = client.marketAPI().getMarkets(null);
        System.out.println(markets);
    }
}
