package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ParadexVaultDTO {

    @JsonProperty("address")
    private String address;

    @JsonProperty("created_at")
    private long createdAt;

    @JsonProperty("description")
    private String description;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("last_updated_at")
    private long lastUpdatedAt;

    @JsonProperty("lockup_period")
    private int lockupPeriod;

    @JsonProperty("max_tvl")
    private long maxTvl;

    @JsonProperty("name")
    private String name;

    @JsonProperty("operator_account")
    private String operatorAccount;

    @JsonProperty("owner_account")
    private String ownerAccount;

    @JsonProperty("profit_share")
    private int profitShare;

    @JsonProperty("status")
    private String status;

    @JsonProperty("strategies")
    private List<StrategyDTO> strategies;

    @JsonProperty("token_address")
    private String tokenAddress;

    @Data
    public static class StrategyDTO {

        @JsonProperty("address")
        private String address;

        @JsonProperty("name")
        private String name;

    }
}
