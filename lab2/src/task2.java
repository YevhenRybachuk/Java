import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;
public class task2 {

        static Queue<Integer> tickets = new LinkedList<>();
        static int nextTicket = 1;

        public static void issueTicket() {
            tickets.add(nextTicket);
            System.out.println("Issued ticket: " + nextTicket);
            nextTicket++;
        }

        public static void serveTicket() {

            if (tickets.isEmpty()) {
                throw new NoSuchElementException("Queue is empty!");
            }

            int served = tickets.remove();
            System.out.println("Served ticket: " + served);
        }

        public static void main(String[] args) {

            try {
                issueTicket();
                issueTicket();
                issueTicket();

                serveTicket();
                serveTicket();
                serveTicket();

                serveTicket();

            } catch (NoSuchElementException e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
    }

