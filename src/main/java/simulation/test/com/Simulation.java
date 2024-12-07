package simulation.test.com;

import simulation.test.com.map.World;

import static simulation.test.com.map.World.createCurrentWorld;

public class Simulation {
    public static void main(String[] args) {
        World world = createCurrentWorld();
        world.printWorld();


    }
}
