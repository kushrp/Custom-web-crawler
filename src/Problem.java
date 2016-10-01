//Problem        : Cycle Check
//Language       : Java
//Compiled Using : javac
//Version        : Java 1.7.0_75
//Input for your program will be provided from STDIN
//Print out all output from your program to STDOUT

import java.util.*;

//Your submission should *ONLY* use the following class name
public class Problem {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();
        int m = stdin.nextInt();
        int ans = 0;
        //ArrayList<Integer> a = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        Hashtable<Integer, Integer> b = new Hashtable();
        int max = -1;
        int imax = -1;
        for (int i = 0; i < n; i++) {
            int c = stdin.nextInt();
            b.put(i, c);
            q.add(i);
            if (c > max) {
                max = c;
                imax = i;
            }
        }
        //System.out.println("Here");
        while (q.isEmpty() == false) {
            int currp = b.get(q.peek());
            //System.out.println("currp: " + currp);
            //System.out.println("max: " + max);
            if (currp >= max) {
                //System.out.println("Here1");
                ans += 2;
                int curr = q.remove();
                b.remove(curr);
                if (curr == m) {
                    System.out.println(ans);
                    return;
                }
                Set<Integer> keys = b.keySet();
                max = -1;
                int l = 0;
                for (Integer key : keys) {
                    if (b.get(key) > max) {
                        max = b.get(key);
                        imax = l;
                    }
                    l++;
                }
                //break;
            }
            else {
                /*
                System.out.println("Here2");
                for(Integer s : q) {
                    System.out.println(s.toString());
                }
                */
                int curr = q.remove();
                q.add(curr);
                /*
                for(Integer s : q) {
                    System.out.println(s.toString());
                }
                */
                //break;
            }
        }
    }
}

        /*
        //Linkedlist ll = new Linkedlist();
        //LinkedList<Integer> lle = new LinkedList<Integer>();
        Hashtable<Integer, Integer> table = new Hashtable<>();
        Hashtable<Integer, Integer> values = new Hashtable<>();

        Scanner stdin = new Scanner(System.in);
        //Linkedlist ll = new Linkedlist();
        //LinkedList<Integer> lle = new LinkedList<Integer>();
        Hashtable<Integer, Integer> table = new Hashtable<>();
        Hashtable<Integer, Integer> values = new Hashtable<>();
        while(stdin.hasNextLine())
        {
            int a = stdin.nextInt();
            int b = stdin.nextInt();
            int c = stdin.nextInt();
            if (a == 0 & b == 0 && c == 0) break;
            table.put(a,c);
            values.put(a,b);
        }
        int head = stdin.nextInt();
        stdin.close();
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        while (true) {
            int next = table.get(head);
            if (a.isEmpty() == false && a.contains(next)) {
                int index = a.indexOf(next);
                a.add(head);
                for (int i = index; i < a.size(); i++) {
                    if (i == a.size() - 1)System.out.print(values.get(a.get(i)));
                    else System.out.print(values.get(a.get(i)) + ",");
                }
                break;
            }
            else {
                a.add(head);
                head = next;
            }
            if (a.size() >= table.size()) {
                System.out.println("NONE");
                break;
            }
        }
    }

}
class Linkedlist {
    Node head;
}
class Node {
    int address;
    int value;
    Node next;
}
*/