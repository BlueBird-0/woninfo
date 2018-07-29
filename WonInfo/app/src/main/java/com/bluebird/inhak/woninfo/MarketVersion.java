package com.bluebird.inhak.woninfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by InHak on 2018-02-24.
 */

public class MarketVersion
{
    public static String getMarketVersion(String packageName)
    {
        try {
            Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id="
                    +packageName).get();
            Elements Version = doc.select(".content");

            for(Element element : Version){
                if(element.attr("itemprop").equals("softwareVersion")){
                    return element.text().trim();
                }
            }

        }catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return "1.0.0";
    }

    public static String getMarketVersionFast(String packageName)
    {
        String data = "", version = null;

        try{
            URL url = new URL("https://play.goolge.com/store/apps/details?id="
                            +packageName);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            if(connection == null)
                return null;

            connection.setConnectTimeout(5000);
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                while(true){
                    String line = reader.readLine();
                    if(line == null)
                        break;
                    data += line;
                }

                reader.close();
            }
            connection.disconnect();

        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }

        String startToken = "softwareVersion\">";
        String endToken = "<";
        int index = data.indexOf(startToken);

        if(index == -1){
            version = null;
        }else{
            version = data.substring(index + startToken.length(), index + startToken.length() + 100);
            version = version.substring(0,version.indexOf(endToken)).trim();
        }

        return version;
    }
}
