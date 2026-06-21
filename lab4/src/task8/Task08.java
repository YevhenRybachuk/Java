package task8;
import java.util.*;

public class Task08 {
    public static Optional<String> getSecondExpensive(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingDouble((Product p) -> p.price).reversed())
                .skip(1)
                .map(p -> p.name)
                .findFirst();
    }
}