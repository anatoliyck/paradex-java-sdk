package trade.paradex.api.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

import java.io.IOException;
import java.math.BigDecimal;

import static trade.paradex.api.dto.ParadexMarketKlineDTO.KlineDeserializer;

@Value
@JsonDeserialize(using = KlineDeserializer.class)
public class ParadexMarketKlineDTO {

    long timestamp;
    BigDecimal open;
    BigDecimal high;
    BigDecimal low;
    BigDecimal close;
    BigDecimal volume;

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
            BigDecimal open = node.get(1).decimalValue();
            BigDecimal high = node.get(2).decimalValue();
            BigDecimal low = node.get(3).decimalValue();
            BigDecimal close = node.get(4).decimalValue();
            BigDecimal volume = node.get(5).decimalValue();

            return new ParadexMarketKlineDTO(timestamp, open, high, low, close, volume);
        }
    }
}
