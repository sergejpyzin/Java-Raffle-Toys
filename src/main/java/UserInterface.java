import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    UserInteraction interaction = new UserInteraction();
    ToyService toyService = new ToyService();

    public  void menuApp(String pathRead, String pathWrite) {
        Scanner scanner = new Scanner(System.in);
        String answer = "";
        List<Toy> toys = null;
        do {
            System.out.println("""
                    Здравствуйте, Вас приветствует программа для розыгрыша игрушек.
                    Данная программа имеет следующий функционал:
                    1. Добавление игрушки в меню розыгрыша;
                    2. Розыгрыш игрушек;
                    Результаты розыгрыша можно вывести на экран и сохранить в текстовом файле.
                    =================================
                    Введите 1 для добавления игрушки в список для розыгрыша;
                    Введите 2 для проведения розыграша;
                    Введите 3 для выхода из программы.
                    =================================
                    Введите номер меню:""");
            answer = scanner.next();
            if (answer.equals("1")){
                toyService.toyService();
            }
            if (answer.equals("2")){
                List<Toy> toysForRaffle = FileIO.readFile(pathRead);
                if (toysForRaffle.isEmpty()){
                    System.out.println("""
                            Магазин игрушек пуст. Внесите хотя бы одну игрушку.
                            ===================================================""");
                    toyService.toyService();
                }else{
                    toyService.raffle(toysForRaffle, pathWrite);
                }
            }
        }while (!answer.equals("3"));


    }
}
