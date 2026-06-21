import java.util.Objects;

public class Word {
    private final String value;

    public Word(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.length();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Word other)) {
            return false;
        }
        return Objects.equals(value, other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
