package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadexSystemConfigDTO {

    @JsonProperty("block_explorer_url")
    private String blockExplorerUrl;

    @JsonProperty("bridged_tokens")
    private List<BridgedToken> bridgedTokens;

    @JsonProperty("environment")
    private String environment;

    @JsonProperty("l1_chain_id")
    private String l1ChainId;

    @JsonProperty("l1_core_contract_address")
    private String l1CoreContractAddress;

    @JsonProperty("l1_operator_address")
    private String l1OperatorAddress;

    @JsonProperty("l1_withdraw_relayer_address")
    private String l1WithdrawRelayerAddress;

    @JsonProperty("l2_withdraw_relayer_address")
    private String l2WithdrawRelayerAddress;

    @JsonProperty("liquidation_fee")
    private BigDecimal liquidationFee;

    @JsonProperty("oracle_address")
    private String oracleAddress;

    @JsonProperty("paraclear_account_hash")
    private String paraclearAccountHash;

    @JsonProperty("paraclear_account_proxy_hash")
    private String paraclearAccountProxyHash;

    @JsonProperty("paraclear_address")
    private String paraclearAddress;

    @JsonProperty("paraclear_decimals")
    private int paraclearDecimals;

    @JsonProperty("partial_liquidation_buffer")
    private BigDecimal partialLiquidationBuffer;

    @JsonProperty("partial_liquidation_share_increment")
    private BigDecimal partialLiquidationShareIncrement;

    @JsonProperty("starknet_chain_id")
    private String starknetChainId;

    @JsonProperty("starknet_fullnode_rpc_url")
    private String starknetFullnodeRpcUrl;

    @JsonProperty("starknet_gateway_url")
    private String starknetGatewayUrl;

    @JsonProperty("universal_deployer_address")
    private String universalDeployerAddress;

    @Data
    public static class BridgedToken {

        @JsonProperty("decimals")
        private int decimals;

        @JsonProperty("l1_bridge_address")
        private String l1BridgeAddress;

        @JsonProperty("l1_token_address")
        private String l1TokenAddress;

        @JsonProperty("l2_bridge_address")
        private String l2BridgeAddress;

        @JsonProperty("l2_token_address")
        private String l2TokenAddress;

        @JsonProperty("name")
        private String name;

        @JsonProperty("symbol")
        private String symbol;

    }

}
