import java.util.Arrays;

public class first_day {
	public static void main(String[] args) {
// ctrl + shift + f 자동 탭 정리
// 자료형, 변수선언 if, for switch while
//		int[] arr1; //권장
//		int arr2[]; //비권장
//		
//		//배열 초기화
//		int[] arr3 = new int[10];
//		System.out.println(Arrays.toString(arr3));
//		
//		String[] string = new String[10];
//		System.out.println(Arrays.toString(string));
//		

//		int[] arr5 = new int[] {1,2,3,4,5}; //재할당 가능
//		System.out.println(Arrays.toString(arr5)); 
//		
//		int[] arr6 = {1,2,3,4,5,6}; // 선언과 동시에 이루어질 떄만 가능
//		System.out.println(Arrays.toString(arr6));
		// arr7 = = new int[] {1,2,3,4,5};

//		int[] arr10 = { 3, 5, 12, 4, 5, 2, 3, 4 };
//		for (int i = 0; i < arr10.length; i++) {
//			System.out.print(arr10[i]);
//		}
//		System.out.println();
//
//		for (int i : arr10) {
//			System.out.println(i);
//		}
//		for (int j = 0; j < arr10.length; j++) {
//			System.out.println(j);
//			arr10[j] *= 2;
//		}
//		for (int i : arr10) {
//			System.out.println(i);
//		}
//		float[] farr = new float[10];
//		double[] darr = new double[10];
//		char[] carr = new char[10];
//		boolean[] barr = new boolean[10];
//		
//		System.out.println(Arrays.toString(farr));
//		System.out.println(Arrays.toString(darr));
//		System.out.println(Arrays.toString(carr));
//		System.out.println(Arrays.toString(barr));
//		System.out.println(farr[0]);
//		System.out.println(darr[0]);
//		System.out.println(carr[0]);
//		System.out.println(barr[0]);
//		System.out.println(((Object)farr[0]).getClass().getSimpleName()); // Float
//		System.out.println(((Object)darr[0]).getClass().getSimpleName()); // Double
//		System.out.println(((Object)carr[0]).getClass().getSimpleName()); // Character
//		System.out.println(((Object)barr[0]).getClass().getSimpleName()); // Boolean
		int[] original = {1,2,3};
		int[] copyarr = original;
		
		copyarr[0] = 10;
		System.out.println(Arrays.toString(original));
		System.out.println(Arrays.toString(copyarr));
	}
}
