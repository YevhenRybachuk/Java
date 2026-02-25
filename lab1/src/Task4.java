public class Task4 {

    public static void main(String[] args) {

        int number = 1221;
        int original = number;
        int reversed = 0;

        while (number > 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number = number / 10;
        }

        if (original == reversed) {
            System.out.println("Паліндром");
        } else {
            System.out.println("Не паліндром");
        }
    }
}