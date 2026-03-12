package task6;

public class Rectangle extends Shape {

    double width;
    double height;

    public Rectangle(double w, double h) {
        width = w;
        height = h;
    }

    double getArea() {
        return width * height;
    }
}