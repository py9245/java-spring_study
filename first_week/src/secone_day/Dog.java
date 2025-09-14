package secone_day;

public class Dog {
	String name;
	int age;
	
	
	public Dog() {
		System.out.println("기본 생성자 호출");
		// TODO Auto-generated constructor stub
	}
	public Dog(int age) {
		this("익명", age);
	}
//	public Dog() {
//		
//	}
//	public Dog(String name, int age) {
//		// TODO Auto-generated constructor stub
//		name = n;
//		age = a;
//	}
	public Dog(String name, int age) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.age = age;
	}
}
