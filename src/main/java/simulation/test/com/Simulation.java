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

    public static Map createCurrentWorld() {
        Map map = new Map();
        setEmptyWorld(map);
        set(new Rock(), MAX_ROCKS);
        set(new Tree(), MAX_TREES);
        setGrass(MAX_GRASS);
        setHerbivores(MAX_HERBIVORES);
        setPredators(MAX_PREDATORS);
        return map;
    }

    public static void main(String[] args) {
        Map map = createCurrentWorld();
        map.printWorld();

        startSimulation(map);
    }

    public static void nextTurn(Map map) {
        for (Herbivore herbivore : getHerbivores()) {
            herbivore.makeMove();
        }
        for (Predator predator : getPredators()) {
            predator.makeMove();
        }
        setGrass(getMaxGrass());
        map.printWorld();

    }

    public static void startSimulation(Map map) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int counter = 0;
        while (true) {
            nextTurn(map);
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
                future.cancel(true);
            }
        }
    }
}











