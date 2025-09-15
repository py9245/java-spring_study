package third_day;

import java.util.Arrays;

public class Manager {
    private String name;
    private int age;

    private static String[] member = new String[10];
    private static int tail;

    public Manager(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void show() {
        System.out.println("Manager 이름: " + name);
        System.out.println("Manager 나이: " + age);
        System.out.println("공용 멤버: " + Arrays.toString(member));
    }


    public static void add(String mem) {
        if (tail == 10) {
            System.out.println("인원 초과");
            return;
        }
        member[tail] = mem;
        tail += 1;
    }
}
