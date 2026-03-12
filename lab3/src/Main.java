import task1.*;
import task2.*;
import task3.*;
import task4.*;
import task5.*;
import task6.*;
import task7.*;
import task8.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("TASK 1: Student Registry");

        StudentRegistry registry = new StudentRegistry();

        registry.addStudent(new Student(1, "Maks", 18));
        registry.addStudent(new Student(2, "Denis", 19));
        registry.addStudent(new Student(3, "Dima", 20));

        System.out.println("All students:");
        registry.showAllStudents();

        System.out.println("Find student id=2:");
        System.out.println(registry.findStudent(2));

        registry.removeStudent(1);

        System.out.println("After removing student id=1:");
        registry.showAllStudents();



        System.out.println("\nTASK 2: Unique Elements & Count");

        List<String> list = Arrays.asList(
                "mars", "venus", "earth", "mars", "jupiter", "earth"
        );

        Collection<String> unique = CollectionUtils.uniqueElements(list);
        System.out.println("Unique elements:");
        System.out.println(unique);

        Map<String, Integer> counts = CollectionUtils.countOccurrences(list);
        System.out.println("Occurrences:");
        System.out.println(counts);



        System.out.println("\nTASK 3: Generic Box");

        Box<Integer> intBox = new Box<>();
        intBox.put(13);
        System.out.println("Integer box: " + intBox.get());

        Box<String> strBox = new Box<>();
        strBox.put("bambam");
        System.out.println("String box: " + strBox.get());



        System.out.println("\nTASK 4: Generic findMax");

        Integer[] intArr = {5, 6, 9, 2};
        Double[] doubleArr = {3.5, 5.2, 2.9};
        String[] strArr = {"Apple", "Orange", "Banana"};
        Character[] charArr = {'a', 'b', 'c'};

        System.out.println("Max Integer: " + GenericUtils.findMax(intArr));
        System.out.println("Max Double: " + GenericUtils.findMax(doubleArr));
        System.out.println("Max String: " + GenericUtils.findMax(strArr));
        System.out.println("Max Character:" + GenericUtils.findMax(charArr));


        System.out.println("\nTASK 5: Generic Pair");

        Pair<Integer, String> p1 = new Pair<>(1, "Hello");
        Pair<Integer, String> p2 = new Pair<>(1, "Hello");
        //Pair<String, List<Integer>> p3 = new Pair<>("Numbers", Arrays.asList(1,2,3));

        System.out.println(p1);
        System.out.println(p2);
        //System.out.println(p3);

        System.out.println("Pairs equal: " + p1.equalsPair(p2));



        System.out.println("\nTASK 6: Upper-bounded Wildcard");

        List<Shape> shapes = new ArrayList<>();

        shapes.add(new Circle(3));
        shapes.add(new Rectangle(5, 4));

        double totalArea = ShapeUtils.calculateTotalArea(shapes);

        System.out.println("Total area: " + totalArea);



        System.out.println("\nTASK 7: Lower-bounded Wildcard");

        List<Integer> intList = new ArrayList<>();
        List<Number> numList = new ArrayList<>();

        NumberUtils.addToList(intList);
        NumberUtils.addToList(numList);

        System.out.println("Integer list:");
        System.out.println(intList);

        System.out.println("Number list:");
        System.out.println(numList);



        System.out.println("\nTASK 8: Animal Shelter");

        Dog dog = new Dog();
        Cat cat = new Cat();
        Labrador labrador = new Labrador();

        AnimalShelter shelter = new AnimalShelter();

        List<Dog> dogs = new ArrayList<>();
        dogs.add(dog);
        dogs.add(labrador);

        shelter.addAnimals(dogs);
        shelter.addOtherAnimal(cat);

        System.out.println("Animal sounds:");
        shelter.printAnimalSounds();
    }
}