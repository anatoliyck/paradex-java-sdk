import trade.paradex.ParadexClient;
import trade.paradex.api.dto.ParadexMarketKlineDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.model.ParadexEnvironment;

import java.time.Duration;
import java.time.Instant;

public class GetMarketKlinesExample {

    public static void main(String[] args) {
        ParadexClient client = ParadexClient.builder(ParadexEnvironment.MAINNET) // or ParadexEnvironment.TESTNET
                .build();

        Instant now = Instant.now();
        long startAt = now.minusSeconds(Duration.ofDays(1).toSeconds()).toEpochMilli();
        long endAt = now.toEpochMilli();
        int resolution = 30; // 30 minutes

        ParadexResultsResponseDTO<ParadexMarketKlineDTO> klines = client.marketAPI().getMarketKlines("BTC-USD-PERP", resolution,
                startAt, endAt, null);
        System.out.println(klines.getResults());
    }
}
