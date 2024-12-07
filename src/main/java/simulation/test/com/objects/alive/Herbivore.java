package simulation.test.com.objects.alive;

public class Herbivore extends Creature {

    public Herbivore() {
        health = 3;
        speed = 3;
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

    }
}
