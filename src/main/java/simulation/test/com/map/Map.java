package simulation.test.com.map;

import simulation.test.com.objects.*;
import simulation.test.com.objects.alive.*;
import simulation.test.com.objects.inanimate.*;

import java.util.*;

public class Map {
    public static final int MAP_SIDE = 15;
    public static final double MAP_MAX_DISTANCE = MAP_SIDE * Math.sqrt(2);
    public static final double MAP_FULLNESS_MULTIPLIER = 0.16;
    public static final int MAX_ROCKS = (int) (3 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_TREES = (int) (3 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_GRASS = (int) (4 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_HERBIVORES = (int) (4 * MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    public static final int MAX_PREDATORS = (int) (MAP_SIDE * MAP_FULLNESS_MULTIPLIER);
    private static final java.util.Map<Node, Entity> map = new LinkedHashMap<>();
    private static final List<Herbivore> herbivoresList = new LinkedList<>();
    private static final List<Predator> predatorsList = new LinkedList<>();
    private static final List<Grass> grassList = new LinkedList<>();

    public static int getMaxGrass() {
        return MAX_GRASS;
    }

    public static int getMaxHerbivores() {
        return MAX_HERBIVORES;
    }

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

    public static void setEmptyWorld(Map map) {
        for (int y = 0; y < MAP_SIDE; y++) {
            for (int x = 0; x < MAP_SIDE; x++) {
                Node node = new Node(y, x);
                Entity entity = new EmptyPlace();
                Map.map.put(node, entity);
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
        int counter = getHerbivores().size();
        Random random = new Random();
        while (counter < MAX_VALUE) {
            Herbivore herbivore = new Herbivore();
            Node node = new Node(random.nextInt(MAP_SIDE), random.nextInt(MAP_SIDE));
            if (map.get(node).getClass() == EmptyPlace.class) {
                map.put(node, herbivore);
                herbivoresList.add(herbivore);
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
                predatorsList.add(predator);
                map.put(node, predator);
                counter++;
            }
        }
    }

    public static void setGrass(int MAX_VALUE) {
        int counter = getGrassList().size();
        Random random = new Random();
        while (counter < MAX_VALUE) {
            Grass grass = new Grass();
            Node node = new Node(random.nextInt(MAP_SIDE), random.nextInt(MAP_SIDE));
            if (map.get(node).getClass() == EmptyPlace.class) {
                grassList.add(grass);
                map.put(node, grass);
                counter++;
            }
        }
    }

    public void printWorld() {
        for (java.util.Map.Entry<Node, Entity> entry : map.entrySet()) {
            System.out.print(entry.getValue());
            if (entry.getKey().getX() == MAP_SIDE - 1) {
                System.out.println();
            }
        }
    }

    public static java.util.Map<Node, Entity> getMap() {
        return map;
    }

    public static List<Herbivore> getHerbivores() {
        return herbivoresList;
    }

    public static List<Predator> getPredators() {
        return predatorsList;
    }

    public static List<Grass> getGrassList() {
        return grassList;
    }



}

