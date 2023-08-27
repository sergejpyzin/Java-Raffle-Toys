import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Toy firstToy = new Toy("First", 0.35);
        Toy secondToy = new Toy("Second", 0.35);
        Toy thirdToy = new Toy();

        thirdToy.setName("Third");
        thirdToy.setFrequencyOfLoss(0.5);

        System.out.println(firstToy);
        System.out.println(secondToy);
        System.out.println(thirdToy);

        List<Toy> toys = new ArrayList<>();
        toys.add(firstToy);
        toys.add(secondToy);
        toys.add(thirdToy);
        toys.add(new Toy("Four", 0.22));
        System.out.println("==================================");
        System.out.println(toys);
        Collections.sort(toys);
        System.out.println("-----------------------------------");
        System.out.println(toys);

    }
}