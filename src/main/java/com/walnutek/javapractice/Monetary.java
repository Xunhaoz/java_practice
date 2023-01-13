package com.walnutek.javapractice;

public class Monetary {
    private String currency;
    private Float cashRateIn = null;
    private Float cashRateOut = null;
    private Float spotRateIn = null;
    private Float spotRateOut = null;

    private float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Monetary(String currency) {
        this.currency = currency;
    }

    public void setCashRateIn(String cashRateIn) {
        this.cashRateIn = parseFloat(cashRateIn);
    }

    public void setCashRateOut(String cashRateOut) {
        this.cashRateOut = parseFloat(cashRateOut);
    }

    public void setSpotRateIn(String spotRateIn) {
        this.spotRateIn = parseFloat(spotRateIn);
    }

    public void setSpotRateOut(String spotRateOut) {
        this.spotRateOut = parseFloat(spotRateOut);
    }

    public String getCurrency() {
        return currency;
    }

    public Float getCashRateIn() {
        return cashRateIn;
    }

    public Float getCashRateOut() {
        return cashRateOut;
    }

    public Float getSpotRateIn() {
        return spotRateIn;
    }

    public Float getSpotRateOut() {
        return spotRateOut;
    }
}
