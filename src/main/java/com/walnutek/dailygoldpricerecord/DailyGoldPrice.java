package com.walnutek.dailygoldpricerecord;

public class DailyGoldPrice {
    private double goldAskingPrice = 0;
    private double goldAskingPriceChange = 0;
    private double goldBuy = 0;
    private double goldSell = 0;
    private double pricePreGram = 0;
    private double platinumPrice = 0;
    private double platinumPriceChange = 0;
    private double palladiumPrice = 0;
    private double palladiumPriceChange = 0;
    private double silverPrice = 0;
    private double silverPriceChange = 0;

    public void setGoldAskingPrice(double goldAskingPrice) {
        this.goldAskingPrice = goldAskingPrice;
    }

    public void setGoldAskingPriceChange(double goldAskingPriceChange) {
        this.goldAskingPriceChange = goldAskingPriceChange;
    }

    public void setGoldBuy(double goldBuy) {
        this.goldBuy = goldBuy;
    }

    public void setGoldSell(double goldSell) {
        this.goldSell = goldSell;
    }

    public void setPalladiumPrice(double palladiumPrice) {
        this.palladiumPrice = palladiumPrice;
    }

    public void setPalladiumPriceChange(double palladiumPriceChange) {
        this.palladiumPriceChange = palladiumPriceChange;
    }

    public void setPlatinumPrice(double platinumPrice) {
        this.platinumPrice = platinumPrice;
    }

    public void setPlatinumPriceChange(double platinumPriceChange) {
        this.platinumPriceChange = platinumPriceChange;
    }

    public void setPricePreGram(double pricePreGram) {
        this.pricePreGram = pricePreGram;
    }

    public void setSilverPrice(double silverPrice) {
        this.silverPrice = silverPrice;
    }

    public void setSilverPriceChange(double silverPriceChange) {
        this.silverPriceChange = silverPriceChange;
    }

    public double getGoldAskingPrice() {
        return goldAskingPrice;
    }
    public double getGoldAskingPriceChange() {
        return goldAskingPriceChange;
    }

    public double getGoldBuy() {
        return goldBuy;
    }

    public double getGoldSell() {
        return goldSell;
    }

    public double getPalladiumPrice() {
        return palladiumPrice;
    }

    public double getPalladiumPriceChange() {
        return palladiumPriceChange;
    }

    public double getPlatinumPrice() {
        return platinumPrice;
    }

    public double getPlatinumPriceChange() {
        return platinumPriceChange;
    }

    public double getPricePreGram() {
        return pricePreGram;
    }

    public double getSilverPrice() {
        return silverPrice;
    }

    public double getSilverPriceChange() {
        return silverPriceChange;
    }
}
