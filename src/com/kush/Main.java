package com.kush;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        /*
        System.out.println("First BFS: ");
        m.processPageBFS("http://www.purdue.edu");
        System.out.println("NOW DFS without recursion: ");
        m.processPageDFSnoRecurse("http://www.purdue.edu");
        System.out.println("NOW DFS with recursion: ");
        m.processPageDFS("http://www.purdue.edu");
        System.out.println("250 links BFS Starting with cs.purdue.edu: ");*/
        m.parsePageBFS("http://www.cs.purdue.edu");

    }

    boolean debug = true;
    Hashtable<String, LinkedList<Integer>> words = new Hashtable<String, LinkedList<Integer>>();
    //int fal = 0;

    private void parsePageBFS(String URL) throws IOException{

        Queue<String> queue = new LinkedList<>();
        queue.add(URL);
        Hashtable<String, Integer> urls = new Hashtable<>(); //explored urls
        Hashtable<String, Integer> depth = new Hashtable<>();
        Hashtable<String, Integer> encounters = new Hashtable<>(); //crawled
        depth.put(URL,0);
        encounters.put(URL,1);
        int max = 0;
        int n = 0;
        int count = 0;
        while (queue.isEmpty() == false && n < 250) {
            try {
                String curr = queue.poll();
                int prevDepth = depth.get(curr);
                if (debug == true) System.out.println("Link in question: " + curr);
                Document doc = Jsoup.connect(curr).timeout(0).get();
                urls.put(curr, 1);
                Elements links = doc.select("a[href]");
                String wordslen[] = doc.body().text().toLowerCase().split("\\P{Alpha}+");
                for (int j = 0; j < wordslen.length; j++) {
                    //System.out.println(words[j]);
                    if (words.containsKey(wordslen[j])) {
                        LinkedList<Integer> cur = words.get(wordslen[j]);
                        cur.add(n);
                        words.put(wordslen[j],cur);
                    }
                    else {
                        LinkedList<Integer> cur = new LinkedList<>();
                        cur.add(n);
                        words.put(wordslen[j],cur);
                    }
                }
                //f (fal == 0)break;
                if (debug == true) print("\nLinks: (%d)", links.size());
                for (Element link : links) {
                    count++;
                    if (encounters.containsKey(link.attr("abs:href")) == false
                            && queue.contains(link.attr("abs:href")) == false
                            && urls.containsKey(link.attr("abs:href")) == false) {

                        //System.out.println("HI");
                        //if (flag == 0) {
                        queue.add(link.attr("abs:href"));
                        encounters.put(link.attr("abs:href"),1);
                        //}
                        //if (queue.size() >= 1000) flag = 1;
                        //Current link's depth
                        depth.put(link.attr("abs:href"), prevDepth + 1);
                        max = Math.max(max, prevDepth + 1);
                        if (debug == true) print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
                    }
                    else {
                        //System.out.println("YO");
                        int num = encounters.get(link.attr("abs:href"));
                        encounters.put(link.attr("abs:href"), num + 1);
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

        Set<String> keys = encounters.keySet();
        int sum = 0;
        int maxk = 0;
        int max2 = 0;
        int lol = 0;
        String maxKey = "";
        String maxKey2 = "";
        ArrayList<Integer> top = new ArrayList<Integer>();
        for (String key: keys) {
            top.add(encounters.get(key));
            if (encounters.get(key) > maxk) {
                max2 = maxk;
                maxk = encounters.get(key);
                maxKey2 = maxKey;
                maxKey = key;
            }
            if (debug == true) System.out.println("Value of " + key + " is: " + encounters.get(key));
            if (encounters.get(key) != null && encounters.get(key) != 1) sum += encounters.get(key) - 1;
            else if (encounters.get(key) == 1) lol++;
        }
        if (debug == true) System.out.println("maxkey: " + maxKey + " Value: " + maxk + "maxKey2: " + maxKey2 + " max2: " + max2);
        System.out.println("No. of times we see a duplicate URL when we crawled 1000 pages: " + sum);
        System.out.println("Max depth. after we crawled 1000 pages BFS: " + max);
        System.out.println("Queue size after we crawled 1000 pages: " + queue.size());
        System.out.println("Total Count of URLs: " + count);
        if (debug == true) System.out.println("Unique: " + lol);
        writeWords("Yolo.txt");
    }
    void writeWords(String filename) {
        File f = new File(filename);
        PrintWriter pw;
        try {
            pw = new PrintWriter(f);
        } catch (Exception E) {
            System.out.println("Couldn't create file output stream");
            return;
        }

        Set<String> keys = words.keySet();
        Iterator<String> itr = keys.iterator();
        //print : word frequency list_of_indices
        while (itr.hasNext()) {
            String word = itr.next();
            LinkedList<Integer> list = words.get(word);
            Object[] indices = list.toArray();
            pw.print(word + " " + indices.length + " ");
            for (int i = 0; i < indices.length; i++) pw.print(indices[i] + " ");
            pw.println();
        }
        pw.close();
    }

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
        Hashtable<String, Integer> urls = new Hashtable<>(); //explored urls
        Hashtable<String, Integer> depth = new Hashtable<>();
        Hashtable<String, Integer> encounters = new Hashtable<>(); //crawled
        Hashtable<String, Long> time = new Hashtable<>();
        //urls.put(URL,1);
        depth.put(URL,0);
        encounters.put(URL,1);
        int max = 0;
        //int flag = 0;
        int n = 0;
        int count = 0;
        while (queue.isEmpty() == false && n < 1000) {
            try {
                String curr = queue.remove(); //not poll?
                int prevDepth = depth.get(curr);
                if (debug == true) System.out.println("Link in question: " + curr);
                //if (curr.contains("mailto") || curr.contains(".jpg")) continue;
                //curr.replace("https://","http://");
                //Document doc = Jsoup.connect(curr).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").ignoreContentType(true).timeout(0).get();
                //if (urls.containsKey(curr) == true) continue;
                if (time.containsKey(curr)) {
                    long lv = time.get(curr);
                    long diff = System.currentTimeMillis() - lv;
                    while (diff < 5000) diff = System.currentTimeMillis() - lv;
                }
                Document doc = Jsoup.connect(curr).timeout(0).get();
                urls.put(curr, 1);
                Elements links = doc.select("a[href]");
                if (debug == true) print("\nLinks: (%d)", links.size());
                for (Element link : links) {
                    //System.out.println("HI");
                    count++;
                    if (encounters.containsKey(link.attr("abs:href")) == false
                            && queue.contains(link.attr("abs:href")) == false
                            && urls.containsKey(link.attr("abs:href")) == false) {
                        //System.out.println("HI");
                        //if (flag == 0) {
                        queue.add(link.attr("abs:href"));
                        encounters.put(link.attr("abs:href"),1);
                        //}
                        //if (queue.size() >= 1000) flag = 1;
                        //Current link's depth
                        depth.put(link.attr("abs:href"), prevDepth + 1);
                        max = Math.max(max, prevDepth + 1);
                        if (debug == true) print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
                    }
                    else {
                        //System.out.println("YO");
                        int num = encounters.get(link.attr("abs:href"));
                        encounters.put(link.attr("abs:href"), num + 1);
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

        Set<String> keys = encounters.keySet();
        int sum = 0;
        int maxk = 0;
        int max2 = 0;
        int lol = 0;
        String maxKey = "";
        String maxKey2 = "";
        ArrayList<Integer> top = new ArrayList<Integer>();
        for (String key: keys) {
            top.add(encounters.get(key));
            if (encounters.get(key) > maxk) {
                max2 = maxk;
                maxk = encounters.get(key);
                maxKey2 = maxKey;
                maxKey = key;
            }
            if (debug == true) System.out.println("Value of " + key + " is: " + encounters.get(key));
            if (encounters.get(key) != null && encounters.get(key) != 1) sum += encounters.get(key) - 1;
            else if (encounters.get(key) == 1) lol++;
        }
        if (debug == true) System.out.println("maxkey: " + maxKey + " Value: " + maxk + "maxKey2: " + maxKey2 + " max2: " + max2);
        System.out.println("No. of times we see a duplicate URL when we crawled 1000 pages: " + sum);
        System.out.println("Max depth. after we crawled 1000 pages BFS: " + max);
        System.out.println("Queue size after we crawled 1000 pages: " + queue.size());
        System.out.println("Total Count of URLs: " + count);
        if (debug == true) System.out.println("Unique: " + lol);
    }

    private void processPageDFSnoRecurse(String URL) throws IOException{
        Stack<String> stack = new Stack<>();
        stack.add(URL);
        Hashtable<String, Integer> urls = new Hashtable<>(); //explored urls
        Hashtable<String, Integer> depth = new Hashtable<>();
        Hashtable<String, Integer> encounters = new Hashtable<>(); //crawled
        //urls.put(URL,1);
        depth.put(URL,0);
        encounters.put(URL,1);
        int max = 0;
        int n = 0;
        int count = 0;
        while (stack.isEmpty() == false && n < 1000) {
            try {
                String curr = stack.pop();
                int prevDepth = depth.get(curr);
                if (debug == true) System.out.println("Link in question: " + curr);
                Document doc = Jsoup.connect(curr).timeout(0).get();
                urls.put(curr, 1);
                Elements links = doc.select("a[href]");
                if (debug == true) print("\nLinks: (%d)", links.size());
                for (Element link : links) {
                    //System.out.println("HI");
                    count++;
                    if (encounters.containsKey(link.attr("abs:href")) == false
                            && stack.contains(link.attr("abs:href")) == false
                            && urls.containsKey(link.attr("abs:href")) == false) {
                        stack.add(link.attr("abs:href"));
                        encounters.put(link.attr("abs:href"),1);
                        depth.put(link.attr("abs:href"), prevDepth + 1);
                        max = Math.max(max, prevDepth + 1);
                        if (debug == true) print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
                    }
                    else {
                        //System.out.println("YO");
                        int num = encounters.get(link.attr("abs:href"));
                        encounters.put(link.attr("abs:href"), num + 1);
                    }
                }
                if (debug == true) System.out.println("Queue size " + stack.size());
                n++;
                if (debug == true) System.out.println("No. of URLs Visited: " + n);
            }
            catch(Exception e) {
                if (debug == true) System.out.println("Error !!!!!!! " + e.toString());
                continue;
            }
        }

        Set<String> keys = encounters.keySet();
        int sum = 0;
        int lol = 0;
        for (String key: keys) {
            if (debug == true) System.out.println("Value of " + key + " is: " + encounters.get(key));
            if (encounters.get(key) != null && encounters.get(key) != 1) sum += encounters.get(key) - 1;
            else if (encounters.get(key) == 1) lol++;
        }
        System.out.println("No. of times we see a duplicate URL when we crawled 1000 pages: " + sum);
        System.out.println("Max depth. after we crawled 1000 pages DFS: " + max);
        System.out.println("Stack size after we crawled 1000 pages: " + stack.size());
        System.out.println("Total Count of URLs: " + count);
        if (debug == true)System.out.println("Unique: " + lol);
    }

    // A function used by DFS

    Hashtable<String, Integer> urls;
    Hashtable<String, Integer> depth;
    Hashtable<String, Integer> encounters;
    Queue<String> queue = new LinkedList<>();
    int max = 0;
    int n = 0;
    int count = 0;
    int total = 0;

    void DFSUtil(String url) throws IOException{
        try {
            if (n > 1000) return;
            else n++;
            String curr = url;
            int prevDepth = depth.get(curr);
            Document doc = Jsoup.connect(curr).timeout(0).get();
            urls.put(curr, 1);
            Elements links = doc.select("a[href]");
            if (debug == true) print("\nLinks: (%d)", links.size());
            for (Element link: links) {
                count++;
                if (encounters.containsKey(link.attr("abs:href")) == false
                        && queue.contains(link.attr("abs:href")) == false
                        && urls.containsKey(link.attr("abs:href")) == false) {
                    encounters.put(link.attr("abs:href"),1);
                    depth.put(link.attr("abs:href"), prevDepth + 1);
                    max = Math.max(max, prevDepth + 1);
                    queue.add(link.attr("abs:href"));
                    if (debug == true) print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
                }
                else {
                    int num = encounters.get(link.attr("abs:href"));
                    encounters.put(link.attr("abs:href"), num + 1);
                }
            }
            for (Element link: links) {
                    String curr1 = queue.remove();
                    DFSUtil(curr1);
                    if (debug == true) print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 100));
            }
            if (debug == true) System.out.println("No. of URLs Visited: " + n);
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
        encounters = new Hashtable<String, Integer>();
        depth.put(URL,0);
        encounters.put(URL,1);
        max = 0;
        //int flag = 0;
        n = 0;
        DFSUtil(URL);
        Set<String> keys = encounters.keySet();
        int sum = 0;
        for (String key: keys) {
            if (debug == true) System.out.println("Value of " + key + " is: " + encounters.get(key));
            if (encounters.get(key) != null && encounters.get(key) != 1) sum += encounters.get(key) - 1;
        }
        System.out.println("No. of times we see a duplicate URL when we crawled 1000 pages DFS: " + sum);
        System.out.println("Max depth. after we crawled 1000 pages DFS: " + max);
        System.out.println("Queue size left to be explored: " + queue.size());
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
