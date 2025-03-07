package trade.paradex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "address")
@AllArgsConstructor
public class ParadexAccount {

    private final String address;
    private final String privateKey;

}
