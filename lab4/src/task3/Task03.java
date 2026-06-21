package task3;
import java.util.*;

public class Task03 {
    public static Optional<String> getLongestName(List<String> names) {
        return names.stream()
                .max(Comparator.comparingInt(String::length));
    }
}