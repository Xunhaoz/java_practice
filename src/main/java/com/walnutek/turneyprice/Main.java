package com.walnutek.turneyprice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //    黃金每盎司(美元) 黃金每盎司(台幣) 黃金每台錢(台幣) 黃金每公克(台幣)
    static final String[] getUrls = {"https://s.tradingview.com/embed-widget/single-quote/?locale=zh_TW#%7B%22symbol%22%3A%22OANDA%3AXAUUSD%22%2C%22width%22%3A%22100%25%22%2C%22colorTheme%22%3A%22light%22%2C%22isTransparent%22%3Atrue%2C%22height%22%3A126%2C%22utm_source%22%3A%22www.truney.com%22%2C%22utm_medium%22%3A%22widget%22%2C%22utm_campaign%22%3A%22single-quote%22%2C%22page-uri%22%3A%22www.truney.com%2Fgold-chart%22%7D", "https://s.tradingview.com/embed-widget/single-quote/?locale=zh_TW#%7B%22symbol%22%3A%22OANDA%3AXAUUSD*USDTWD%22%2C%22width%22%3A%22100%25%22%2C%22colorTheme%22%3A%22light%22%2C%22isTransparent%22%3Atrue%2C%22height%22%3A126%2C%22utm_source%22%3A%22www.truney.com%22%2C%22utm_medium%22%3A%22widget%22%2C%22utm_campaign%22%3A%22single-quote%22%2C%22page-uri%22%3A%22www.truney.com%2Fen%2Fgold-chart%22%7D", "https://s.tradingview.com/embed-widget/single-quote/?locale=zh_TW#%7B%22symbol%22%3A%22OANDA%3AXAUUSD*USDTWD%2F8.2944%22%2C%22width%22%3A%22100%25%22%2C%22colorTheme%22%3A%22light%22%2C%22isTransparent%22%3Atrue%2C%22height%22%3A126%2C%22utm_source%22%3A%22www.truney.com%22%2C%22utm_medium%22%3A%22widget%22%2C%22utm_campaign%22%3A%22single-quote%22%2C%22page-uri%22%3A%22www.truney.com%2Fen%2Fgold-chart%22%7D", "https://s.tradingview.com/embed-widget/single-quote/?locale=zh_TW#%7B%22symbol%22%3A%22OANDA%3AXAUUSD*USDTWD%2F31.1035%22%2C%22width%22%3A%22100%25%22%2C%22colorTheme%22%3A%22light%22%2C%22isTransparent%22%3Atrue%2C%22height%22%3A126%2C%22utm_source%22%3A%22www.truney.com%22%2C%22utm_medium%22%3A%22widget%22%2C%22utm_campaign%22%3A%22single-quote%22%2C%22page-uri%22%3A%22www.truney.com%2Fgold-chart%22%7D"};

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

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<TruneyContainer> truneyContainerList = new ArrayList<TruneyContainer>();

        for (String getUrl : getUrls) {
            TruneyContainer truneyContainer = new TruneyContainer();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
            WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/"),options);
            driver.get(getUrl);
            Thread.sleep(2000);
            String pageSource = driver.getPageSource();

            Document doc = Jsoup.parse(pageSource);
            Elements spans = doc.getElementsByClass("tv-ticker-item-last__body").select("span");
            truneyContainer.setTitle(doc.getElementsByClass("js-symbol-short-name").html());

            for(Element span : spans){
                if (parseDouble(span.getElementsByClass("tv-ticker-item-last__last").html()) != 0) {
                    truneyContainer.setCurrentPrice(parseDouble(span.getElementsByClass("tv-ticker-item-last__last").html()));
                }
                if (parseDouble(span.getElementsByClass("tv-ticker-item-last__change-percent").html()) != 0) {
                    truneyContainer.setQuoteChange(parseDouble(span.getElementsByClass("tv-ticker-item-last__change-percent").html()));
                }
                if (parseDouble(span.getElementsByClass("tv-ticker-item-last__change").html()) != 0) {
                    truneyContainer.setQuotePrice(parseDouble(span.getElementsByClass("tv-ticker-item-last__change").html()));
                }
            }

            truneyContainerList.add(truneyContainer);
            driver.quit();
        }

        for (TruneyContainer truneyContainer : truneyContainerList) {
            System.out.println(truneyContainer.getTitle());
            System.out.println(truneyContainer.getCurrentPrice());
            System.out.println(truneyContainer.getQuoteChange());
            System.out.println(truneyContainer.getQuotePrice());
        }
    }
}
