package day_05;

import java.io.*;
import java.util.*;

public class day_05_02_dijkstra {
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		int[][] dxy = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());

			int[][] board = new int[N][N];
			for (int j = 0; j < N; j++) {
				String line = br.readLine();
				for (int j2 = 0; j2 < N; j2++) {
					board[j][j2] = line.charAt(j2) - '0';
				}
			}

			int[][] memo = new int[N][N];
			for (int j = 0; j < N; j++) {
				for (int j2 = 0; j2 < N; j2++) {
					memo[j][j2] = Integer.MAX_VALUE;
				}
			}

			PriorityQueue<int[]> hq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

			hq.add(new int[] { 0, 0, 0 });
			memo[0][0] = 0;

			int[] point;
			int d, x, y, nd, dx, dy, nx, ny;

			while (!hq.isEmpty()) {
				point = hq.poll();
				d = point[0];
				x = point[1];
				y = point[2];

				if (x == N - 1 && y == N - 1) {
					System.out.println("#" + tc + " " + d);
					break;
				}

				for (int[] didx : dxy) {
					dx = didx[0];
					dy = didx[1];
					nx = x + dx;
					ny = y + dy;
					if (!(0 <= nx && nx < N && 0 <= ny && ny < N))
						continue;
					nd = d + board[nx][ny];
					if (memo[nx][ny] > nd) {
						memo[nx][ny] = nd;
						hq.add(new int[] { nd, nx, ny });
					}

				}

			}
		}
	}
}
