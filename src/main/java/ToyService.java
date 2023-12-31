import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class ToyService {

    UserInteraction userInteraction = new UserInteraction();

    /**
     * Метод вывода на печать параметризованного списка экземпляров класса Toy
     * @param toyList - параметризованный список экземпляров класса Toy*/
    public void showToys(List<Toy> toyList) {
        if (toyList.isEmpty()) {
            System.out.println("Список игрушек пуст");
        } else {
            toyList.forEach(System.out::println);
        }

    }

    /**
     * Метод создания параметризованного списка экземпляров класса Toy.
     * Запрашивает информация у пользователя, на основе полученных данных создает параметризованный список
     * и записывает его в файл
     * @param pathWrite  - строковое значение месторасположения файла, в который будет производиться запись
     * */
    public void toyService(String pathWrite) {
        List<Toy> toys = new ArrayList<>();
        List<Integer> idArray = new ArrayList<>();
        List<String> nameArray = new ArrayList<>();
        List<Integer> frequencyOfLossArray = new ArrayList<>();
        int size = userInteraction.checkingUserAnswerForInt("Введите количество игрушек для розыгрыша:");
        for (int i = 1; i < size + 1; i++) {
            idArray.add(i);
        }
        while (size > 0) {
            String userAnswer = userInteraction.checkingUserAnswerFromEmpty("""
                    Введите игрушки для розыгрыша в формате:
                    'количество игрушек, наименование, шанс выпадения в процентах'
                    Пример: 1, конструктор, 20""");
            String[] splitString = userAnswer.split(" ");
            int count = userInteraction.checkStringParseToInt(splitString, 0);
            for (int i = 0; i < count; i++) {
                if (isSize(splitString)) {
                    nameArray.add(splitString[1].replace(",", ""));
                    frequencyOfLossArray.add(userInteraction.checkStringParseToInt(splitString, 2));
                    size--;
                } else {
                    System.out.println("Внимание! Ошибка формата ввода. ");
                }
            }
        }
        for (int i = 0; i < idArray.size(); i++) {
            toys.add(new Toy(idArray.get(i), nameArray.get(i), frequencyOfLossArray.get(i)));
        }
        FileIO.writeFile(toys, pathWrite);
    }

    /**
     * Метод создания параметризованного списка экземпляров класса Toy.
     * Из полученного параметризованного списка экземпляров класса Toy создается параметризованный список,
     * на основании случайного распределения элементов полученного списка.
     * @param toysList - параметризованный список экземпляров класса Toy
     * @param path  - строковое значение месторасположения файла, в который будет производиться запись
     * */
    public void raffleAllToys (List<Toy> toysList, String path) {
        List<Toy> toyRaffle = createPriorityQueue(toysList);
        List<Toy> resultRaffle = new ArrayList<>();
        while (!toyRaffle.isEmpty()) {
            int index = randomToyIndex(toyRaffle);
            resultRaffle.add(toyRaffle.get(index));
            toyRaffle.remove(toyRaffle.get(index));
        }
        System.out.println("Розыгрыш проведен успешно");
        FileIO.writeFile(resultRaffle, path);
    }

    /**
     * Метод случайного распределения экземпляров параметризованного списка экземпляров класса Toy.
     * Возвращает индекс случайного экземпляра параметризованного списка
     * @param toysList - параметризованный список экземпляров класса Toy
     * */
    public int randomToyIndex(List<Toy> toysList) {
        List<Toy> toyRaffle = createPriorityQueue(toysList);
        double totalWeight = 0.0d;
        for (Toy toy : toyRaffle) {
            totalWeight += toy.getFrequencyOfLoss();
        }
        int randomIndex = -1;
        double random = Math.random() * totalWeight;
        for (int i = 0; i < toyRaffle.size(); ++i) {
            int toyWeight = toyRaffle.get(i).getFrequencyOfLoss();
            random -= toyWeight;
            if (random <= 0.0d) {
                randomIndex = i;
                break;
            }
        }
        return randomIndex;
    }

    /**
     * Метод создания экземпляра класса Toy из входящей строки.
     * Возвращает экземпляр класса Toy.
     * @param toysString - входящая строка
     * */
    public Toy createdToy(String toysString) {
        String[] splitString = toysString.split(" ");
        int id = Integer.parseInt(splitString[1]);
        String name = splitString[4].replace(",", "");
        int frequencyOfLoss = Integer.parseInt(splitString[splitString.length - 1]);
        return new Toy(id, name, frequencyOfLoss);
    }

    /**
     * Метод создания упорядоченного параметризованного списка экземпляров класса Toy.
     * Возвращает упорядоченный параметризованный список
     * @param someList - параметризованный список экземпляров класса Toy
     * */
    private List<Toy> createPriorityQueue(List<Toy> someList) {
        List<Toy> priorityList = new ArrayList<>();
        PriorityQueue<Toy> raffle = new PriorityQueue<>(someList);
        while (!raffle.isEmpty()) {
            priorityList.add(createdToy(String.valueOf(raffle.poll())));
        }
        return priorityList;
    }

    /**
     * Метод вывода на печать рандомизированного экземпляра класса Toy.
     * @param toysList - параметризованный список экземпляров класса Toy
     * */
    public void printRaffleToy (List<Toy> toysList){
        System.out.println(toysList.get(randomToyIndex(toysList)));
    }

    /**
     * Метод проверки длинны массива строк.
     * @param someString - массив строк
     * */
    private boolean isSize(String[] someString) {
        return someString.length == 3;
    }

}
