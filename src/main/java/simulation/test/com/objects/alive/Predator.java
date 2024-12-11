package simulation.test.com.objects.alive;

import java.util.*;

import static simulation.test.com.map.Map.*;
import static simulation.test.com.map.Map.getMaxGrass;

public class Predator extends Creature {
    private final int id;
    private final int attackDamage = 2;
    private int counterOfEaten;

    public Predator(int id) {
        this.id = id;
        health = 5;
        speed = 2;
        food = new Herbivore();
    }

    @Override
    public void makeMove() {
        isPathExist = true;
        stepsLeft = getSpeed();
        while (stepsLeft > 0) {
            getPath();
            if (!isPathExist) {
                break;
            }
            pathToTarget.removeLast();
            if (pathToTarget.getLast().equals(targetNode)) {
                attack();
                continue;
            }
            takeStep();
        }
        System.out.println("Wolf " + id + " ate " + counterOfEaten + " rabbits");
    }

    public void attack() {
        Random rand = new Random();
        if (rand.nextInt(10) > 3) {
            Herbivore herbivore = (Herbivore) getMap().get(targetNode);
            herbivore.changeHealth(this.attackDamage);
            if (herbivore.getHealth() <= 0) {
                getHerbivores().remove(herbivore);
                takeStep();
                counterOfEaten++;
            } else stepsLeft--;
        }
        stepsLeft--;
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

}
