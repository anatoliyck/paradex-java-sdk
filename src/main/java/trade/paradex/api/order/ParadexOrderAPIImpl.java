package trade.paradex.api.order;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import trade.paradex.api.ParadexBaseAPI;
import trade.paradex.api.auth.ParadexAuthAPI;
import trade.paradex.api.dto.ParadexModifyOrderDTO;
import trade.paradex.api.dto.ParadexOrderDTO;
import trade.paradex.api.dto.ParadexPagedResultsResponseDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.api.dto.request.ParadexCreateOrderRequestDTO;
import trade.paradex.api.dto.request.ParadexModifyOrderRequestDTO;
import trade.paradex.api.dto.request.ParadexOrdersHistoryRequestDTO;
import trade.paradex.http.HttpClient;
import trade.paradex.http.resolver.HttpClientResolver;
import trade.paradex.model.ParadexAccount;
import trade.paradex.utils.JsonUtils;
import trade.paradex.utils.ParadexUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class ParadexOrderAPIImpl extends ParadexBaseAPI implements ParadexOrderAPI {

    private final String url;
    private final String chainId;
    private final ParadexAuthAPI authAPI;
    private final HttpClientResolver httpClientResolver;

    @Override
    public ParadexResultsResponseDTO<ParadexOrderDTO> getOrders(ParadexAccount account, String market) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = market != null ? Map.of("market", market) : null;

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/orders", headers, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetOrdersTypeReference.INSTANCE);
    }

    @Override
    public ParadexOrderDTO getOrder(ParadexAccount account, String paradexOrderId) {
        if (Objects.isNull(paradexOrderId)) {
            throw new IllegalArgumentException("paradexOrderId is null but required!");
        }

        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/orders/" + paradexOrderId, headers, null);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, OrderTypeReference.INSTANCE);
    }

    @Override
    public ParadexOrderDTO getOrderByClientId(ParadexAccount account, String clientId) {
        if (Objects.isNull(clientId)) {
            throw new IllegalArgumentException("clientId is null but required!");
        }

        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        String fullUrl = url + "/v1/orders/by_client_id/" + URLEncoder.encode(clientId, StandardCharsets.UTF_8);
        var response = httpClient.get(fullUrl, headers, null);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, OrderTypeReference.INSTANCE);
    }

    @Override
    public ParadexPagedResultsResponseDTO<ParadexOrderDTO> getOrdersHistory(ParadexAccount account, ParadexOrdersHistoryRequestDTO requestDTO) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = prepareQueryParams(requestDTO);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.get(url + "/v1/orders-history", headers, queryParams);

        String body = processResponse(response);

        return JsonUtils.deserialize(body, GetOrdersHistoryTypeReference.INSTANCE);
    }

    @Override
    public ParadexOrderDTO createOrder(ParadexAccount account, ParadexCreateOrderRequestDTO order) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        String body = JsonUtils.serialize(buildOrderPayload(account, order));
        var response = httpClient.post(url + "/v1/orders", headers, body);

        String responseBody = processResponse(response);

        return JsonUtils.deserialize(responseBody, OrderTypeReference.INSTANCE);
    }

    @Override
    public ParadexModifyOrderDTO modifyOrder(ParadexAccount account, ParadexModifyOrderRequestDTO modifyOrder) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        String body = JsonUtils.serialize(buildModifyOrderPayload(account, modifyOrder));
        String fullUrl = url + "/v1/orders/" + URLEncoder.encode(modifyOrder.getId(), StandardCharsets.UTF_8);
        var response = httpClient.put(fullUrl, headers, body);

        String responseBody = processResponse(response);

        return JsonUtils.deserialize(responseBody, ModifyOrderTypeReference.INSTANCE);
    }

    @Override
    public void cancelAllOrders(ParadexAccount account, String market) {
        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);
        Map<String, String> queryParams = market != null ? Map.of("market", market) : null;

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.delete(url + "/v1/orders", headers, queryParams);
        processResponse(response);
    }

    @Override
    public void cancelOrder(ParadexAccount account, String paradexOrderId) {
        if (Objects.isNull(paradexOrderId)) {
            throw new IllegalArgumentException("Paradex order id is null but required!");
        }

        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.delete(url + "/v1/orders/" + paradexOrderId, headers, null);
        processResponse(response);
    }

    @Override
    public void cancelOrderByClientId(ParadexAccount account, String clientId) {
        if (Objects.isNull(clientId)) {
            throw new IllegalArgumentException("clientid is null but required!");
        }

        String jwtToken = authAPI.auth(account);

        Map<String, String> headers = ParadexUtils.prepareHeaders(jwtToken);

        HttpClient httpClient = httpClientResolver.resolve(account);
        var response = httpClient.delete(url + "/v1/orders/by_client_id/" + clientId, headers, null);

        processResponse(response);
    }


    private Map<String, String> prepareQueryParams(ParadexOrdersHistoryRequestDTO requestDTO) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("page_size", String.valueOf(requestDTO.getPageSize()));

        if (requestDTO.getClientId() != null) {
            queryParams.put("client_id", requestDTO.getClientId());
        }
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
        if (requestDTO.getStatus() != null) {
            queryParams.put("status", requestDTO.getStatus());
        }
        if (requestDTO.getOrderSide() != null) {
            queryParams.put("side", requestDTO.getOrderSide().name());
        }
        if (requestDTO.getOrderType() != null) {
            queryParams.put("type", requestDTO.getOrderType().name());
        }
        return queryParams;
    }

    private Map<String, Object> buildOrderPayload(ParadexAccount account, ParadexCreateOrderRequestDTO requestDTO) {
        long now = System.currentTimeMillis();
        Map<String, Object> order = new HashMap<>();

        order.put("market", requestDTO.getMarket());
        order.put("side", requestDTO.getOrderSide());
        order.put("size", String.valueOf(requestDTO.getSize()));
        order.put("type", requestDTO.getOrderType());
        order.put("instruction", requestDTO.getInstruction());
        if (requestDTO.getPrice() != null) {
            order.put("price", String.valueOf(requestDTO.getPrice()));
        }
        if (requestDTO.getFlags() != null && !requestDTO.getFlags().isEmpty()) {
            order.put("flags", requestDTO.getFlags().stream().toArray());
        }
        if (requestDTO.getClientId() != null) {
            order.put("client_id", requestDTO.getClientId());
        }
        if (requestDTO.getRecvWindow() != null) {
            order.put("recv_window", requestDTO.getRecvWindow());
        }
        if (requestDTO.getStp() != null) {
            order.put("stp", requestDTO.getStp());
        }
        if (requestDTO.getTriggerPrice() != null) {
            order.put("trigger_price", String.valueOf(requestDTO.getTriggerPrice()));
        }

        var message = buildOrderMessage(chainId, now,
                requestDTO.getMarket(),
                requestDTO.getOrderSide().name(),
                requestDTO.getOrderType().name(),
                BigDecimal.valueOf(requestDTO.getSize()),
                requestDTO.getPrice() != null ? BigDecimal.valueOf(requestDTO.getPrice()) : BigDecimal.ZERO
        );
        var pair = ParadexUtils.getSignature(account, message);
        order.put("signature_timestamp", now);
        order.put("signature", pair.getLeft());
        return order;
    }

    private Map<String, Object> buildModifyOrderPayload(ParadexAccount account, ParadexModifyOrderRequestDTO requestDTO) {
        long now = System.currentTimeMillis();
        Map<String, Object> payload = new HashMap<>();

        payload.put("id", requestDTO.getId());
        payload.put("market", requestDTO.getMarket());
        payload.put("price", String.valueOf(requestDTO.getPrice()));
        payload.put("side", requestDTO.getOrderSide());
        payload.put("size", String.valueOf(requestDTO.getSize()));
        payload.put("type", requestDTO.getOrderType());

        var message = buildModifyOrderMessage(chainId, now,
                requestDTO.getId(),
                requestDTO.getMarket(),
                requestDTO.getOrderSide().name(),
                requestDTO.getOrderType().name(),
                BigDecimal.valueOf(requestDTO.getSize()),
                BigDecimal.valueOf(requestDTO.getPrice())
        );
        var pair = ParadexUtils.getSignature(account, message);
        payload.put("signature_timestamp", now);
        payload.put("signature", pair.getLeft());
        return payload;
    }

    private String buildOrderMessage(
            String chainId,
            long timestamp,
            String market,
            String side,
            String orderType,
            BigDecimal size,
            BigDecimal price
    ) {
        var chainSide = Objects.equals(side, "BUY") ? "1" : "2";
        var chainPrice = Objects.equals(orderType, "MARKET") ? "0" : price.scaleByPowerOfTen(8).toBigInteger().toString();
        var chainSize = size.scaleByPowerOfTen(8).toBigInteger().toString();

        return String.format("{\n" +
                "  \"message\": {\n" +
                "    \"timestamp\": %d,\n" +
                "    \"market\": \"%s\",\n" +
                "    \"side\": %s,\n" +
                "    \"orderType\": \"%s\",\n" +
                "    \"size\": \"%s\",\n" +
                "    \"price\": %s\n" +
                "  },\n" +
                "  \"domain\": {\n" +
                "    \"name\": \"Paradex\",\n" +
                "    \"chainId\": \"%s\",\n" +
                "    \"version\": \"1\"\n" +
                "  },\n" +
                "  \"primaryType\": \"Order\",\n" +
                "  \"types\": {\n" +
                "    \"StarkNetDomain\": [\n" +
                "      {\n" +
                "        \"name\": \"name\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"chainId\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"version\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"Order\": [\n" +
                "      {\n" +
                "        \"name\": \"timestamp\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"market\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"side\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"orderType\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"size\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"price\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}", timestamp, market, chainSide, orderType, chainSize, chainPrice, chainId);
    }

    private String buildModifyOrderMessage(
            String chainId,
            long timestamp,
            String orderId,
            String market,
            String side,
            String orderType,
            BigDecimal size,
            BigDecimal price
    ) {
        var chainSide = Objects.equals(side, "BUY") ? "1" : "2";
        var chainPrice = price.scaleByPowerOfTen(8).toBigInteger().toString();
        var chainSize = size.scaleByPowerOfTen(8).toBigInteger().toString();

        return String.format("{\n" +
                "  \"message\": {\n" +
                "    \"timestamp\": %d,\n" +
                "    \"market\": \"%s\",\n" +
                "    \"side\": %s,\n" +
                "    \"orderType\": \"%s\",\n" +
                "    \"size\": \"%s\",\n" +
                "    \"price\": %s,\n" +
                "    \"id\": \"%s\"\n" +
                "  },\n" +
                "  \"domain\": {\n" +
                "    \"name\": \"Paradex\",\n" +
                "    \"chainId\": \"%s\",\n" +
                "    \"version\": \"1\"\n" +
                "  },\n" +
                "  \"primaryType\": \"ModifyOrder\",\n" +
                "  \"types\": {\n" +
                "    \"StarkNetDomain\": [\n" +
                "      {\n" +
                "        \"name\": \"name\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"chainId\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"version\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ModifyOrder\": [\n" +
                "      {\n" +
                "        \"name\": \"timestamp\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"market\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"side\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"orderType\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"size\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"price\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"id\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}", timestamp, market, chainSide, orderType, chainSize, chainPrice, orderId, chainId);
    }

    private static class GetOrdersTypeReference extends TypeReference<ParadexResultsResponseDTO<ParadexOrderDTO>> {
        private static final GetOrdersTypeReference INSTANCE = new GetOrdersTypeReference();
    }

    private static class GetOrdersHistoryTypeReference extends TypeReference<ParadexPagedResultsResponseDTO<ParadexOrderDTO>> {
        private static final GetOrdersHistoryTypeReference INSTANCE = new GetOrdersHistoryTypeReference();
    }

    private static class OrderTypeReference extends TypeReference<ParadexOrderDTO> {
        private static final OrderTypeReference INSTANCE = new OrderTypeReference();
    }

    private static class ModifyOrderTypeReference extends TypeReference<ParadexModifyOrderDTO> {
        private static final ModifyOrderTypeReference INSTANCE = new ModifyOrderTypeReference();
    }
}
