public class Task2 {

    public static void main(String[] args) {

        int age = 56;
        boolean isVip = true;

        boolean hasDiscount = hasDiscount(age, isVip);

        if (hasDiscount) {
            System.out.println("Знижка є");
        } else {
            System.out.println("Знижки нема");
        }
    }

    public static boolean hasDiscount(int age, boolean isVip) {

        if (age >= 60 || isVip) {
            return true;
        } else {
            return false;
        }
    }
}