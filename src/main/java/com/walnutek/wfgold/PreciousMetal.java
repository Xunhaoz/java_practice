package com.walnutek.wfgold;

public class PreciousMetal {
    private String title = "";
    private double Bid = 0;
    private double Ask = 0;
    private double high = 0;
    private double low = 0;

    public void setLow(double low) {
        this.low = low;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAsk(double ask) {
        Ask = ask;
    }

    public void setBid(double bid) {
        Bid = bid;
    }

    public double getLow() {
        return low;
    }

    public double getHigh() {
        return high;
    }

    public String getTitle() {
        return title;
    }

    public double getAsk() {
        return Ask;
    }

    public double getBid() {
        return Bid;
    }
}
