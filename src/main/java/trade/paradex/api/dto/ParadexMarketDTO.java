package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParadexMarketDTO {

    @JsonProperty("asset_kind")
    private String assetKind;

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("chain_details")
    private ChainDetailsDTO chainDetails;

    @JsonProperty("clamp_rate")
    private BigDecimal clampRate;

    @JsonProperty("delta1_cross_margin_params")
    private CrossMarginParamsDTO delta1CrossMarginParams;

    @JsonProperty("expiry_at")
    private long expiryAt;

    @JsonProperty("interest_rate")
    private BigDecimal interestRate;

    @JsonProperty("market_kind")
    private String marketKind;

    @JsonProperty("max_funding_rate")
    private BigDecimal maxFundingRate;

    @JsonProperty("max_funding_rate_change")
    private BigDecimal maxFundingRateChange;

    @JsonProperty("max_open_orders")
    private int maxOpenOrders;

    @JsonProperty("max_order_size")
    private BigDecimal maxOrderSize;

    @JsonProperty("max_tob_spread")
    private BigDecimal maxTobSpread;

    @JsonProperty("min_notional")
    private BigDecimal minNotional;

    @JsonProperty("open_at")
    private long openAt;

    @JsonProperty("option_cross_margin_params")
    private OptionCrossMarginParamsDTO optionCrossMarginParams;

    @JsonProperty("option_type")
    private String optionType;

    @JsonProperty("oracle_ewma_factor")
    private BigDecimal oracleEwmaFactor;

    @JsonProperty("order_size_increment")
    private BigDecimal orderSizeIncrement;

    @JsonProperty("position_limit")
    private BigDecimal positionLimit;

    @JsonProperty("price_bands_width")
    private BigDecimal priceBandsWidth;

    @JsonProperty("price_feed_id")
    private String priceFeedId;

    @JsonProperty("price_tick_size")
    private BigDecimal priceTickSize;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("settlement_currency")
    private String settlementCurrency;

    @JsonProperty("strike_price")
    private BigDecimal strikePrice;

    @JsonProperty("symbol")
    private String symbol;

    @Data
    public static class ChainDetailsDTO {

        @JsonProperty("collateral_address")
        private String collateralAddress;

        @JsonProperty("contract_address")
        private String contractAddress;

        @JsonProperty("fee_account_address")
        private String feeAccountAddress;

        @JsonProperty("fee_maker")
        private BigDecimal feeMaker;

        @JsonProperty("fee_taker")
        private BigDecimal feeTaker;

        @JsonProperty("insurance_fund_address")
        private String insuranceFundAddress;

        @JsonProperty("liquidation_fee")
        private BigDecimal liquidationFee;

        @JsonProperty("oracle_address")
        private String oracleAddress;

        @JsonProperty("symbol")
        private String symbol;

    }

    @Data
    public static class CrossMarginParamsDTO {

        @JsonProperty("imf_base")
        private BigDecimal imfBase;

        @JsonProperty("imf_factor")
        private BigDecimal imfFactor;

        @JsonProperty("imf_shift")
        private BigDecimal imfShift;

        @JsonProperty("mmf_factor")
        private BigDecimal mmfFactor;

    }

    @Data
    public static class OptionCrossMarginParamsDTO {

        @JsonProperty("imf")
        private MarginFactorsDTO imf;

        @JsonProperty("mmf")
        private MarginFactorsDTO mmf;

    }

    @Data
    public static class MarginFactorsDTO {

        @JsonProperty("long_itm")
        private BigDecimal longItm;

        @JsonProperty("premium_multiplier")
        private BigDecimal premiumMultiplier;

        @JsonProperty("short_itm")
        private BigDecimal shortItm;

        @JsonProperty("short_otm")
        private BigDecimal shortOtm;

        @JsonProperty("short_put_cap")
        private BigDecimal shortPutCap;

    }
}
