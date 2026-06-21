import task1.Task01;
import task2.Task02;
import task3.Task03;
import task4.*;
import task5.Task05;
import task6.*;
import task7.*;
import task8.*;
import task9.Task09;
import task10.Task10;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Завдання 1: " + Task01.findFirstX(List.of("X-Ray-Specs", "sss", "X-Men")));

        System.out.println("Завдання 2: " + Task02.unwrap(List.of(Optional.of(1), Optional.empty(), Optional.of(4))));

        System.out.println("Завдання 3: " + Task03.getLongestName(List.of("Maks", "Denis")).orElse("N/A"));

        List<Employee> emps = List.of(new Employee("A", 2000), new Employee("B", 4500), new Employee("C", 7000),
        new Employee("D", 4000), new Employee("E", 2200), new Employee("F", 7600));
        System.out.println("Завдання 4: " + Task04.getBestBySalary(emps));

        System.out.println("Завдання 5: " + Task05.oddProduct(List.of(1, 2, 3, 4, 5)).orElse(0));

        Person p1 = new Person("Denis", List.of());
        Person p2 = new Person("Nazar", List.of());
        Person p3 = new Person("Vova", List.of(p1, p2));
        System.out.println("Завдання 6: " + Task06.getFriendNames(List.of(p3)));

        List<Transaction> trans = List.of(new Transaction(100, "Games"), new Transaction(67, "Games"), new Transaction(50, "Tech"));
        System.out.println("Завдання 7: " + Task07.sumByCategory(trans));

        List<Product> prods = List.of(new Product("A", 10), new Product("B", 50), new Product("C", 30));
        System.out.println("Завдання 8: " + Task08.getSecondExpensive(prods).orElse("N/A"));

        Map<Integer, Optional<String>> prodMap = Map.of(1, Optional.of("Phone"), 2, Optional.empty(), 3, Optional.of("pc"));
        System.out.println("Завдання 9: " + Task09.getUpperNames(prodMap));

        Map<String, List<Integer>> temps = Map.of("Kyiv", List.of(10, 20), "Odesa", List.of(25, 30));
        System.out.println("Завдання 10: " + Task10.getHottestCity(temps).orElse("N/A"));
    }
}