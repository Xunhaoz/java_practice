package com.walnutek.javapractice;

import com.walnutek.javapractice.Monetary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String getUrl = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
        Document doc = Jsoup.parse(new URL(getUrl).openStream(), "UTF-8", getUrl);

        String[] querys = {"美金 (USD)", "英鎊 (GBP)", "人民幣 (CNY)", "歐元 (EUR)"};
        ArrayList<Monetary> monetarys = new ArrayList<Monetary>();

        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        for (Element row : rows) {
            Elements cols = row.select("td");

            String currency = "";
            boolean selected = false;

            if (cols.size() > 0) {
                currency = cols.get(0).getElementsByClass("hidden-phone print_show").html();
            }

            for (String query : querys) {
                if (query.equals(currency)) {
                    selected = true;
                    monetarys.add(new Monetary(currency));
                }
            }

            if (selected) {
                monetarys.get(monetarys.size() - 1).setCash_rate_in(Float.parseFloat(cols.get(1).html()));
                monetarys.get(monetarys.size() - 1).setCash_rate_out(Float.parseFloat(cols.get(2).html()));
                monetarys.get(monetarys.size() - 1).setSpot_rate_in(Float.parseFloat(cols.get(3).html()));
                monetarys.get(monetarys.size() - 1).setSpot_rate_out(Float.parseFloat(cols.get(4).html()));
            }
        }
        for (Monetary monetary : monetarys) {
            System.out.println(monetary.getCurrency());
            System.out.println(monetary.getCash_rate_in());
            System.out.println(monetary.getCash_rate_out());
            System.out.println(monetary.getSpot_rate_in());
            System.out.println(monetary.getSpot_rate_out());
        }

    }
}
