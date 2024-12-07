package simulation.test.com.objects.alive;

public class Predator extends Creature{
private final int attackDamage = 3;
    public Predator() {
        health = 5;
        speed = 5;
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

    @Override
    public void makeMove() {
        //findWay
        //
    }


    public void eat(){};

    public void attack(Herbivore herbivore){};

    @Override
    public void move() {

    }
}
