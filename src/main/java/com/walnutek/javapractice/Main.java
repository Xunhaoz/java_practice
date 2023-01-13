package com.walnutek.javapractice;

import com.walnutek.javapractice.Monetary;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Main {
    static final String[] targetCurrencyArray = {"USD", "GBP", "CNY", "EUR"};

    private static double parseDouble(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        String getUrl = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
        Connection.Response response = Jsoup.connect(getUrl)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .timeout(12000)
                .followRedirects(true)
                .execute();

        Document doc = response.parse();

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
