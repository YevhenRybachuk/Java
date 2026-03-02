public class task1 {

        public static double circleArea(double radius) {

            if (radius < 0) {
                throw new IllegalArgumentException("Radius cannot be negative!");
            }

            return Math.PI * radius * radius;
        }

    public static void main(String[] args) {

        try {
            double r = -4;
            double area = circleArea(r);

            System.out.println("Circle area: " + area);

        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}

