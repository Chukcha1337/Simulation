package simulation.test.com.objects.alive;

import simulation.test.com.map.Coordinate;
import simulation.test.com.map.World;
import simulation.test.com.objects.inanimate.EmptyPlace;
import simulation.test.com.objects.inanimate.Grass;

import java.util.*;
import java.util.stream.Collectors;

import static simulation.test.com.map.World.getMap;

public class Herbivore extends Creature {

    public Herbivore() {
        health = 3;
        speed = 3;
    }

    @Override
    public int getHealth() {
        return super.getHealth();
    }

    @Override
    public int getSpeed() {
        return super.getSpeed();
    }

    @Override
    public void changeHealth(int value) {
        super.changeHealth(value);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC07 ";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void makeMove() {
        while (getSpeed() != 0) {
            // findWay
            // if (map.get(coordinates).getClass() == Grass.class)
            // eat
            // else move
        }
    }
    @Override
    public void print() {
        System.out.println("Herbivore");
    }

    @Override
    public void move() {
        Coordinate currentCoordinate = getMap().entrySet().stream().filter(a -> a.getValue().equals(this)).
                map(Map.Entry::getKey).findFirst().orElse(null);
        Set<Coordinate> food = getMap().entrySet().stream().filter(a -> a.getValue().getClass().equals(Grass.class)).
                map(Map.Entry::getKey).collect(Collectors.toSet());
//        System.out.println("rabbit" + currentCoordinate);
        Coordinate target = null;
        int difference = World.MAP_SIDE;
        for (Coordinate c : food) {
            int curentDiff = Math.abs(c.getX() - currentCoordinate.getX()) + Math.abs(c.getY() - currentCoordinate.getY());
            if (curentDiff < difference) {
                difference = curentDiff;
                target = c;
            }
        }
//        System.out.println("food" + target);
        getMap().put(target, this);
        getMap().put(currentCoordinate, new EmptyPlace());
    }

    public void findPath () {
        Coordinate currentCoordinate = getMap().entrySet().stream().filter(a -> a.getValue().equals(this)).
                map(Map.Entry::getKey).findFirst().orElse(null);
        Set<Coordinate> food = getMap().entrySet().stream().filter(a -> a.getValue().getClass().equals(Grass.class)).
                map(Map.Entry::getKey).collect(Collectors.toSet());
        Coordinate me = new Coordinate(5,5);
        Coordinate target = null;
        int difference = World.MAP_SIDE;
        for (Coordinate c : food) {
            int curentDiff = Math.abs(c.getX() - me.getX()) + Math.abs(c.getY() - me.getY());
            if (curentDiff < difference) {
                difference = curentDiff;
//                System.out.println(c);
                target = c;
            }
        }
//        System.out.println(target);




        Set<Coordinate> rightPath = new LinkedHashSet<>();
        List<Coordinate> adas = new ArrayList<>();

    }
}
