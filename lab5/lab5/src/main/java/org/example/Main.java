package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conn = DatabaseManager.getConnection()) {
            System.out.println("Система авторизації rybachuk");
            System.out.println("1. Реєстрація\n2. Вхід");
            System.out.print("Вибір: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Логін: ");
            String username = scanner.nextLine();
            System.out.print("Пароль: ");
            String password = scanner.nextLine();

            if (choice == 1) {
                registerUser(conn, username, password);
            } else {
                if (loginUser(conn, username, password)) {
                    System.out.println("Успіх! Дані таблиці:");
                    showTableInfo(conn);

                    showMainMenu(conn);

                } else {
                    System.out.println("Невірний логін або пароль.");
                }
            }
        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showMainMenu(Connection conn) throws SQLException {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ (Оберіть таблицю) ---");
            System.out.println("1. Автори (Author)");
            System.out.println("2. Статті (Article)");
            System.out.println("3. Рецензенти (Reviewer)");
            System.out.println("4. Рецензії (Review)");
            System.out.println("5. Оцінки (Evaluation)");
            System.out.println("6. Анотації (Annotation)");
            System.out.println("7. Категорії (JournalCategory)");
            System.out.println("8. Управління співавторством");
            System.out.println("9. Пошук (Search)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: authorSubMenu(conn); break;
                case 2: articleSubMenu(conn); break;
                case 3: reviewerSubMenu(conn); break;
                case 4: reviewSubMenu(conn); break;
                case 5: evaluationSubMenu(conn); break;
                case 6: annotationSubMenu(conn); break;
                case 7: journalCategorySubMenu(conn); break;
                case 8: linksSubMenu(conn); break;
                case 9: searchMenu(conn); break;
                case 0:
                    System.out.println("Вихід з програми...");
                    exit = true;
                    break;
                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
    }

    private static void searchMenu(Connection conn) throws SQLException {
        authorDAO dao = new authorDAO(conn);

        scanner.nextLine();

        System.out.println("\n=== ПОШУК АВТОРА ЗА ПРІЗВИЩЕМ ===");
        System.out.print("Введіть прізвище (або частину): ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Запит не може бути порожнім!");
            return;
        }

        List<author> found = dao.searchByLastName(query);

        if (found.isEmpty()) {
            System.out.println("Авторів не знайдено за запитом: " + query);
        } else {
            System.out.println("Знайдено: " + found.size());
            for (author a : found) {
                System.out.println("ID: " + a.getAuthorId() + " | " + a.getLastName() + " " + a.getFirstName());
            }
        }
    }

    private static void linksSubMenu(Connection conn) throws SQLException {
        article_has_authorDAO dao = new article_has_authorDAO(conn);
        System.out.println("\n--- Співавторство (Зв'язки) ---");
        System.out.println("1. Список зв'язків\n2. Призначити автора до статті\n3. Прибрати автора зі статті\n4. Назад");
        int action = scanner.nextInt();

        if (action == 1) {
            dao.printAllLinks();
        } else if (action == 2) {
            System.out.print("ID статті: "); int artId = scanner.nextInt();
            System.out.print("ID автора: "); int autId = scanner.nextInt();
            dao.link(artId, autId);
            System.out.println("Готово! Автор тепер співавтор статті.");
        } else if (action == 3) {
            System.out.print("ID статті: "); int artId = scanner.nextInt();
            System.out.print("ID автора: "); int autId = scanner.nextInt();
            dao.unlink(artId, autId);
            System.out.println("Зв'язок видалено.");
        }
    }

    private static void authorSubMenu(Connection conn) throws SQLException {
        authorDAO dao = new authorDAO(conn);
        System.out.println("\n--- Робота з авторами ---");
        System.out.println("1. Список всіх\n2. Додати\n3. Редагувати\n4. Видалити\n5. Назад");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                dao.findAll().forEach(a -> System.out.println(a.getAuthorId() + ": " + a.getFirstName() + " " + a.getLastName() + " (" + a.getEmail() + ")"));
                break;
            case 2:
                System.out.print("Прізвище: "); String ln = scanner.nextLine();
                System.out.print("Ім'я: "); String fn = scanner.nextLine();
                System.out.print("Email: "); String em = scanner.nextLine();
                System.out.print("Організація: "); String aff = scanner.nextLine();
                dao.save(new author(0, ln, fn, em, aff));
                System.out.println("Збережено!");
                break;
            case 3:
                System.out.print("Введіть ID автора для редагування: ");
                int upId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Нове прізвище: "); String newLn = scanner.nextLine();
                System.out.print("Нове ім'я: "); String newFn = scanner.nextLine();
                System.out.print("Новий Email: "); String newEm = scanner.nextLine();
                System.out.print("Нова організація: "); String newAff = scanner.nextLine();

                dao.update(new author(upId, newLn, newFn, newEm, newAff));
                System.out.println("Дані автора №" + upId + " оновлено!");
                break;
            case 4:
                System.out.print("ID для видалення: ");
                int delId = scanner.nextInt();
                dao.delete(delId);
                System.out.println("Видалено!");
                break;
        }
    }

    private static void registerUser(Connection conn, String user, String pass) throws SQLException {
        String sql = "INSERT INTO app_users (username, password_hash) VALUES (?, ?)";
        String hash = BCrypt.hashpw(pass, BCrypt.gensalt());
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, hash);
            pstmt.executeUpdate();
            System.out.println("Користувача " + user + " зареєстровано!");
        }
    }

    private static boolean loginUser(Connection conn, String user, String pass) throws SQLException {
        String sql = "SELECT password_hash FROM app_users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return BCrypt.checkpw(pass, rs.getString("password_hash"));
                }
            }
        }
        return false;
    }

    private static void articleSubMenu(Connection conn) throws SQLException {
        articleDAO dao = new articleDAO(conn);
        System.out.println("\n--- Керування статтями ---");
        System.out.println("1. Список\n2. Додати\n3. Видалити\n4. Назад");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                dao.findAll().forEach(art -> System.out.println(art.getArticleId() + ": " + art.getTitle() + " [" + art.getStatus() + "]"));
                break;
            case 2:
                System.out.print("Назва статті: ");
                String title = scanner.nextLine();

                System.out.print("Статус (напр. New, Review, Published): ");
                String status = scanner.nextLine();

                System.out.print("ID категорії журналу: ");
                int catId = scanner.nextInt();
                Date currentDate = new Date(System.currentTimeMillis());

                dao.save(new article(0, title, currentDate, status, catId));
                System.out.println("Статтю додано!");
                break;
            case 3:
                System.out.print("Введіть ID статті для видалення: ");
                int id = scanner.nextInt();
                dao.delete(id);
                System.out.println("Видалено.");
                break;
        }
    }

    private static void reviewerSubMenu(Connection conn) throws SQLException {
        reviewerDAO dao = new reviewerDAO(conn);
        System.out.println("\n--- Керування рецензентами ---");
        System.out.println("1. Список всіх рецензентів");
        System.out.println("2. Додати нового");
        System.out.println("3. Редагувати дані");
        System.out.println("4. Видалити");
        System.out.println("5. Назад");
        System.out.print("Вибір: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                System.out.println("\nСписок фахівців:");
                dao.findAll().forEach(r ->
                        System.out.println(r.getReviewerId() + ": " + r.getFirstName() + " " + r.getLastName() + " [" + r.getScientificDegree() + "]"));
                break;

            case 2:
                System.out.print("Прізвище: "); String ln = scanner.nextLine();
                System.out.print("Ім'я: "); String fn = scanner.nextLine();
                System.out.print("Email: "); String email = scanner.nextLine();
                System.out.print("Науковий ступінь (напр. к.т.н, проф.): "); String degree = scanner.nextLine();
                dao.save(new reviewer(0, ln, fn, email, degree));
                System.out.println("Рецензента додано до бази!");
                break;

            case 3:
                System.out.print("Введіть ID рецензента для оновлення: ");
                int upId = scanner.nextInt(); scanner.nextLine();
                System.out.print("Нове прізвище: "); String newLn = scanner.nextLine();
                System.out.print("Нове ім'я: "); String newFn = scanner.nextLine();
                System.out.print("Новий Email: "); String newEm = scanner.nextLine();
                System.out.print("Новий ступінь: "); String newDeg = scanner.nextLine();
                dao.update(new reviewer(upId, newLn, newFn, newEm, newDeg));
                System.out.println("Дані оновлено.");
                break;

            case 4:
                System.out.print("Введіть ID для видалення: ");
                int delId = scanner.nextInt();
                dao.delete(delId);
                System.out.println("Рецензента видалено.");
                break;
        }
    }

    private static void reviewSubMenu(Connection conn) throws SQLException {
        reviewDAO dao = new reviewDAO(conn);
        System.out.println("\n--- Керування рецензіями ---");
        System.out.println("1. Список всіх рецензій");
        System.out.println("2. Додати нову рецензію");
        System.out.println("3. Редагувати");
        System.out.println("4. Видалити");
        System.out.println("5. Назад");
        System.out.print("Вибір: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                dao.findAll().forEach(r ->
                        System.out.println("ID: " + r.getReviewId() + " | Дата: " + r.getReviewDate() +
                                " | Стаття ID: " + r.getArticleId() + " | Рецензент ID: " + r.getReviewerId()));
                break;

            case 2:
                System.out.print("Коментар: "); String comment = scanner.nextLine();
                System.out.print("ID статті: "); int artId = scanner.nextInt();
                System.out.print("ID рецензента: "); int revId = scanner.nextInt();

                Date today = new Date(System.currentTimeMillis());
                dao.save(new review(0, today, comment, artId, revId));
                System.out.println("Рецензію збережено!");
                break;

            case 3:
                System.out.print("ID рецензії для оновлення: ");
                int upId = scanner.nextInt(); scanner.nextLine();
                System.out.print("Новий коментар: "); String newComm = scanner.nextLine();
                System.out.print("Новий ID статті: "); int newArtId = scanner.nextInt();
                System.out.print("Новий ID рецензента: "); int newRevId = scanner.nextInt();

                dao.update(new review(upId, new Date(System.currentTimeMillis()), newComm, newArtId, newRevId));
                System.out.println("Оновлено!");
                break;

            case 4:
                System.out.print("ID для видалення: ");
                int delId = scanner.nextInt();
                dao.delete(delId);
                System.out.println("Видалено.");
                break;
        }
    }

    private static void evaluationSubMenu(Connection conn) throws SQLException {
        evaluationDAO dao = new evaluationDAO(conn);
        System.out.println("\n--- Керування оцінками ---");
        System.out.println("1. Список всіх оцінок\n2. Додати нову\n3. Редагувати\n4. Видалити\n5. Назад");
        System.out.print("Вибір: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                dao.findAll().forEach(e ->
                        System.out.println("ID: " + e.getEvaluationId() + " | Бал: " + e.getScore() +
                                " | Рекомендація: " + e.getRecommendation() + " | Рецензія ID: " + e.getReviewId()));
                break;

            case 2:
                System.out.print("Бал (1-10): "); int score = scanner.nextInt(); scanner.nextLine();
                System.out.print("Рекомендація (Accept/Reject): "); String rec = scanner.nextLine();
                System.out.print("ID рецензії (Review ID): "); int revId = scanner.nextInt();

                dao.save(new evaluation(0, score, rec, revId));
                System.out.println("Оцінку збережено!");
                break;

            case 3:
                System.out.print("ID оцінки для оновлення: ");
                int upId = scanner.nextInt(); scanner.nextLine();
                System.out.print("Новий бал: "); int nScore = scanner.nextInt(); scanner.nextLine();
                System.out.print("Нова рекомендація: "); String nRec = scanner.nextLine();
                System.out.print("Новий ID рецензії: "); int nRevId = scanner.nextInt();

                dao.update(new evaluation(upId, nScore, nRec, nRevId));
                System.out.println("Дані оновлено.");
                break;

            case 4:
                System.out.print("ID оцінки для видалення: ");
                int delId = scanner.nextInt();
                dao.delete(delId);
                System.out.println("Оцінку видалено.");
                break;
        }
    }

    private static void annotationSubMenu(Connection conn) throws SQLException {
        annotationDAO dao = new annotationDAO(conn);
        System.out.println("\n--- Керування анотаціями ---");
        System.out.println("1. Список всіх анотацій");
        System.out.println("2. Додати нову (UA/EN)");
        System.out.println("3. Редагувати");
        System.out.println("4. Видалити");
        System.out.println("5. Назад");
        System.out.print("Вибір: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                dao.findAll().forEach(a ->
                        System.out.println("ID: " + a.getAnnotationId() + " | Мова: " + a.getLanguage() +
                                " | Стаття ID: " + a.getArticleId() + " | Текст: " + a.getText()));
                break;

            case 2:
                System.out.print("Мова (напр. UA, EN): "); String lang = scanner.nextLine();
                System.out.print("Текст анотації: "); String text = scanner.nextLine();
                System.out.print("ID статті: "); int artId = scanner.nextInt();

                dao.save(new annotation(0, lang, text, artId));
                System.out.println("Анотацію додано!");
                break;

            case 3:
                System.out.print("ID анотації для оновлення: ");
                int upId = scanner.nextInt(); scanner.nextLine();
                System.out.print("Нова мова: "); String nLang = scanner.nextLine();
                System.out.print("Новий текст: "); String nText = scanner.nextLine();
                System.out.print("Новий ID статті: "); int nArtId = scanner.nextInt();

                dao.update(new annotation(upId, nLang, nText, nArtId));
                System.out.println("Дані оновлено.");
                break;

            case 4:
                System.out.print("ID анотації для видалення: ");
                int delId = scanner.nextInt();
                dao.delete(delId);
                System.out.println("Анотацію видалено.");
                break;
        }
    }
    private static void journalCategorySubMenu(Connection conn) throws SQLException {
        journalcategoryDAO dao = new journalcategoryDAO(conn);
        System.out.println("\n--- Керування категоріями журналів ---");
        System.out.println("1. Список всіх");
        System.out.println("2. Додати нову");
        System.out.println("3. Редагувати існуючу");
        System.out.println("4. Видалити");
        System.out.println("5. Назад");
        System.out.print("Ваш вибір: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1:
                System.out.println("\nСписок категорій:");
                dao.findAll().forEach(c ->
                        System.out.println("ID: " + c.getCategoryId() + " | Назва: " + c.getName() + " | Опис: " + c.getDescription()));
                break;

            case 2:
                System.out.print("Введіть назву: "); String name = scanner.nextLine();
                System.out.print("Введіть опис: "); String desc = scanner.nextLine();
                dao.save(new journalcategory(0, name, desc));
                System.out.println("Категорію успішно створено!");
                break;

            case 3:
                System.out.print("Введіть ID категорії для оновлення: ");
                int upId = scanner.nextInt(); scanner.nextLine();
                System.out.print("Нова назва: "); String newName = scanner.nextLine();
                System.out.print("Новий опис: "); String newDesc = scanner.nextLine();
                dao.update(new journalcategory(upId, newName, newDesc));
                System.out.println("Категорію оновлено!");
                break;

            case 4:
                System.out.print("Введіть ID категорії для видалення: ");
                int delId = scanner.nextInt();
                dao.delete(delId);
                System.out.println("Категорію видалено");
                break;

            case 5:
                return;
        }
    }



    private static void showTableInfo(Connection conn) throws SQLException {
        String sql = "SELECT * FROM article LIMIT 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();

            System.out.println("\nМетадані таблиці");
            int columnCount = metaData.getColumnCount();
            System.out.println("Назва таблиці: " + metaData.getTableName(1));
            System.out.println("Кількість колонок: " + columnCount);

            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Колонка №" + i + ": " +
                        metaData.getColumnName(i) + " [" +
                        metaData.getColumnTypeName(i) + "]");
            }
        }
    }
}
