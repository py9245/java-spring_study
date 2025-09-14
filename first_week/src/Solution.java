import java.util.*;
import java.io.FileInputStream;

class Solution {
    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("res/input.txt"));
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            int n = sc.nextInt();
            String line = sc.next();

            int ans = 0;
            int current = 0;

            for (int i = 0; i < n; i++) {
                if (line.charAt(i) == '0') {
                    current = 0;
                } else {
                    current += 1;
                    ans = Math.max(ans, current);
                }
            }
            System.out.printf("#%d %d%n", test_case, ans);
        }
    }
}
