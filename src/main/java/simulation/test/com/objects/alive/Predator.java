package simulation.test.com.objects.alive;

import java.util.*;

import static simulation.test.com.map.Map.getHerbivores;
import static simulation.test.com.map.Map.getMap;

public class Predator extends Creature {
    private final int attackDamage = 1;

    public Predator() {
        health = 5;
        speed = 1;
        food = new Herbivore();
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
        return "\uD83D\uDC3A ";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void attack() {
        Random rand = new Random();
        if (rand.nextInt(10) > 2) {
            Herbivore herbivore = (Herbivore) getMap().get(targetNode);
            herbivore.changeHealth(-this.attackDamage);
            if (herbivore.getHealth() <= 0) {
                getHerbivores().remove(herbivore);
                takeStep();
            } else stepsLeft--;
        }
    }

    @Override
    public void makeMove() {
        isPathExist = true;
        stepsLeft = getSpeed();
        while (stepsLeft > 0 && isPathExist) {
            getPath();
            pathToTarget.removeLast();
            if (pathToTarget.getLast().equals(targetNode)) {
                attack();
                continue;
            }
            takeStep();
        }
    }

}
