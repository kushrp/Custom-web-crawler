package com.kush;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws IOException {

        Main m = new Main();
        System.out.println("First BFS: ");
        m.processPageBFS("http://www.purdue.edu");
        System.out.println("NOW DFS: ");
        m.processPageDFS("http://www.purdue.edu");
        System.out.println("Starting with cs.purdue.edu: ");
        m.processPageBFS("http://www.cs.purdue.edu");
    }
    boolean debug = false;

    private void processPageBFS(String URL) throws IOException{
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
        Hashtable<String, Integer> depth = new Hashtable<>();
        urls.put(URL,1);
        depth.put(URL,0);
        int max = 0;
        //int flag = 0;
        int n = 0;
        while (queue.isEmpty() == false && n <= 1000) {
            try {
                String curr = queue.poll();
                int prevDepth = depth.get(curr);
                if (debug == true) System.out.println("Link in question: " + curr);
                if (curr.contains("mailto") || curr.contains(".jpg")) continue;
                //curr.replace("https://","http://");
                Document doc = Jsoup.connect(curr).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").ignoreContentType(true).timeout(0).get();
                Elements links = doc.select("a[href]");
                if (debug == true) print("\nLinks: (%d)", links.size());
                for (Element link : links) {
                    //System.out.println("HI");
                    if (urls.containsKey(link.attr("abs:href")) == false) {
                        //System.out.println("HI");

                        //if (flag == 0) {
                            queue.add(link.attr("abs:href"));
                        //}
                        //if (queue.size() >= 1000) flag = 1;

                        urls.put(link.attr("abs:href"), 1);
                        //Current link's depth
                        depth.put(link.attr("abs:href"), prevDepth + 1);
                        max = Math.max(max, prevDepth + 1);
                        //print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
                    } else {
                        //System.out.println("YO");
                        int num = urls.get(link.attr("abs:href"));
                        urls.put(link.attr("abs:href"), num + 1);
                    }
                }
                if (debug == true) System.out.println("Queue size " + queue.size());
                n++;
                if (debug == true) System.out.println("No. of URLs Visited: " + n);
            }
            catch(Exception e) {
                if (debug == true) System.out.println("Error !!!!!!! " + e.toString());
                continue;
            }
        }
        Set<String> keys = urls.keySet();
        int sum = 0;
        for (String key: keys) {
            if (debug == true) System.out.println("Value of " + key + " is: " + urls.get(key));
            if (urls.get(key) != null && urls.get(key) != 1) sum += urls.get(key) - 1;
        }
        System.out.println("No. of times we see a duplicate URL when we crawled 1000 pages: " + sum);
        System.out.println("Max depth. after we crawled 1000 pages BFS: " + max);
        System.out.println("Queue size after we crawled 1000 pages: " + queue.size());
    }

    // A function used by DFS

    Hashtable<String, Integer> urls;
    Hashtable<String, Integer> depth;
    int max = 0;
    int n = 0;

    void DFSUtil(String url) {
        try {
            if (n > 1000) return;
            else n++;
            String curr = url;
            int prevDepth = depth.get(curr);
            if (debug == true) System.out.println("Link in question: " + curr);
            if (curr.contains("mailto") || curr.contains(".jpg")) return;
            //curr.replace("https://","http://");
            Document doc = Jsoup.connect(curr).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").ignoreContentType(true).timeout(0).get();
            Elements links = doc.select("a[href]");
            if (debug == true) print("\nLinks: (%d)", links.size());
            for (Element link : links) {
                if (urls.containsKey(link.attr("abs:href")) == false) {
                    urls.put(link.attr("abs:href"), 1);
                    depth.put(link.attr("abs:href"), prevDepth + 1);
                    max = Math.max(max, prevDepth + 1);
                    DFSUtil(link.attr("abs:href"));
                    if (debug == true) print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
                } else {
                    int num = urls.get(link.attr("abs:href"));
                    urls.put(link.attr("abs:href"), num + 1);
                }
            }
        }
        catch(Exception e) {
            if (debug == true) System.out.println("Error !!!!!!! " + e.toString());
            return;
        }
    }

    private void processPageDFS(String URL) throws IOException{

        //Stack<String> stack = new Stack<String>();
        //stack.push(URL);
        //Hashtable<String, Integer> urls = new Hashtable<>();
        //Hashtable<String, Integer> depth = new Hashtable<>();

        urls = new Hashtable<String, Integer>();
        depth = new Hashtable<String, Integer>();
        urls.put(URL,1);
        depth.put(URL,0);
        max = 0;
        //int flag = 0;
        n = 0;
        DFSUtil(URL);
        Set<String> keys = urls.keySet();
        int sum = 0;
        for (String key: keys) {
            if (debug == true) System.out.println("Value of " + key + " is: " + urls.get(key));
            if (urls.get(key) != null && urls.get(key) != 1) sum += urls.get(key) - 1;
        }
        System.out.println("No. of times we see a duplicate URL when we crawled 1000 pages DFS: " + sum);
        System.out.println("Max depth. after we crawled 1000 pages DFS: " + max);
        //System.out.println("Queue size after we crawled 1000 pages: " + queue.size());
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
