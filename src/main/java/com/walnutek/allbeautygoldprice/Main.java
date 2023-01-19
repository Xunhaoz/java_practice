package com.walnutek.allbeautygoldprice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final String[] getURLs = {"https://allbeauty.com.tw/GoldPrice/", "https://i.jzj9999.com/quoteh5/"};

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
        ArrayList<String> pageSourceList = new ArrayList<>();
        HashMap<String, String> htmlEntities = new HashMap<>();
        htmlEntities.put(String.valueOf("\ue1f2"), "0");
        htmlEntities.put(String.valueOf("\uefab"), "1");
        htmlEntities.put(String.valueOf("\ueba3"), "2");
        htmlEntities.put(String.valueOf("\uecfa"), "3");
        htmlEntities.put(String.valueOf("\uedfd"), "4");
        htmlEntities.put(String.valueOf("\ueffa"), "5");
        htmlEntities.put(String.valueOf("\uef3a"), "6");
        htmlEntities.put(String.valueOf("\ue6f5"), "7");
        htmlEntities.put(String.valueOf("\uecb2"), "8");
        htmlEntities.put(String.valueOf("\ue8ae"), "9");

        Thread threadPage01 = new Thread(() -> {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            WebDriver driver = null;
            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/"), options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            assert driver != null;
            driver.get(getURLs[0]);
            String beautyPageSource = driver.getPageSource();
            driver.quit();
            pageSourceList.add(0, beautyPageSource);
        });

        Thread threadPage02 = new Thread(() -> {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            WebDriver driver = null;
            try {
                driver = new RemoteWebDriver(new URL("http://localhost:4444/"), options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            assert driver != null;
            driver.get(getURLs[1]);
            String jzj9999PageSource = driver.getPageSource();
            driver.quit();
            for (Map.Entry<String, String> entry : htmlEntities.entrySet()) {
                jzj9999PageSource = jzj9999PageSource.replaceAll(entry.getKey(), entry.getValue());
            }
            pageSourceList.add(jzj9999PageSource);
        });
        threadPage01.start();
        threadPage02.start();

        try {
            threadPage01.join();
            threadPage02.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Document doc = Jsoup.parse(pageSourceList.get(0));
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

        PreciousMetalsContainer ProductVersesRMB = new PreciousMetalsContainer(Unit.RMBtoOunce);
        doc = Jsoup.parse(pageSourceList.get(1), "UTF-16");
        Elements priceTableRow = doc.getElementsByClass("price-table-row");
        for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
            Elements span = priceTableRow.get(rowIndex).select("span");
            PreciousMetal preciousMetal = new PreciousMetal(span.get(0).html(), Unit.RMBtoOunce);
            preciousMetal.setBuy(parseDouble(span.get(1).html()), Unit.RMBtoOunce);
            preciousMetal.setSell(parseDouble(span.get(2).html()), Unit.RMBtoOunce);
            preciousMetal.setHigh(parseDouble(span.get(3).html()), Unit.RMBtoOunce);
            preciousMetal.setLow(parseDouble(span.get(4).html()), Unit.RMBtoOunce);
            ProductVersesRMB.addPreciousMetal(preciousMetal);
        }

        for (PreciousMetal item : ProductVersesRMB.getPreciousMetals()) {
            System.out.println(item.getTitle());
            System.out.println(item.getBuy());
            System.out.println(item.getSell());
            System.out.println(item.getHigh());
            System.out.println(item.getLow());
        }
    }
}
