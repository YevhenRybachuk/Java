package task2;
import java.util.*;
import java.util.stream.Collectors;

public class Task02 {
    public static List<Integer> unwrap(List<Optional<Integer>> list) {
        return list.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}