import java.util.Arrays;
import java.util.*;
import java.io.*;

public class first_day_02 {
	public static void main(String[] args) throws IOException {
		int[][] arr1 = new int[5][5];
		System.out.println(Arrays.deepToString(arr1));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); // 5
        int m = Integer.parseInt(st.nextToken()); // 10

        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(n + " " + m);
        System.out.println(Arrays.toString(arr));
	}
}
