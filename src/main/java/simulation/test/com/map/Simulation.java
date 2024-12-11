package simulation.test.com.map;

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
        Map worldMap = new Map();
        setEmptyWorld(worldMap);
        set(new Rock(), MAX_ROCKS);
        set(new Tree(), MAX_TREES);
        setGrass(MAX_GRASS);
        setHerbivores(MAX_HERBIVORES);
        setPredators(MAX_PREDATORS);
        return worldMap;
    }

    public static void nextTurn(Map worldMap) {
        worldMap.printWorld();
        for (Herbivore herbivore : getHerbivores()) {
            herbivore.makeMove();
        }
        for (Predator predator : getPredators()) {
            predator.makeMove();
        }


    }

    public static void startSimulation(Map worldMap) {
        System.out.println("Приветствую Вас в симуляции! Нажмите Enter для старта симуляции. " +
                "Для выхода, - нажмите Enter дважды");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        int counter = 0;
        while (true) {
            nextTurn(worldMap);
            setHerbivores(getMaxHerbivores());
            System.out.println();
            System.out.println("Счетчик итераций: " + counter++ + "\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
}











