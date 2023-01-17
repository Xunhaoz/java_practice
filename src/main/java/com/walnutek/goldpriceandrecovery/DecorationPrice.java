package com.walnutek.goldpriceandrecovery;

public class DecorationPrice {
    private String title = "";
    private double price = 0;
    private double recovery = 0;

    DecorationPrice(String title, double price, double recovery){
        setTitle(title);
        setPrice(price);
        setRecovery(recovery);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRecovery(double recovery) {
        this.recovery = recovery;
    }

    public String getTitle() {
        return title;
    }

    public double getRecovery() {
        return recovery;
    }

    public double getPrice() {
        return price;
    }
}
