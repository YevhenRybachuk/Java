package task9;
import java.util.*;
import java.util.stream.Collectors;

public class Task09 {
    public static List<String> getUpperNames(Map<Integer, Optional<String>> productMap) {
        return productMap.values().stream()
                .flatMap(Optional::stream)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}