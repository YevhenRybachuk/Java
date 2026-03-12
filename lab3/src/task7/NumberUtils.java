package task7;
import java.util.*;

public class NumberUtils {

    public static void addToList(List<? super Integer> list) {

        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
    }
}
