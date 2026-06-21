package task6;
import java.util.List;

public class Person {
    public String name;
    public List<Person> friends;
    public Person(String n, List<Person> f) { name = n; friends = f; }
}