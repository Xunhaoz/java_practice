package com.walnutek.monetary;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final String[] targetCurrencyArray = {"USD", "GBP", "CNY", "EUR"};

    private static double parseDouble(String s) {
        Pattern pattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
        Matcher matcher = pattern.matcher(s);

        if (!matcher.find()) return 0;
        try {
            return Double.parseDouble(matcher.group());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        String getUrl = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/"), options);
        driver.get(getUrl);
        Document doc = Jsoup.parse(driver.getPageSource());

        ArrayList<Monetary> monetaries = new ArrayList<Monetary>();

        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        for (Element row : rows) {
            Elements cols = row.select("td");

            String currency = "";

            if (cols.size() > 5) {
                currency = cols.get(0).getElementsByClass("hidden-phone print_show").html();
            } else {
                continue;
            }


            for (String query : targetCurrencyArray) {
                if (currency.contains(query)) {
                    Monetary monetary = new Monetary(currency);
                    monetary.setCashRateIn(parseDouble(cols.get(1).html()));
                    monetary.setCashRateOut(parseDouble(cols.get(2).html()));
                    monetary.setSpotRateIn(parseDouble(cols.get(3).html()));
                    monetary.setSpotRateOut(parseDouble(cols.get(4).html()));
                    monetaries.add(monetary);
                }
            }
        }

        for (Monetary monetary : monetaries) {
            System.out.println(monetary.getCurrency());
            System.out.println(monetary.getCashRateIn());
            System.out.println(monetary.getCashRateOut());
            System.out.println(monetary.getSpotRateIn());
            System.out.println(monetary.getSpotRateOut());
        }
    }
}
