package task6;

public class Circle extends Shape {

    double radius;

    public Circle(double r) {
        radius = r;
    }

    double getArea() {
        return Math.PI * radius * radius;
    }
}
