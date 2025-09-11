package first_week;

public class first_day_04 {
	public static void main(String[] args) {
		String name1 = "Park";
		int age1 = 29;

		String name2 = "Min";
		int age2 = 25;
		int size = 100;
		String[] names = new String[size];
		int[] ages = new int[size];
		String[] hobbies = new String[size];

		
		names[0] = name1;
		names[1] = name2;
		ages[0] = age1;
		ages[1] = age2;
		hobbies[0] = "youtube";
		hobbies[1] = "game";

		
		Person park = new Person();
		park.name = names[0];
		park.age = ages[0];
		park.hobby = hobbies[0];
//		System.out.printf("나의 이름은 %s 나이는 %d 취미는 %s", park.name, park.age, park.hobby);

		
		Person min = new Person();
		min.name = names[1];
		min.age = ages[1];
		min.hobby = hobbies[1];
//		System.out.printf("나의 이름은 %s 나이는 %d 취미는 %s", min.name, min.age, min.hobby);

		park.info();
		min.info();
		for (int i = 0; i < 4; i++) {
			min.study((int) 1000000000);			
		}
	}

}
