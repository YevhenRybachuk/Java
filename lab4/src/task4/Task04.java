package task4;
import java.util.*;
import java.util.stream.Collectors;

public class Task04 {
    public static Map<String, Optional<Employee>> getBestBySalary(List<Employee> employees) {
        return employees.stream().collect(Collectors.groupingBy(
                e -> {
                    if (e.salary < 3000) return "< 3000";
                    if (e.salary <= 5000) return "3000–5000";
                    return "> 5000";
                },
                Collectors.maxBy(Comparator.comparingDouble(e -> e.salary))
        ));
    }
}