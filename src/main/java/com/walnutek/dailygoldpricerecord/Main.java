package com.walnutek.dailygoldpricerecord;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final String getURL = "https://allbeauty.com.tw/GoldPrice/output_ListGoldPrice.html";

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
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), options);
        driver.get(getURL);
        String pageSource = driver.getPageSource();
        driver.quit();

        Element doc = Jsoup.parse(pageSource).getElementById("goldprice");
        Element cal = null;

        assert doc != null;
        Elements cals = doc.select("tr").get(1).select("td");
        DailyGoldPrice dailyGoldPrice = new DailyGoldPrice();
        dailyGoldPrice.setGoldAskingPrice(parseDouble(cals.get(2).html()));
        dailyGoldPrice.setGoldAskingPriceChange(parseDouble(cals.get(3).html()));
        dailyGoldPrice.setGoldSell(parseDouble(cals.get(4).html()));
        dailyGoldPrice.setGoldBuy(parseDouble(cals.get(5).html()));
        dailyGoldPrice.setPricePreGram(parseDouble(cals.get(6).html()));
        dailyGoldPrice.setPlatinumPrice(parseDouble(cals.get(7).html()));
        cal = cals.get(8);
        cal.select("img").remove();
        dailyGoldPrice.setPlatinumPriceChange(parseDouble(cal.html()));
        dailyGoldPrice.setPalladiumPrice(parseDouble(cals.get(9).html()));
        cal = cals.get(10);
        cal.select("img").remove();
        dailyGoldPrice.setPalladiumPriceChange(parseDouble(cal.html()));
        dailyGoldPrice.setSilverPrice(parseDouble(cals.get(11).html()));
        cal = cals.get(12);
        cal.select("img").remove();
        dailyGoldPrice.setSilverPriceChange(parseDouble(cal.html()));

        System.out.println(dailyGoldPrice.getGoldAskingPrice());
        System.out.println(dailyGoldPrice.getGoldAskingPriceChange());
        System.out.println(dailyGoldPrice.getGoldSell());
        System.out.println(dailyGoldPrice.getGoldBuy());
        System.out.println(dailyGoldPrice.getPricePreGram());
        System.out.println(dailyGoldPrice.getPlatinumPrice());
        System.out.println(dailyGoldPrice.getPlatinumPriceChange());
        System.out.println(dailyGoldPrice.getPalladiumPrice());
        System.out.println(dailyGoldPrice.getPalladiumPriceChange());
        System.out.println(dailyGoldPrice.getSilverPrice());
        System.out.println(dailyGoldPrice.getSilverPriceChange());
    }
}
