import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    static final int ENTRIES = 100;
    static String[] dates = new String[ENTRIES];
    static String[] entries = new String[ENTRIES];
    static int enCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int userChoice = 0;

        do {
            showMenu();
            try {
                userChoice = Integer.parseInt(sc.nextLine());
                switch (userChoice) {
                    case 1:
                        wIvent(sc);
                        break;
                    case 2:
                        delIvent(sc);
                        break;
                    case 3:
                        showIvent();
                        break;
                    case 4:
                        System.out.println("Вихід з календаря.");
                        break;
                    default:

                }

            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть числове значення.");
            }
        } while (userChoice != 4);
        sc.close();
    }

    public static void showMenu() {
        System.out.println("\n--- Мій щоденник ---");
        System.out.println("1. Додати подію");
        System.out.println("2. Видалити подію");
        System.out.println("3. Переглянути події");
        System.out.println("4. Вихід");
    }

    public static final void wIvent (Scanner sc){
        if (enCount >= ENTRIES) {
            System.out.println("Щоденник повний! Видаліть старі записи.");
            return;
        }

        System.out.print("Введіть дату (формат DD.ММ.YYYY): ");
        String date = sc.nextLine();

        if (!isValidDate(date)) {
            System.out.println("Невірний формат дати!");
            return;
        }

        System.out.println("Введіть текст запису (для завершення введіть порожній рядок):");
        String entry = "";
        String line;
        while (!(line = sc.nextLine()).isEmpty()) {
            entry += line + "\n";
        }

        if (!entry.isEmpty()) {
            dates[enCount] = date;
            entries[enCount] = entry;
            enCount++;
            System.out.println("Запис додано успішно!");
        }

    }

    public static final void delIvent(Scanner sc){
        if (enCount == 0) {
            System.out.println("Щоденник порожній!");
            return;
        }

        System.out.print("Введіть дату запису для видалення (DD.ММ.YYYY): ");
        String dateToDelete = sc.nextLine();

        for (int i = 0; i < enCount; i++) {
            if (dates[i].equals(dateToDelete)) {
                for (int j = i; j < enCount - 1; j++) {
                    dates[j] = dates[j + 1];
                    entries[j] = entries[j + 1];
                }
                enCount--;
                System.out.println("Запис видалено успішно!");
                return;
            }
        }
        System.out.println("Запис з такою датою не знайдено!");
    }

    public static final void showIvent(){
        if (enCount == 0) {
            System.out.println("Щоденник порожній!");
            return;
        }

        System.out.println("\nВсі записи:");
        for (int i = 0; i < enCount; i++) {
            System.out.println("\nДата: " + dates[i]);
            System.out.println("Запис:");
            System.out.println(entries[i]);
        }
    }

    private static boolean isValidDate(String date) {
        if (date.length() != 10) return false;

        try {
            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6, 10));

            if (date.charAt(2) != '.' || date.charAt(5) != '.') return false;
            if (day < 1 || day > 31) return false;
            if (month < 1 || month > 12) return false;
            if (year < 1900 || year > 2100) return false;

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}