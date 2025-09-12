import java.util.Random;

public class first_day_03 {
	public static void main(String[] args) {
		int N = 3;
		int[][] type = { { 1, 1, 1 }, { 1, 3, 2 }, { 3, 2, 1 } };
		for (int i = 0; i < N; i++) {
			education(type[i][0]);
			lunch(type[i][1]);
			way(type[i][2]);
			if (solve()) {
				System.out.println("과제를 해야겠어");
			}
			else {
				System.out.println("과제는 PASS");				
			}
		}
	}

	public static void education(int type) {
		switch (type) {
		case 1:
			System.out.println("LIVE수강");
			break;
			
		case 2:
			System.out.println("월말평가");
			break;
			
		case 3:
			System.out.println("과목평가");
			break;

		default:
			System.out.println("잘못 입력 하였습니다.");
			break;
		}
	}

	public static void lunch(int type) {
		switch (type) {
		case 1:
			System.out.println("한식을 먹었어");
			break;
		case 2:
			System.out.println("중식을 먹었어");
			break;
		case 3:
			System.out.println("일식을 먹었어");
			break;

		default:
			System.out.println("잘못 입력 하였습니다.");
			break;
		}
	}

	public static void way(int type) {
		switch (type) {
		case 1:
			System.out.println("걸어서 갔어");
			break;
		case 2:
			System.out.println("버스를 탔어");
			break;
		case 3:
			System.out.println("지하철을 탔어");
			break;

		default:
			System.out.println("잘못 입력 하였습니다.");
			break;
		}
	}
	public static boolean solve() {
		return new Random().nextBoolean();
	}

}
