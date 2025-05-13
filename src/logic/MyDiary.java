package logic;

import utils.FileUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MyDiary {
    private static final int ENTRIES = 100;
    private final String[] dates = new String[ENTRIES];
    private final String[] entries = new String[ENTRIES];
    private int enCount = 0;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public void runApp(Scanner sc) {
        int userChoice = 0;

        do {
            showMenu();
            try {
                userChoice = Integer.parseInt(sc.nextLine());
                switch (userChoice) {
                    case 1 -> addEntry(sc);
                    case 2 -> deleteEntry(sc);
                    case 3 -> viewEntries();
                    case 4 -> {
                        System.out.print("Бажаєте зберегти щоденник у файл? (так/ні): ");
                        String saveChoice = sc.nextLine().toLowerCase();
                        if ("так".equals(saveChoice)) {
                            System.out.print("Введіть шлях до файлу для збереження: ");
                            String savePath = sc.nextLine();
                            FileUtils.saveToFile(savePath, this);
                        }
                        System.out.println("Вихід з календаря.");
                    }
                    default -> {}
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть числове значення.");
            }
        } while (userChoice != 4);
    }

    public void showMenu() {
        System.out.println("\n--- Мій щоденник ---");
        System.out.println("1. Додати подію");
        System.out.println("2. Видалити подію");
        System.out.println("3. Переглянути події");
        System.out.println("4. Вихід");
    }

    public void addEntry(Scanner sc) {
        if (enCount >= ENTRIES) {
            System.out.println("Щоденник повний! Видаліть старі записи.");
            return;
        }

        System.out.print("Введіть дату (формат DD.MM.YYYY HH:mm): ");
        String dateStr = sc.nextLine();

        try {
            Date parsedDate = dateFormat.parse(dateStr);
            String formatted = dateFormat.format(parsedDate);

            System.out.println("Введіть текст запису (порожній рядок — завершення):");
            StringBuilder entryBuilder = new StringBuilder();
            String line;
            while (!(line = sc.nextLine()).isEmpty()) {
                entryBuilder.append(line).append("\n");
            }

            String entry = entryBuilder.toString();
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

    public void deleteEntry(Scanner sc) {
        if (enCount == 0) {
            System.out.println("Щоденник порожній!");
            return;
        }

        System.out.print("Введіть дату запису для видалення (DD.MM.YYYY): ");
        String dateToDelete = sc.nextLine();

        for (int i = 0; i < enCount; i++) {
            if (dates[i].startsWith(dateToDelete)) {
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

    public void viewEntries() {
        if (enCount == 0) {
            System.out.println("Щоденник порожній!");
            return;
        }

        System.out.println("\nВсі записи:");
        for (int i = 0; i < enCount; i++) {
            System.out.println("\nДата: " + dates[i]);
            System.out.println("Запис:\n" + entries[i]);
        }
    }

    public int getEntryCount() {
        return enCount;
    }

    public String getDate(int i) {
        return dates[i];
    }

    public String getEntry(int i) {
        return entries[i];
    }

    public void addLoadedEntry(String date, String entry) {
        if (enCount < ENTRIES) {
            dates[enCount] = date;
            entries[enCount] = entry;
            enCount++;
        }
    }
}
