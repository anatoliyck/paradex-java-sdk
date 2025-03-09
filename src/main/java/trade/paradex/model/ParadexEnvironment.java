package trade.paradex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParadexEnvironment {

    MAINNET("https://api.prod.paradex.trade", "wss://ws.api.prod.paradex.trade", "0x505249564154455f534e5f50415241434c4541525f4d41494e4e4554"),
    TESTNET("https://api.testnet.paradex.trade", "wss://ws.api.testnet.paradex.trade", "0x505249564154455f534e5f504f54435f5345504f4c4941");

    private final String url;
    private final String wsUrl;
    private final String chainId;

}
