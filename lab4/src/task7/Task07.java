package task7;
import java.util.*;
import java.util.stream.Collectors;

public class Task07 {
    public static Map<String, Double> sumByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.category,
                        Collectors.summingDouble(t -> t.amount)
                ));
    }
}