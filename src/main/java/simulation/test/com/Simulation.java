package simulation.test.com;

import simulation.test.com.map.Coordinate;
import simulation.test.com.map.Node;
import simulation.test.com.map.World;
import simulation.test.com.objects.Entity;
import simulation.test.com.objects.alive.Creature;
import simulation.test.com.objects.alive.Herbivore;
import simulation.test.com.objects.alive.Predator;
import simulation.test.com.objects.inanimate.Grass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static simulation.test.com.map.World.createCurrentWorld;
import static simulation.test.com.map.World.getMap;

public class Simulation {
    public static void main(String[] args) {
        World world = createCurrentWorld();
        world.printWorld();

        List<Entity> herbivore = getMap().entrySet().stream().filter(a -> a.getValue().getClass().equals(Herbivore.class)).
                map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println(herbivore);

          List<Entity> predators = getMap().entrySet().stream().filter(a -> a.getValue().getClass().equals(Predator.class)).
                map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println(predators);

        herbivore.get(0).move();
        world.printWorld();





        }


    }

