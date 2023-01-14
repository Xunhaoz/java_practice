package com.walnutek.javapractice;

public class Monetary {
    private String currency = "";
    private double cashRateIn = 0.0;
    private double cashRateOut = 0.0;
    private double spotRateIn = 0.0;
    private double spotRateOut = 0.0;

    public Monetary(String currency) {
        this.currency = currency;
    }

    public void setCashRateIn(double cashRateIn) {
        this.cashRateIn = cashRateIn;
    }

    public void setCashRateOut(double cashRateOut) {
        this.cashRateOut = cashRateOut;
    }

    public void setSpotRateIn(double spotRateIn) {
        this.spotRateIn = spotRateIn;
    }

    public void setSpotRateOut(double spotRateOut) {
        this.spotRateOut = spotRateOut;
    }

    public String getCurrency() {
        return currency;
    }

    public double getCashRateIn() {
        return cashRateIn;
    }

    public double getCashRateOut() {
        return cashRateOut;
    }

    public double getSpotRateIn() {
        return spotRateIn;
    }

    public double getSpotRateOut() {
        return spotRateOut;
    }
}
