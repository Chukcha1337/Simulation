package simulation.test.com;

import simulation.test.com.map.Map;
import simulation.test.com.objects.alive.Herbivore;
import simulation.test.com.objects.alive.Predator;
import simulation.test.com.objects.inanimate.Rock;
import simulation.test.com.objects.inanimate.Tree;
import java.util.*;
import java.util.concurrent.*;

import static simulation.test.com.map.Map.*;

public class Simulation {
    public static Scanner scanner = new Scanner(System.in);
    public static int counterOfTurns;
    public static Map createCurrentWorld() {
        Map worldMap = new Map();
        setEmptyWorld(worldMap);
        set(new Rock(), MAX_ROCKS);
        set(new Tree(), MAX_TREES);
        setGrass(MAX_GRASS);
        setHerbivores(MAX_HERBIVORES);
        setPredators(MAX_PREDATORS);
        return worldMap;
    }

    public static void main(String[] args) {
        Map worldMap = createCurrentWorld();
        startSimulation(worldMap);
    }

    public static void nextTurn(Map worldMap) {
        for (Herbivore herbivore : getHerbivores()) {
            herbivore.makeMove();
        }
        for (Predator predator : getPredators()) {
            predator.makeMove();
        }
        worldMap.printWorld();

    }

    public static void startSimulation(Map worldMap) {

        int counter = 0;
        while (true) {
            nextTurn(worldMap);
            System.out.println(counter++);
            System.out.println();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(() -> scanner.nextLine());
            executorService.shutdown();
            try {
                if (future.get(2, TimeUnit.SECONDS) != null) {
                    break;
                }
            } catch (ExecutionException | TimeoutException | InterruptedException e) {
                            }

        }
    }
    class MyThread extends Thread {


    }
}











