package trade.paradex.api.market;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.api.dto.*;
import trade.paradex.api.dto.request.ParadexHistoryRequestDTO;
import trade.paradex.http.HttpClient;
import trade.paradex.utils.JsonUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ParadexMarketAPIImpl extends ParadexBaseAPI implements ParadexMarketAPI {

    private final String url;
    private final HttpClient httpClient;

    @Override
    public ParadexMarketBestBidOfferDTO getMarketBBO(String market) {
        if (market == null || market.isEmpty()) {
            throw new IllegalArgumentException("market cannot be null or empty");
        }

        String fullUrl = url + "/v1/bbo/" + URLEncoder.encode(market, StandardCharsets.UTF_8);
        var response = httpClient.get(fullUrl, null, null);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetMarketBestBidOfferTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexMarketKlineDTO> getMarketKlines(String market, int resolution,
                                                                            long startAt, long endAt, String priceKind) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("symbol", market);
        queryParams.put("resolution", String.valueOf(resolution));
        queryParams.put("start_at", String.valueOf(startAt));
        queryParams.put("end_at", String.valueOf(endAt));
        if (priceKind != null) {
            queryParams.put("price_kind", priceKind);
        }

        var response = httpClient.get(url + "/v1/markets/klines", null, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetMarketKlinesTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexMarketDTO> getMarkets(String market) {
        Map<String, String> queryParams = market != null ? Map.of("market", market) : null;

        var response = httpClient.get(url + "/v1/markets", null, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetMarketTypeReference.INSTANCE);
    }

    @Override
    public ParadexResultsResponseDTO<ParadexMarketSummaryDTO> getMarketSummary(String market, Long start, Long end) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("market", market);
        if (start != null) {
            queryParams.put("start", String.valueOf(start));
        }
        if (end != null) {
            queryParams.put("end", String.valueOf(end));
        }

        var response = httpClient.get(url + "/v1/markets/summary", null, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetMarketSummaryTypeReference.INSTANCE);
    }

    @Override
    public ParadexOrderBookDTO getMarketOrderBook(String market, Integer depth) {
        if (market == null || market.isEmpty()) {
            throw new IllegalArgumentException("Market cannot be null or empty");
        }

        Map<String, String> queryParams = depth != null ? Map.of("depth", String.valueOf(depth)) : null;

        String fullUrl = url + "/v1/orderbook/" + URLEncoder.encode(market, StandardCharsets.UTF_8);
        var response = httpClient.get(fullUrl, null, queryParams);

        String body = processResponse(response);
        return JsonUtils.deserialize(body, GetOrderBookTypeReference.INSTANCE);
    }

    @Override
    public ParadexPagedResultsResponseDTO<ParadexFundingDataDTO> getFundingDataHistory(ParadexHistoryRequestDTO requestDTO) {
        if (requestDTO.getMarket() == null) {
            throw new IllegalArgumentException("market cannot be null");
        }

        Map<String, String> queryParams = prepareQueryParams(requestDTO);
        var response = httpClient.get(url + "/v1/funding/data", null, queryParams);

        String body = processResponse(response);
        return JsonUtils.deserialize(body, GetFundingDataHistoryTypeReference.INSTANCE);
    }

    private Map<String, String> prepareQueryParams(ParadexHistoryRequestDTO requestDTO) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("page_size", String.valueOf(requestDTO.getPageSize()));
        if (requestDTO.getCursor() != null) {
            queryParams.put("cursor", requestDTO.getCursor());
        }
        if (requestDTO.getStartAt() != null) {
            queryParams.put("start_at", String.valueOf(requestDTO.getStartAt()));
        }
        if (requestDTO.getEndAt() != null) {
            queryParams.put("end_at", String.valueOf(requestDTO.getEndAt()));
        }
        if (requestDTO.getMarket() != null) {
            queryParams.put("market", requestDTO.getMarket());
        }

        return queryParams;
    }

    private static class GetMarketBestBidOfferTypeReference extends TypeReference<ParadexMarketBestBidOfferDTO> {
        private static final GetMarketBestBidOfferTypeReference INSTANCE = new GetMarketBestBidOfferTypeReference();
    }

    private static class GetMarketKlinesTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexMarketKlineDTO>> {
        private static final GetMarketKlinesTypeReference INSTANCE = new GetMarketKlinesTypeReference();
    }

    private static class GetMarketTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexMarketDTO>> {
        private static final GetMarketTypeReference INSTANCE = new GetMarketTypeReference();
    }

    private static class GetMarketSummaryTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexMarketSummaryDTO>> {
        private static final GetMarketSummaryTypeReference INSTANCE = new GetMarketSummaryTypeReference();
    }

    private static class GetOrderBookTypeReference extends TypeReference<ParadexOrderBookDTO> {
        private static final GetOrderBookTypeReference INSTANCE = new GetOrderBookTypeReference();
    }

    private static class GetFundingDataHistoryTypeReference extends TypeReference<ParadexPagedResultsResponseDTO<ParadexFundingDataDTO>> {
        private static final GetFundingDataHistoryTypeReference INSTANCE = new GetFundingDataHistoryTypeReference();
    }

}
