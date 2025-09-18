package day_06_1;

import java.io.*;
import java.util.*;

public class day_06_03 {
    static int N, answer;
    static int[][] arr;
    static boolean[] visited;

    public static void dfs(int now, int count, int total) {
        if (count == N - 1) {
            total += arr[now][0];
            answer = Math.min(answer, total);
            return;
        }

        for (int next = 1; next < N; next++) {
            if (!visited[next]) {
                visited[next] = true;
                dfs(next, count + 1, total + arr[now][next]);
                visited[next] = false;
            }
        }
    }


    public static void main(String[] args) throws IOException {
    	System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            arr = new int[N][N];
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            answer = Integer.MAX_VALUE;
            visited = new boolean[N];
            visited[0] = true; // 사무실에서 시작
            dfs(0, 0, 0);

            System.out.println("#" + tc + " " + answer);
        }
    }
}
