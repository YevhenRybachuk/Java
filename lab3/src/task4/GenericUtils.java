package task4;

public class GenericUtils {

    public static <T extends Comparable<T>> T findMax(T[] array) {

        T max = array[0];

        for (T element : array) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }

        return max;
    }
}