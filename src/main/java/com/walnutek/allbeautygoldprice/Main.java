package com.walnutek.allbeautygoldprice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final String getURL = "https://allbeauty.com.tw/GoldPrice/";

    private static double parseDouble(String s) {
        Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
        s = s.replace(",", "");
        Matcher matcher = pattern.matcher(s);

        if (!matcher.find()) return 0;
        try {
            return Double.parseDouble(matcher.group());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get(getURL);
        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);
        Elements listMetalPriceTable = Objects.requireNonNull(doc.getElementById("ListMetalPrice")).select("tr");

        PreciousMetalsContainer ProductVersesTWD = new PreciousMetalsContainer(Unit.TWDtoQian);
        for (int rowIndex = 1; rowIndex < 5; rowIndex++) {
            Elements cols = listMetalPriceTable.get(rowIndex).select("td");
            PreciousMetal preciousMetal = new PreciousMetal(cols.get(0).html(), Unit.TWDtoQian);
            preciousMetal.setBuy(parseDouble(cols.get(1).html()), Unit.TWDtoQian);
            preciousMetal.setSell(parseDouble(cols.get(2).html()), Unit.TWDtoQian);
            preciousMetal.setPriceChange(parseDouble(cols.get(4).html()), Unit.TWDtoQian);
            preciousMetal.setHigh(parseDouble(cols.get(5).html()), Unit.TWDtoQian);
            preciousMetal.setLow(parseDouble(cols.get(6).html()), Unit.TWDtoQian);
            ProductVersesTWD.addPreciousMetal(preciousMetal);
        }

        PreciousMetalsContainer ProductVersesUSD = new PreciousMetalsContainer(Unit.USDtoOunce);
        for (int rowIndex = 6; rowIndex < 10; rowIndex++) {
            Elements cols = listMetalPriceTable.get(rowIndex).select("td");
            PreciousMetal preciousMetal = new PreciousMetal(cols.get(0).html(), Unit.USDtoOunce);
            preciousMetal.setBuy(parseDouble(cols.get(1).html()), Unit.USDtoOunce);
            preciousMetal.setSell(parseDouble(cols.get(2).html()), Unit.USDtoOunce);
            preciousMetal.setPriceChange(parseDouble(cols.get(4).html()), Unit.USDtoOunce);
            preciousMetal.setHigh(parseDouble(cols.get(5).html()), Unit.USDtoOunce);
            preciousMetal.setLow(parseDouble(cols.get(6).html()), Unit.USDtoOunce);
            preciousMetal.setTradingVolume(parseDouble(cols.get(7).html()), Unit.USDtoOunce);
            ProductVersesUSD.addPreciousMetal(preciousMetal);
        }

        for (PreciousMetal item : ProductVersesTWD.getPreciousMetals()) {
            System.out.println(item.getTitle());
            System.out.println(item.getBuy());
            System.out.println(item.getSell());
            System.out.println(item.getPriceChange());
            System.out.println(item.getHigh());
            System.out.println(item.getLow());
            System.out.println(item.getTradingVolume());
        }

        for (PreciousMetal item : ProductVersesUSD.getPreciousMetals()) {
            System.out.println(item.getTitle());
            System.out.println(item.getBuy());
            System.out.println(item.getSell());
            System.out.println(item.getPriceChange());
            System.out.println(item.getHigh());
            System.out.println(item.getLow());
            System.out.println(item.getTradingVolume());
        }

        driver.quit();
    }
}
