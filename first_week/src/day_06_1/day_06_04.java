package day_06_1;

import java.io.*;
import java.util.*;

public class day_06_04 {
    static int N, answer;
    static int[][] arr;
    static int[][] dxy = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
    static int startX, startY;
    static Set<Integer> root;

    static void dfs(int x, int y, int d, int cnt, int turn) {
        int nx = x + dxy[d][0];
        int ny = y + dxy[d][1];

        if (nx < 0 || ny < 0 || nx >= N || ny >= N) return;

        if (nx == startX && ny == startY && cnt >= 4 && turn == 3) {
            answer = Math.max(answer, cnt);
            return;
        }

        int dessert = arr[nx][ny];
        if (root.contains(dessert)) return;

        root.add(dessert);
        dfs(nx, ny, d, cnt + 1, turn);
        if (d < 3) {
            dfs(nx, ny, d + 1, cnt + 1, turn + 1);
        }
        root.remove(dessert);
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

            answer = -1;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    startX = i;
                    startY = j;
                    root = new HashSet<>();
                    root.add(arr[i][j]);
                    dfs(i, j, 0, 1, 0);
                }
            }
            System.out.println("#" + tc + " " + answer);
        }
    }
}
