package trade.paradex.api.market;

import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexFundingDataHistoryRequestDTO;

public interface ParadexMarketAPI {

    /**
     * Get the best bid/ask for the given market.
     *
     * @param market market(e.g. BTC-USD-PERP)
     * @return {@link ParadexMarketBestBidOfferDTO}
     */
    ParadexMarketBestBidOfferDTO getMarketBBO(String market);

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
     * @param requestDTO {@link ParadexFundingDataHistoryRequestDTO}
     * @return List of {@link ParadexFundingDataDTO}
     */
    ParadexPagedResultsResponseDTO<ParadexFundingDataDTO> getFundingDataHistory(ParadexFundingDataHistoryRequestDTO requestDTO);
}
