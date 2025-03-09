package trade.paradex.ws;

import com.fasterxml.jackson.databind.JsonNode;

public interface ParadexSubscriptionListener {

    void onMessage(JsonNode data);

}
