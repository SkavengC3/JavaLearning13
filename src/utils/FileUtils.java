package utils;

import logic.MyDiary;
import java.io.*;

public class FileUtils {

    public static void saveToFile(String path, MyDiary diary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < diary.getEntryCount(); i++) {
                writer.write(diary.getDate(i));
                writer.newLine();
                writer.write(diary.getEntry(i));
                writer.newLine();
            }
            System.out.println("Щоденник збережено.");
        } catch (IOException e) {
            System.out.println("Помилка при збереженні.");
        }
    }

    public static void loadFromFile(String path, MyDiary diary) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line, entry = "", date = null;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (date != null && !entry.isEmpty()) {
                        diary.addLoadedEntry(date, entry);
                    }
                    entry = "";
                    date = null;
                } else if (date == null) {
                    date = line;
                } else {
                    entry += line + "\n";
                }
            }
            if (date != null && !entry.isEmpty()) {
                diary.addLoadedEntry(date, entry);
            }

            System.out.println("Щоденник завантажено.");
        } catch (IOException e) {
            System.out.println("Не вдалося завантажити файл.");
        }
    }
}
