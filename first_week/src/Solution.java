import java.util.*;
import java.io.*;


class Solution {
    public static void main(String args[]) throws IOException {
    	System.setIn(new FileInputStream("res/input.txt"));
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int T = Integer.parseInt(br.readLine());
    	int N, M, K;
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	N = Integer.parseInt(st.nextToken());
    	M = Integer.parseInt(st.nextToken());
    	K = Integer.parseInt(st.nextToken());
    	Map<Integer, ArrayList<int[]> > tiket = new HashMap<>(); 
    	for (int i = 0; i < K; i++) {
    		StringTokenizer line = new StringTokenizer(br.readLine());
    		int[] item = new int[3];
    		int keyidx = Integer.parseInt(line.nextToken());
			for (int j = 2; j >= 0; j--) {
				item[j] = Integer.parseInt(line.nextToken());
			}
			tiket[i].append(item)
		}
    }
}
