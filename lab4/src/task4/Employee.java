package task4;

public class Employee {
    public String name;
    public double salary;
    public Employee(String n, double s) { name = n; salary = s; }
    @Override public String toString() { return name + "(" + salary + ")"; }
}