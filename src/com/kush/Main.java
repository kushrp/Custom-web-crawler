package com.kush;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jsoup.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws IOException {
        processPage("http://www.purdue.edu");
    }

    public static void processPage(String URL) throws IOException{
        //check if the given URL is already in the hashtable

        //store the URL to hashtable to avoid parsing again

        //get useful information
        Document doc = Jsoup.connect("http://www.purdue.edu/").get();

        String title = doc.title();
        /*
        Element content = doc.getElementById("content");
        //get all links and recursively call the processPage method
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
        }
        */
        /*
        Elements questions = doc.select("a[href]");

        for(Element link: questions) {
            if (link.attr("href").contains("purdue.edu"))
                processPage(link.attr("abs:href"));
        }
        */
        Elements links = doc.select("a[href]");
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
