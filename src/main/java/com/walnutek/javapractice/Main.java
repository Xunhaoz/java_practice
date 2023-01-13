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
    static final String[] targetCurrencyList = {"USD", "GBP", "CNY", "EUR"};

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

            if (cols.size() > 0) {
                currency = cols.get(0).getElementsByClass("hidden-phone print_show").html();
            }

            for (String query : targetCurrencyList) {
                if (currency.contains(query)) {
                    Monetary tmp = new Monetary(currency);
                    tmp.setCashRateIn(cols.get(1).html());
                    tmp.setCashRateOut(cols.get(2).html());
                    tmp.setSpotRateIn(cols.get(3).html());
                    tmp.setSpotRateOut(cols.get(4).html());
                    monetaries.add(tmp);
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
