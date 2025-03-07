package trade.paradex.api.order;

import trade.paradex.api.dto.ParadexOrderDTO;
import trade.paradex.api.dto.ParadexPagedResultsResponseDTO;
import trade.paradex.api.dto.ParadexResultsResponseDTO;
import trade.paradex.api.dto.request.ParadexCreateOrderRequestDTO;
import trade.paradex.api.dto.request.ParadexOrdersHistoryRequestDTO;
import trade.paradex.model.ParadexAccount;

public interface ParadexOrderAPI {

    /**
     * Get current account open orders.
     *
     * @param account {@link ParadexAccount}
     * @param market  specific market. Optional
     * @return List of {@link ParadexOrderDTO}
     */
    ParadexResultsResponseDTO<ParadexOrderDTO> getOrders(ParadexAccount account, String market);

    /**
     * Get an order by id. Only return orders in OPEN or NEW status.
     *
     * @param account        {@link ParadexAccount}
     * @param paradexOrderId Paradex order id
     * @return {@link ParadexOrderDTO}
     */
    ParadexOrderDTO getOrder(ParadexAccount account, String paradexOrderId);

    /**
     * Get an order by client id. Only returns orders in OPEN status.
     *
     * @param account  {@link ParadexAccount}
     * @param clientId client id
     * @return {@link  ParadexOrderDTO}
     */
    ParadexOrderDTO getOrderByClientId(ParadexAccount account, String clientId);

    /**
     * Get order history for account.
     *
     * @param account    {@link ParadexAccount}
     * @param requestDTO {@link ParadexOrdersHistoryRequestDTO}
     * @return List of {@link ParadexOrderDTO}
     */
    ParadexPagedResultsResponseDTO<ParadexOrderDTO> getOrdersHistory(ParadexAccount account, ParadexOrdersHistoryRequestDTO requestDTO);

    /**
     * Creates a new order with given parameters.
     *
     * @param account {@link ParadexAccount}
     * @param order   {@link ParadexCreateOrderRequestDTO} order data.
     * @return returns a new {@link ParadexOrderDTO}.
     */
    ParadexOrderDTO createOrder(ParadexAccount account, ParadexCreateOrderRequestDTO order);

    /**
     * Cancel all open orders for specific market(if present), otherwise cancel all orders for all markets.
     *
     * @param account {@link ParadexAccount}
     * @param market  specific market. Optional
     */
    void cancelAllOrders(ParadexAccount account, String market);

    /**
     * Cancel order by Paradex order id.
     *
     * @param account        {@link ParadexAccount}
     * @param paradexOrderId Paradex order id
     */
    void cancelOrder(ParadexAccount account, String paradexOrderId);

    /**
     * Cancel order by client id.
     *
     * @param account  {@link ParadexAccount}
     * @param clientId client id
     */
    void cancelOrderByClientId(ParadexAccount account, String clientId);

}
