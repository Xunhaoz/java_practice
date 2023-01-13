package com.walnutek.javapractice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        String getUrl = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
        Document doc = Jsoup.parse(new URL(getUrl).openStream(), "GBK", getUrl);

        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            for (int j = 0; j < cols.size(); j++) {
                System.out.println(cols.get(j));
            }
        }


    }
}
