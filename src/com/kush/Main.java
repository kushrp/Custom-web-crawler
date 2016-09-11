package com.kush;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws IOException {
        processPage("http://www.purdue.edu");
    }

    private static void processPage(String URL) throws IOException{
        //check if the given URL is already in the HashTable

        //store the URL to HashTable to avoid parsing again

        //get useful information

        //List links approach

        /*
        String title = doc.title();
        Element content = doc.getElementById("content");
        //get all links and recursively call the processPage method
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
        }
        */

        //Recursive approach

        /*
        Elements questions = doc.select("a[href]");

        for(Element link: questions) {
            if (link.attr("href").contains("purdue.edu"))
                processPage(link.attr("abs:href"));
        }
        */
        Queue<String> queue = new LinkedList<>();
        queue.add(URL);
        Hashtable<String, Integer> urls = new Hashtable<>();
        urls.put(URL,1);
        while (queue.isEmpty() == false && queue.size() <= 1000) {
            Document doc = Jsoup.connect(queue.poll()).timeout(0).get();
            Elements links = doc.select("a[href]");
            print("\nLinks: (%d)", links.size());
            for (Element link : links) {
                //System.out.println("HI");
                if (urls.containsKey(link.attr("abs:href")) == false) {
                    //System.out.println("HI");
                    queue.add(link.attr("abs:href"));
                    urls.put(link.attr("abs:href"), 1);
                    print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
                }
                else {
                    //System.out.println("YOLO");
                    int num = urls.get(link.attr("abs:href"));
                    urls.put(link.attr("abs:href"), num+1);
                }
            }
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
