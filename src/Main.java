import logic.MyDiary;
import utils.FileUtils;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyDiary diary = new MyDiary();

        System.out.println("Вітаю у щоденнику!");
        System.out.println("1. Створити новий щоденник");
        System.out.println("2. Завантажити існуючий з файлу");
        String choice = sc.nextLine();

        if ("2".equals(choice)) {
            System.out.print("Введіть шлях до файлу: ");
            String filePath = sc.nextLine();
            FileUtils.loadFromFile(filePath, diary);
        }

        diary.runApp(sc);
        sc.close();
    }
}
