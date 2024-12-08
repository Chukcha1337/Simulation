package simulation.test.com.objects.alive;

import simulation.test.com.objects.Entity;

public abstract class Creature extends Entity   {
    protected int speed;
    protected int health;

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int value) {
        this.health += value;
    }

    public abstract void makeMove();

}
