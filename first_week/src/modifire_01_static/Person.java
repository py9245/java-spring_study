package modifire_01_static;

public class Person {
	static int pCount;
	
	String name;
	int age;
	String hobby;
	
	public Person() {
		System.out.println("기본 생성자 호출");
	}
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
		pCount +=  1;
	}
	
	public Person(String name, int age, String hobby) {
		this(name, age);
		this.hobby = hobby;
	}
	
	public void ChangeName (String name) {
		this.name = name;
		pCount += 1;
		if (name == this.name) {
			System.out.println("이름 변경이 완료되었습니다.");
		}
		else {
			System.out.println("엇 이름 변경이 안됐습니다.!");			
		}
	}
}
