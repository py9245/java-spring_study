package day_06_1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class day_06_02 {
	static int answer;
	static Integer[] nums;
	static int M, N;
	public static void dfs(int idx, int total) {
		if (total == M) { answer += 1; return; }
		if (idx == N) return;
		dfs(idx + 1, total + nums[idx]);
		dfs(idx + 1, total);
		
	}
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			answer = 0;
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			nums = new Integer[N];
			
			StringTokenizer innum = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				nums[i] = Integer.parseInt(innum.nextToken());
			}
			Arrays.sort(nums, Collections.reverseOrder());
			dfs(0, 0);
			System.out.println("#" + tc + " " + answer);
		}
	}
}
