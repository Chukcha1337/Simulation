package simulation.test.com.map;

import simulation.test.com.objects.*;
import simulation.test.com.objects.alive.*;
import simulation.test.com.objects.inanimate.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class World {
    public static final int MAP_SIDE = 10;
    public static final double MAP_FULLNESS_MULTIPLIER = 0.3;
    public static final int MAX_ROCKS = (int) (MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_TREES = (int) (2 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_GRASS = (int) (4 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_HERBIVORES = (int) (2 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_PREDATORS = (int) (MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    private static final Map<Coordinate, Entity> map = new LinkedHashMap<>();

    public static World createCurrentWorld() {
        World world = new World();
        setEmptyWorld(world);
        set(new Rock(), MAX_ROCKS);
        set(new Tree(), MAX_TREES);
        set(new Grass(), MAX_GRASS);
        setHerbivores(MAX_HERBIVORES);
        set(new Predator(), MAX_PREDATORS);
        return world;
    }

    public static void setEmptyWorld(World world) {
        for (int y = 0; y < MAP_SIDE; y++) {
            for (int x = 0; x < MAP_SIDE; x++) {
                Coordinate coordinate = new Coordinate(y, x);
                Entity entity = new EmptyPlace();
                map.put(coordinate, entity);
            }
        }
    }

    public static void set(Entity entity, int MAX_VALUE) {
        int counter = 0;
        Random random = new Random();
        while (counter < MAX_VALUE) {

            Coordinate coordinate = new Coordinate(random.nextInt(MAP_SIDE), random.nextInt(MAP_SIDE));
            if (map.get(coordinate).getClass() == EmptyPlace.class) {
                map.put(coordinate, entity);
                counter++;
            }
        }
    }

    public static void setHerbivores(int MAX_VALUE) {
        int counter = 0;
        Random random = new Random();
        while (counter < MAX_VALUE) {
            Herbivore herbivore = new Herbivore();
            Coordinate coordinate = new Coordinate(random.nextInt(MAP_SIDE), random.nextInt(MAP_SIDE));
            if (map.get(coordinate).getClass() == EmptyPlace.class) {
                map.put(coordinate, herbivore);
                counter++;
            }
        }
    }

    public void printWorld() {
        for (Map.Entry<Coordinate, Entity> entry : map.entrySet()) {
            System.out.print(entry.getValue());
            if (entry.getKey().getX() == MAP_SIDE - 1) {
                System.out.println();
            }
        }
    }

    public static Map<Coordinate, Entity> getMap() {
        return map;
    }
}
