package day_06;

import java.io.*;
import java.util.*;

public class day_06_01 {
	static int check = (1 << ('z' - 'a' + 1)) - 1;
	static long answer;

	public static long comb(int n, int k) {
		return fact(n) / (fact(k) * fact(n - k));
	}

	public static long fact(int x) {
		long res = 1;
		for (int i = 2; i <= x; i++)
			res *= i;
		return res;
	}

	public static void dfs(int idx, int IntString, int cnt, int[] arr) {
		if (IntString == check) {
			answer += 1 << cnt;
			return;
		}
		if (cnt == 0) return;
		dfs(idx + 1, IntString | arr[idx], cnt - 1, arr);
		dfs(idx + 1, IntString, cnt - 1, arr);

	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			answer = 0;
			int[] TranceString = new int[N];

			for (int j = 0; j < N; j++) {
				String line = br.readLine();
				for (int k = 0; k < line.length(); k++) {
					TranceString[j] |= 1 << (line.charAt(k) - 'a');
				}
			}
			dfs(0, 0, N, TranceString);
			System.out.println("#" + tc + " " + answer);
		}
	}

}
