package com.walnutek.javapractice;

public class Monetary {
    private String currency;
    private Float cash_rate_in = null;
    private Float cash_rate_out = null;
    private Float spot_rate_in = null;
    private Float spot_rate_out = null;

    public Monetary(String currency){
        this.currency = currency;
    }

    public void setCash_rate_in(Float cash_rate_in) {
        this.cash_rate_in = cash_rate_in;
    }

    public void setCash_rate_out(Float cash_rate_out) {
        this.cash_rate_out = cash_rate_out;
    }

    public void setSpot_rate_in(Float spot_rate_in) {
        this.spot_rate_in = spot_rate_in;
    }

    public void setSpot_rate_out(Float spot_rate_out) {
        this.spot_rate_out = spot_rate_out;
    }

    public String getCurrency() {
        return currency;
    }

    public Float getCash_rate_in() {
        return cash_rate_in;
    }

    public Float getCash_rate_out() {
        return cash_rate_out;
    }

    public Float getSpot_rate_in() {
        return spot_rate_in;
    }

    public Float getSpot_rate_out() {
        return spot_rate_out;
    }
}
