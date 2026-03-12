package task2;
import java.util.*;

public class CollectionUtils {

    public static <T> Collection<T> uniqueElements(List<T> list) {
        return new HashSet<>(list);
    }

    public static <T> Map<T, Integer> countOccurrences(List<T> list) {

        Map<T, Integer> map = new HashMap<>();

        for (T element : list) {
            map.put(element, map.getOrDefault(element, 0) + 1);
        }

        return map;
    }
}