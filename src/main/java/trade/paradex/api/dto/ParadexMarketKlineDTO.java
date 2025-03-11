package trade.paradex.api.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

import java.io.IOException;

import static trade.paradex.api.dto.ParadexMarketKlineDTO.KlineDeserializer;

@Value
@JsonDeserialize(using = KlineDeserializer.class)
public class ParadexMarketKlineDTO {

    long timestamp;
    double open;
    double high;
    double low;
    double close;
    double volume;

    public static class KlineDeserializer extends JsonDeserializer<ParadexMarketKlineDTO> {
        @Override
        public ParadexMarketKlineDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            if (!node.isArray()) {
                throw new IllegalArgumentException("Expected array node but got: " + node);
            }

            if (node.size() != 6) {
                throw new IllegalArgumentException("Wrong data. Expected 6 elements but got " + node);
            }

            long timestamp = node.get(0).longValue();
            double open = node.get(1).doubleValue();
            double high = node.get(2).doubleValue();
            double low = node.get(3).doubleValue();
            double close = node.get(4).doubleValue();
            double volume = node.get(5).doubleValue();

            return new ParadexMarketKlineDTO(timestamp, open, high, low, close, volume);
        }
    }
}
