package third_day;

public class SingletonPatten {
	public static void main(String[] args) {
		Manager mana = new Manager("park", 25);
		Manager mana2 = new Manager("park", 25);

		mana.show();
		mana2.show();
		mana.add("a");
		mana.add("b");
		mana.add("c");
		mana.add("d");
		mana.add("e");
		mana.add("f");
		mana.add("g");
		mana.add("h");
		mana2.add("i");
		mana2.add("j");
		mana.show();
		mana2.show();
		mana2.add("j");
	}
}
