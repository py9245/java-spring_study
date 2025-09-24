package first_day;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Hanaro {
	static final int bit = 1 << 20;
	static int N, cnt;
	static double cost, multi;
	static int[][] locations;
	static PriorityQueue<double[]> pq;

	public static double GetDist(int x1, int x2, int y1, int y2) {
//		System.out.println(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			cost = 0.0;
			N = Integer.parseInt(br.readLine());
			locations = new int[N][2];
			StringTokenizer xLine = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				locations[i][0] = Integer.parseInt(xLine.nextToken());
			}
			StringTokenizer yLine = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				locations[i][1] = Integer.parseInt(yLine.nextToken());
			}
			multi = Double.parseDouble(br.readLine());
			cnt = 0;
			boolean[] visi = new boolean[N];
			pq = new PriorityQueue<>((a, b) -> Double.compare(a[0], b[0]));
			int sx = locations[0][0];
			int sy = locations[0][1];
			for (int i = 1; i < locations.length; i++) {
				pq.offer(new double[] { GetDist(sx, locations[i][0], sy, locations[i][1]), 0, i });
			}
			visi[0] = true;
			while (!pq.isEmpty()) {
				double[] nxt = pq.poll();
				double d = nxt[0];
				int s = (int) nxt[1];
				int e = (int) nxt[2];
				if (cnt == N - 1)
					break;
				if (visi[e])
					continue;
				visi[e] = true;
//				System.out.println(d);
				cost += d * multi;
				cnt++;
				for (int ne = 0; ne < locations.length; ne++) {
					if (visi[ne])
						continue;
					pq.offer(new double[] { GetDist(locations[e][0], locations[ne][0], locations[e][1], locations[ne][1]),
							e, ne });
				}

			}
			System.out.println("#" + tc + " " + Math.round(cost));
		}
	}
}
