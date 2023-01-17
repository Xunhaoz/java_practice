package com.walnutek.goldpriceandrecovery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
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

    public static void main(String[] args) throws InterruptedException {
        ArrayList<GoldPrice> goldPriceList = new ArrayList<GoldPrice>();
        ArrayList<DecorationPrice> decorationPriceList = new ArrayList<DecorationPrice>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);
        driver.get(getURL);
        String pageSource = driver.getPageSource();

        Document doc = Jsoup.parse(pageSource);
        Elements tables = doc.select("table");

        Element truneyPrices = null;
        Element decorationPrices = null;

        for (Element table : tables) {
            if (table.getElementById("goldprice") != null && Objects.requireNonNull(table.getElementById("goldprice")).toString().contains("白金條塊")) {
                truneyPrices = table.getElementById("goldprice");
            } else if (table.getElementById("goldprice") != null && Objects.requireNonNull(table.getElementById("goldprice")).toString().contains("黃金飾金")) {
                decorationPrices = table.getElementById("goldprice");
            }
        }

        int titleIndex = 1, taiwanIndex = 2, gramIndex = 1;
        assert truneyPrices != null;
        Elements rows = truneyPrices.select("tr");


        for (int i = 0; i < 4; i++) {
            GoldPrice goldPrice = new GoldPrice();
            goldPrice.setTitle(rows.get(0).select("th").get(titleIndex).html());
            goldPrice.setPriceTaiwan(parseDouble(rows.get(2).select("td").get(taiwanIndex).toString()));
            goldPrice.setPriceGram(parseDouble(rows.get(3).select("td").get(gramIndex).toString()));
            taiwanIndex += 1;
            gramIndex += 1;
            goldPrice.setRecoveryTaiwan(parseDouble(rows.get(2).select("td").get(taiwanIndex).toString()));
            goldPrice.setRecoveryGram(parseDouble(rows.get(3).select("td").get(gramIndex).toString()));
            goldPriceList.add(goldPrice);
            titleIndex += 1;
            taiwanIndex += 1;
            gramIndex += 1;
        }

        Element decorationPricesGoldTable = decorationPrices.select("tr").get(2);
        Element decorationPricesPt950Table = decorationPrices.select("tr").get(3);
        DecorationPrice decorationPriceGold = new DecorationPrice(
                decorationPricesGoldTable.select("td").get(0).html(),
                parseDouble(decorationPricesGoldTable.select("td").get(1).html()),
                parseDouble(decorationPricesGoldTable.select("td").get(2).html()));
        DecorationPrice decorationPricePt950 = new DecorationPrice(
                decorationPricesPt950Table.select("td").get(0).html(),
                parseDouble(decorationPricesPt950Table.select("td").get(1).html()),
                parseDouble(decorationPricesPt950Table.select("td").get(2).html()));
        decorationPriceList.add(decorationPriceGold);
        decorationPriceList.add(decorationPricePt950);


        for (GoldPrice goldPrice : goldPriceList) {
            System.out.println(goldPrice.getTitle());
            System.out.println(goldPrice.getPriceTaiwan());
            System.out.println(goldPrice.getPriceGram());
            System.out.println(goldPrice.getRecoveryTaiwan());
            System.out.println(goldPrice.getRecoveryGram());
        }

        for(DecorationPrice decorationPrice : decorationPriceList){
            System.out.println(decorationPrice.getTitle());
            System.out.println(decorationPrice.getPrice());
            System.out.println(decorationPrice.getRecovery());
        }

        driver.quit();
    }
}
