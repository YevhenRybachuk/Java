import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws InterruptedException {
        String text = "java is good java is fast threads count words java is cool language java good and fast";

        String[] words = text.split(" ");
        WordCounter counter = new WordCounter();
        List<Thread> threads = new ArrayList<>();

        int threadCount = 3;
        int partSize = (int) Math.ceil((double) words.length / threadCount);

        for (int i = 0; i < threadCount; i++) {
            int start = i * partSize;
            int end = Math.min(start + partSize, words.length);

            Thread thread = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    counter.increment(new Word(words[j]));
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        counter.printResult();
    }
}
