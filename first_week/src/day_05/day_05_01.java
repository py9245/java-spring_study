package day_05;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

public class day_05_01 {

	public static void do_dfs(int[] arr, int idx, int n, int m, int sum) {
	    if (sum >= m && sum < answer) {
	        answer = sum;
	        return;
	    }
	    if (idx == n) return;
	    if (sum >= answer) return;

	    do_dfs(arr, idx + 1, n, m, sum + arr[idx]);

	    do_dfs(arr, idx + 1, n, m, sum);
	}
	
	static int answer;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int[] arr = new int[N];
			
			StringTokenizer toArr = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(toArr.nextToken());
			}
			
			Arrays.sort(arr);
			int total = 0;
			
			for (int num : arr) {
				total += num;
			}
			
			answer = total;
			do_dfs(arr, 0, N, M, 0);
			System.out.println("#"+tc+" "+(answer - M));
		}

	}

}
