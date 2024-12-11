package simulation.test.com;

import simulation.test.com.map.Map;
import static simulation.test.com.map.Simulation.createCurrentWorld;
import static simulation.test.com.map.Simulation.startSimulation;

public class Launcer {
    public static void main(String[] args) {
        Map worldMap = createCurrentWorld();
        startSimulation(worldMap);
    }
}
