package com.walnutek.turneyprice;

public class TruneyContainer {
    private String title = "";
    private double currentPrice = 0;
    private double quoteChange = 0;
    private double quotePrice = 0;

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getQuoteChange() {
        return quoteChange;
    }

    public double getQuotePrice() {
        return quotePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setQuoteChange(double quoteChange) {
        this.quoteChange = quoteChange;
    }

    public void setQuotePrice(double quotePrice) {
        this.quotePrice = quotePrice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
