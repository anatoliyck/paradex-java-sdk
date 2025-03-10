package trade.paradex.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParadexMarketDTO {

    @JsonProperty("asset_kind")
    private String assetKind;

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("chain_details")
    private ChainDetailsDTO chainDetails;

    @JsonProperty("clamp_rate")
    private double clampRate;

    @JsonProperty("delta1_cross_margin_params")
    private CrossMarginParamsDTO delta1CrossMarginParams;

    @JsonProperty("expiry_at")
    private long expiryAt;

    @JsonProperty("interest_rate")
    private double interestRate;

    @JsonProperty("market_kind")
    private String marketKind;

    @JsonProperty("max_funding_rate")
    private double maxFundingRate;

    @JsonProperty("max_funding_rate_change")
    private double maxFundingRateChange;

    @JsonProperty("max_open_orders")
    private int maxOpenOrders;

    @JsonProperty("max_order_size")
    private double maxOrderSize;

    @JsonProperty("max_tob_spread")
    private double maxTobSpread;

    @JsonProperty("min_notional")
    private double minNotional;

    @JsonProperty("open_at")
    private long openAt;

    @JsonProperty("option_cross_margin_params")
    private OptionCrossMarginParamsDTO optionCrossMarginParams;

    @JsonProperty("option_type")
    private String optionType;

    @JsonProperty("oracle_ewma_factor")
    private double oracleEwmaFactor;

    @JsonProperty("order_size_increment")
    private double orderSizeIncrement;

    @JsonProperty("position_limit")
    private int positionLimit;

    @JsonProperty("price_bands_width")
    private double priceBandsWidth;

    @JsonProperty("price_feed_id")
    private String priceFeedId;

    @JsonProperty("price_tick_size")
    private double priceTickSize;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("settlement_currency")
    private String settlementCurrency;

    @JsonProperty("strike_price")
    private double strikePrice;

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
        private double feeMaker;

        @JsonProperty("fee_taker")
        private double feeTaker;

        @JsonProperty("insurance_fund_address")
        private String insuranceFundAddress;

        @JsonProperty("liquidation_fee")
        private double liquidationFee;

        @JsonProperty("oracle_address")
        private String oracleAddress;

        @JsonProperty("symbol")
        private String symbol;

    }

    @Data
    public static class CrossMarginParamsDTO {

        @JsonProperty("imf_base")
        private double imfBase;

        @JsonProperty("imf_factor")
        private double imfFactor;

        @JsonProperty("imf_shift")
        private double imfShift;

        @JsonProperty("mmf_factor")
        private double mmfFactor;

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
        private double longItm;

        @JsonProperty("premium_multiplier")
        private double premiumMultiplier;

        @JsonProperty("short_itm")
        private double shortItm;

        @JsonProperty("short_otm")
        private double shortOtm;

        @JsonProperty("short_put_cap")
        private double shortPutCap;

    }
}
