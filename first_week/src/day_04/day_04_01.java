package day_04;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class day_04_01 {
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int i = 1; i <= T; i++) {

			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] graph = new ArrayList[N + 1];

			for (int k = 0; k < M; k++) {
				graph[k] = new ArrayList<>();
			}

			for (int j = 0; j < M; j++) {
				StringTokenizer line = new StringTokenizer(br.readLine());
				int p = Integer.parseInt(line.nextToken());
				int c = Integer.parseInt(line.nextToken());
				graph[p].add(c);
			}
			StringTokenizer se = new StringTokenizer(br.readLine());
			int S = Integer.parseInt(se.nextToken());
			int E = Integer.parseInt(se.nextToken());

			int answer = 0;

			ArrayList<Integer> stack = new ArrayList<>();
			stack.add(S);

			while (!stack.isEmpty()) {
				int n = stack.remove(stack.size() - 1);
				if (n == E) {
					answer = 1;
					break;
				}
				if (graph[n] == null) {
					continue;
				}
				for (int num : graph[n]) {
					stack.add(num);
				}
			}
			System.out.println("#" + i + " " + answer);

		}

	}
}
