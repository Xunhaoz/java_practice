package com.walnutek.allbeautygoldprice;

import java.util.ArrayList;

enum Unit {
    TWDtoQian, USDtoOunce, RMBtoOunce, Defult
}

public class PreciousMetal {
    private String title = "";
    Unit unit = Unit.Defult;
    private double buy = 0;
    private double sell = 0;
    private double priceChange = 0;
    private double high = 0;
    private double low = 0;
    private double tradingVolume = 0;

    public PreciousMetal(String title, Unit unit) {
        this.title = title;
        this.unit = unit;
    }

    private boolean checkUnit(Unit unit) {
        return this.unit != Unit.Defult && this.unit != unit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTradingVolume(double tradingVolume, Unit unit) {
        if (checkUnit(unit)) return;
        this.tradingVolume = tradingVolume;
    }

    public void setBuy(double buy, Unit unit) {
        if (checkUnit(unit)) return;
        this.buy = buy;
    }

    public void setHigh(double high, Unit unit) {
        if (checkUnit(unit)) return;
        this.high = high;
    }

    public void setLow(double low, Unit unit) {
        if (checkUnit(unit)) return;
        this.low = low;
    }

    public void setPriceChange(double priceChange, Unit unit) {
        if (checkUnit(unit)) return;
        this.priceChange = priceChange;
    }

    public void setSell(double sell, Unit unit) {
        if (checkUnit(unit)) return;
        this.sell = sell;
    }

    public double getSell() {
        return sell;
    }

    public double getTradingVolume() {
        return tradingVolume;
    }

    public String getTitle() {
        return title;
    }

    public double getBuy() {
        return buy;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getPriceChange() {
        return priceChange;
    }

}

class PreciousMetalsContainer {
    private final ArrayList<PreciousMetal> preciousMetals;
    Unit unit = Unit.Defult;

    public PreciousMetalsContainer(Unit unit) {
        this.preciousMetals = new ArrayList<PreciousMetal>();
        this.unit = unit;
    }

    public void addPreciousMetal(PreciousMetal preciousMetal) {
        preciousMetals.add(preciousMetal);
    }

    public ArrayList<PreciousMetal> getPreciousMetals() {
        return preciousMetals;
    }
}