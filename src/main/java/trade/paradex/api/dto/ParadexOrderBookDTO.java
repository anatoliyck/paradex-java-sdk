package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Value;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ParadexOrderBookDTO {

    @JsonProperty("market")
    private String market;

    @JsonProperty("seq_no")
    private long seqNo;

    @JsonProperty("last_updated_at")
    private long lastUpdatedAt;

    @JsonProperty("asks")
    @JsonDeserialize(using = OrderListDeserializer.class)
    private List<OrderDTO> asks;

    @JsonProperty("bids")
    @JsonDeserialize(using = OrderListDeserializer.class)
    private List<OrderDTO> bids;

    @Value
    public static class OrderDTO {
        double price;
        double size;
    }

    public static class OrderListDeserializer extends JsonDeserializer<List<OrderDTO>> {
        @Override
        public List<OrderDTO> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            List<OrderDTO> orders = new ArrayList<>();
            for (JsonNode arrayNode : node) {
                BigDecimal price = new BigDecimal(arrayNode.get(0).asText());
                BigDecimal size = new BigDecimal(arrayNode.get(1).asText());
                orders.add(new OrderDTO(price.doubleValue(), size.doubleValue()));
            }
            return orders;
        }
    }
}
