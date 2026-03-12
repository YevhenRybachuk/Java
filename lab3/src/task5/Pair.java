package task5;

public class Pair<T, U> {

    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public boolean equalsPair(Pair<T, U> other) {
        return first.equals(other.first) && second.equals(other.second);
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}