package task6;

import java.util.*;

public class ShapeUtils {

    public static double calculateTotalArea(List<? extends Shape> shapes) {

        double total = 0;

        for (Shape s : shapes) {
            total += s.getArea();
        }

        return total;
    }
}