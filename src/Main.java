import java.io.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    static final int ENTRIES = 100;
    static String[] dates = new String[ENTRIES];
    static String[] entries = new String[ENTRIES];
    static int enCount = 0;
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Вітаю у щоденнику!");
        System.out.println("1. Створити новий щоденник");
        System.out.println("2. Завантажити існуючий з файлу");
        String choice = sc.nextLine();

        if ("2".equals(choice)) {
            System.out.print("Введіть шлях до файлу: ");
            String filePath = sc.nextLine();
            loadFromFile(filePath);
        }
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
                            System.out.print("Бажаєте зберегти щоденник у файл? (так/ні): ");
                            String saveChoice = sc.nextLine().toLowerCase();
                            if (saveChoice.equals("так")) {
                                System.out.print("Введіть шлях до файлу для збереження: ");
                                String savePath = sc.nextLine();
                                saveToFile(savePath);
                            }
                            System.out.println("Вихід з календаря.");
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

        public static void wIvent (Scanner sc){
            if (enCount >= ENTRIES) {
                System.out.println("Щоденник повний! Видаліть старі записи.");
                return;
            }

            System.out.print("Введіть дату (формат DD.ММ.YYYY HH:mm): ");
            String dateStr = sc.nextLine();

            try {
                Date parsedDate = dateFormat.parse(dateStr);
                String formatted = dateFormat.format(parsedDate);

                System.out.println("Введіть текст запису (порожній рядок — завершення):");
                String entry = "";
                String line;
                while (!(line = sc.nextLine()).isEmpty()) {
                    entry += line + "\n";
                }

                if (!entry.isEmpty()) {
                    dates[enCount] = formatted;
                    entries[enCount] = entry;
                    enCount++;
                    System.out.println("Запис додано успішно!");
                }
            } catch (Exception e) {
                System.out.println("Невірний формат дати і часу!");
            }

        }

        public static void delIvent (Scanner sc){
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

        public static void showIvent() {
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
        public static void saveToFile(String path) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                for (int i = 0; i < enCount; i++) {
                    writer.write(dates[i]);
                    writer.newLine();
                    writer.write(entries[i]);
                    writer.newLine();
                }
                writer.close();
                System.out.println("Щоденник збережено.");
            } catch (IOException e) {
                System.out.println("Помилка при збереженні.");
            }
        }
        public static void loadFromFile(String path) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                String line;
                String entry = "";
                String date = null;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        if (date != null && !entry.isEmpty() && enCount < ENTRIES) {
                            dates[enCount] = date;
                            entries[enCount] = entry;
                            enCount++;
                        }
                        entry = "";
                        date = null;
                    } else if (date == null) {
                        date = line;
                    } else {
                        entry += line + "\n";
                    }
                }
                if (date != null && !entry.isEmpty() && enCount < ENTRIES) {
                    dates[enCount] = date;
                    entries[enCount] = entry;
                    enCount++;
                }

                reader.close();
                System.out.println("Щоденник завантажено.");
            } catch (IOException e) {
                System.out.println("Не вдалося завантажити файл.");
            }
    }
}