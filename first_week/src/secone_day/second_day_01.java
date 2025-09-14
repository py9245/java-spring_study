package secone_day;

public class second_day_01 {
	public static void main(String[] args) {
//		Dog dog = new Dog();
//		dog.name = "마루";
//		dog.age = 12;
		Dog dog = new Dog("마리", 12);
		Dog cat = new Dog();
		Dog dog2 = new Dog(4);
		System.out.println(dog2.name + dog2.age);
	}
}
