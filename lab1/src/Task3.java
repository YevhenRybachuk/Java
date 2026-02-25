public class Task3 {

    public static void main(String[] args) {

        double weight = 2.3;

        int category;

        if (weight <= 0) {
            category = 0;
        } else if (weight <= 2) {
            category = 1;
        } else if (weight <= 30) {
            category = 2;
        } else {
            category = 3;
        }

        switch (category) {
            case 1:
                System.out.println("Дрібний вантаж");
                break;
            case 2:
                System.out.println("Посилка");
                break;
            case 3:
                System.out.println("Вантаж");
                break;
            default:
                System.out.println("Невідома категорія");
        }
    }
}