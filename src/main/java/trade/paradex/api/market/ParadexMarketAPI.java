package trade.paradex.api.market;

import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexHistoryRequestDTO;

public interface ParadexMarketAPI {

    /**
     * Get the best bid/ask for the given market.
     *
     * @param market market(e.g. BTC-USD-PERP)
     * @return {@link ParadexMarketBestBidOfferDTO}
     */
    ParadexMarketBestBidOfferDTO getMarketBBO(String market);

    /**
     * Get OHLCV(klines) aggregated data for given market.
     *
     * @param market     specific market(e.g. BTC-USD-PERP)
     * @param resolution klines resolution in minutes: 1, 3, 5, 15, 30, 60
     * @param startAt    start timestamp
     * @param endAt      end timestamp
     * @param priceKind  one of [last, mark]. Optional
     */
    ParadexResultsResponseDTO<ParadexMarketKlineDTO> getMarketKlines(String market, int resolution,
                                                                     long startAt, long endAt, String priceKind);

    /**
     * Get markets static data component.
     *
     * @param market specific market(e.g. BTC-USD-PERP). Optional
     * @return List of {@link ParadexMarketDTO}
     */
    ParadexResultsResponseDTO<ParadexMarketDTO> getMarkets(String market);

    /**
     * Get markets dynamic data component.
     *
     * @param market name of the market for which summary is requested (for all available markets use `ALL`)
     * @param start  start time (unix time millisecond). Optional
     * @param end    end time (unix time millisecond). Optional
     * @return List of {@link ParadexMarketSummaryDTO}
     */
    ParadexResultsResponseDTO<ParadexMarketSummaryDTO> getMarketSummary(String market, Long start, Long end);

    /**
     * Get snapshot of the orderbook for the given market.
     *
     * @param market market(e.g. BTC-USD-PERP)
     * @param depth  orderbook depth. Optional
     * @return {@link ParadexOrderBookDTO}
     */
    ParadexOrderBookDTO getMarketOrderBook(String market, Integer depth);

    /**
     * Returns list historical funding data by market.
     *
     * @param requestDTO {@link ParadexHistoryRequestDTO}
     * @return List of {@link ParadexFundingDataDTO}
     */
    ParadexPagedResultsResponseDTO<ParadexFundingDataDTO> getFundingDataHistory(ParadexHistoryRequestDTO requestDTO);
}
