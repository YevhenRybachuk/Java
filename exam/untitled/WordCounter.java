import java.util.HashMap;
import java.util.Map;

public class WordCounter {
    private final HashMap<Word, Integer> words = new HashMap<>();

    public void increment(Word word) {
        synchronized (this) {
            words.put(word, words.getOrDefault(word, 0) + 1);
        }
    }

    public void printResult() {
        for (Map.Entry<Word, Integer> entry : words.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
