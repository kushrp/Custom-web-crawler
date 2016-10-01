
import java.util.Arrays;
import java.util.Collections;
import java.util.*;
//Your submission should *ONLY* use the following class name
public class Problem2 {
    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            a.add(stdin.nextInt());
        }
        int maxSum = 0;
        int sumOfCake = 0;
        for (int i = 0; i < a.size(); i++) {
            sumOfCake = 0;
            int curr = a.get(i);
            for (int j = i; j < a.size(); j++) {
                if (a.get(j) >= curr) {
                    sumOfCake += a.get(j);
                    curr = a.get(j);
                }
            }
            if (sumOfCake > maxSum) maxSum = sumOfCake;
        }
        System.out.println(maxSum);
    }
}