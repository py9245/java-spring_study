

public class Person {
	String name;
	int age;
	String hobby;
	long study_time;
	void info() {
		System.out.printf("나의 이름은 %s 나이는 %d 취미는 %s입니다.%n", name, age, hobby);
		System.out.printf("공부는 총 %d시간 했습니다%n",study_time);
	}
	
	void study(int time) {
		study_time += time;
		System.out.printf("방금 %d시간 공부를 더 했습니다.%n", study_time);
		info();
	}
	void study(long time) {
		study_time += time;
		System.out.printf("방금 %d시간 공부를 엄청 했습니다.%n", study_time);
		info();
		
	}
	void study (Short time) {
		study_time += time;
		System.out.printf("방금 %d시간 공부를 엄청 했습니다.%n", study_time);
		info();

	}
}