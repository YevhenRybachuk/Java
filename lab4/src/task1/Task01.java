package task1;
import java.util.*;

public class Task01 {
    public static String findFirstX(List<String> list) {
        return list.stream()
                .filter(s -> s.startsWith("X") && s.length() > 5)
                .findFirst()
                .orElse("Default");
    }
}