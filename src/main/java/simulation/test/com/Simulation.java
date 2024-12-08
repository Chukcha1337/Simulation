package simulation.test.com;


import simulation.test.com.map.World;
import simulation.test.com.objects.alive.Herbivore;
import simulation.test.com.objects.alive.Predator;

import java.util.*;
import java.util.concurrent.*;

import static simulation.test.com.map.World.*;

public class Simulation {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        World world = createCurrentWorld();
        world.printWorld();

        startSimulation(world);
    }

    public static void nextTurn(World world) {
        System.out.println();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        for (Herbivore herbivore : getHerbivores()) {
            herbivore.makeMove();
        }
        for (Predator predator : getPredators()) {
            predator.makeMove();
        }
        setGrass(getMaxGrass());
        world.printWorld();

    }

    public static void startSimulation(World world) {
        System.out.println("Нажмите любую клавишу для старта/паузы симуляции");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (true) {
            nextTurn(world);
            System.out.println();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(() -> {
                return scanner.nextLine();
            });
            executorService.shutdown();
            try {
                if (future.get(2,TimeUnit.SECONDS) != null) {
                    scanner.nextLine();
                };
            } catch (ExecutionException | TimeoutException | InterruptedException e) {
                future.cancel(true);
            }

            System.out.println("dasd");
            }

        }
    }











