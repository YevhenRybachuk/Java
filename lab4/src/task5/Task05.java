package task5;
import java.util.*;

public class Task05 {
    public static Optional<Integer> oddProduct(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 != 0)
                .reduce((a, b) -> a * b);
    }
}