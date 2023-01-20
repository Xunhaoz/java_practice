package com.walnutek.wfgold;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    final static String getURL = "https://www.wfgold.com/";

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

    public static void main(String[] args) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.get(getURL);
        String pageSource = driver.getPageSource();
        driver.quit();

        Element doc = Jsoup.parse(pageSource);
        Element tables = doc.getElementsByClass("scroll-scroller").get(0);
        Elements preciousMetalsRows = tables.getElementsByClass("quote-table scroll-table").get(0).select("tr");

        ArrayList<PreciousMetal> preciousMetalArrayList = new ArrayList<PreciousMetal>();

        for (int index = 1; index < preciousMetalsRows.size(); index++) {
            Element row = preciousMetalsRows.get(index);
            PreciousMetal preciousMetal = new PreciousMetal();
            Elements span = row.select("span");
            preciousMetal.setTitle(span.get(0).html());
            preciousMetal.setBid(parseDouble(span.get(5).html()));
            preciousMetal.setAsk(parseDouble(span.get(6).html()));
            String[] divided = span.get(3).html().split("/");
            preciousMetal.setHigh(parseDouble(divided[0].toString()) / parseDouble(divided[1].toString()));
            divided = span.get(4).html().split("/");
            preciousMetal.setLow(parseDouble(divided[0].toString()) / parseDouble(divided[1].toString()));
            preciousMetalArrayList.add(preciousMetal);
        }

        for (PreciousMetal preciousMetal : preciousMetalArrayList) {
            System.out.println(preciousMetal.getTitle());
            System.out.println(preciousMetal.getBid());
            System.out.println(preciousMetal.getAsk());
            System.out.println(preciousMetal.getHigh());
            System.out.println(preciousMetal.getLow());
        }

    }
}
