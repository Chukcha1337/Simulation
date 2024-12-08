package simulation.test.com.map;

import simulation.test.com.objects.*;
import simulation.test.com.objects.alive.*;
import simulation.test.com.objects.inanimate.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class World {
    public static final int MAP_SIDE = 10;
    public static final double MAP_FULLNESS_MULTIPLIER = 0.1;
    public static final int MAX_ROCKS = (int) (MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_TREES = (int) (2 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_GRASS = (int) (4 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_HERBIVORES = (int) (2 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_PREDATORS = (int) (MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    private static final Map<Node, Entity> map = new LinkedHashMap<>();

    public static World createCurrentWorld() {
        World world = new World();
        setEmptyWorld(world);
        set(new Rock(), MAX_ROCKS);
        set(new Tree(), MAX_TREES);
        set(new Grass(), MAX_GRASS);
        setHerbivores(MAX_HERBIVORES);
        setPredators(MAX_PREDATORS);
        return world;
    }

    public static void setEmptyWorld(World world) {
        for (int y = 0; y < MAP_SIDE; y++) {
            for (int x = 0; x < MAP_SIDE; x++) {
                Node node = new Node(y, x);
                Entity entity = new EmptyPlace();
                map.put(node, entity);
            }
        }
    }



    public static void set(Entity entity, int MAX_VALUE) {
        int counter = 0;
        Random random = new Random();
        while (counter < MAX_VALUE) {

            Node node = new Node(random.nextInt(MAP_SIDE), random.nextInt(MAP_SIDE));
            if (map.get(node).getClass() == EmptyPlace.class) {
                map.put(node, entity);
                counter++;
            }
        }
    }

    public static void setHerbivores(int MAX_VALUE) {
        int counter = 0;
        Random random = new Random();
        while (counter < MAX_VALUE) {
            Herbivore herbivore = new Herbivore();
            Node node = new Node(random.nextInt(MAP_SIDE), random.nextInt(MAP_SIDE));
            if (map.get(node).getClass() == EmptyPlace.class) {
                map.put(node, herbivore);
                counter++;
            }
        }
    }
    public static void setPredators(int MAX_VALUE) {
        int counter = 0;
        Random random = new Random();
        while (counter < MAX_VALUE) {
            Predator predator = new Predator();
            Node node = new Node(random.nextInt(MAP_SIDE), random.nextInt(MAP_SIDE));
            if (map.get(node).getClass() == EmptyPlace.class) {
                map.put(node, predator);
                counter++;
            }
        }
    }

    public void printWorld() {
        for (Map.Entry<Node, Entity> entry : map.entrySet()) {
            System.out.print(entry.getValue());
            if (entry.getKey().getX() == MAP_SIDE - 1) {
                System.out.println();
            }
        }
    }

    public static Map<Node, Entity> getMap() {
        return map;
    }
}

