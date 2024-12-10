package simulation.test.com.objects.alive;
import simulation.test.com.objects.inanimate.Grass;

import static simulation.test.com.map.Map.*;
import static simulation.test.com.map.Map.getMaxGrass;

public class Herbivore extends Creature {

    public Herbivore() {
        health = 3;
        speed = 3;
        food = new Grass();
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

    public void eatGrass() {
        Grass grass = (Grass) getMap().get(targetNode);
        getGrassList().remove(grass);
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
            System.out.println(pathToTarget);
            pathToTarget.removeLast();
            if (pathToTarget.getLast().equals(targetNode)) {
                eatGrass();
                takeStep();
                setGrass(getMaxGrass());
                continue;
            }
            takeStep();
        }
    }

}

