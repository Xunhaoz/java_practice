package com.walnutek.goldpriceandrecovery;

public class GoldPrice {
    private String title = "";
    private double priceTaiwan = 0;
    private double priceGram = 0;
    private double recoveryTaiwan = 0;
    private double recoveryGram = 0;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriceGram(double priceGram) {
        this.priceGram = priceGram;
    }

    public void setPriceTaiwan(double priceTaiwan) {
        this.priceTaiwan = priceTaiwan;
    }

    public void setRecoveryGram(double recoveryGram) {
        this.recoveryGram = recoveryGram;
    }

    public void setRecoveryTaiwan(double recoveryTaiwan) {
        this.recoveryTaiwan = recoveryTaiwan;
    }

    public String getTitle() {
        return title;
    }

    public double getPriceGram() {
        return priceGram;
    }

    public double getPriceTaiwan() {
        return priceTaiwan;
    }

    public double getRecoveryGram() {
        return recoveryGram;
    }

    public double getRecoveryTaiwan() {
        return recoveryTaiwan;
    }
}

