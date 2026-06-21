package task6;
import java.util.*;
import java.util.stream.Collectors;

public class Task06 {
    public static List<String> getFriendNames(List<Person> people) {
        return people.stream()
                .flatMap(p -> p.friends.stream())
                .map(f -> f.name.toUpperCase())
                .distinct()
                .collect(Collectors.toList());
    }
}