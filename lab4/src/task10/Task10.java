package task10;
import java.util.*;

public class Task10 {
    public static Optional<String> getHottestCity(Map<String, List<Integer>> cityData) {
        return cityData.entrySet().stream()
                .max(Comparator.comparingDouble(entry ->
                        entry.getValue().stream()
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(Double.NEGATIVE_INFINITY)
                ))
                .map(Map.Entry::getKey);
    }
}