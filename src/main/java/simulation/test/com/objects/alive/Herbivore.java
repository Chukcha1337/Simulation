package simulation.test.com.objects.alive;
import simulation.test.com.map.Node;
import simulation.test.com.map.World;
import simulation.test.com.objects.Entity;
import simulation.test.com.objects.inanimate.EmptyPlace;
import simulation.test.com.objects.inanimate.Grass;
import java.util.*;
import java.util.stream.Collectors;

import static simulation.test.com.map.World.getGrass;
import static simulation.test.com.map.World.getMap;

public class Herbivore extends Creature {

    public Herbivore() {
        health = 3;
        speed = 1;
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
        // текущая координата существа
        Node startNode = getMap().entrySet().stream().filter(a -> a.getValue().equals(this)).
                map(Map.Entry::getKey).findFirst().orElse(new Node(0, 0));
//        System.out.println(startNode);
        // сет координат пищи
        Set<Node> food = getMap().entrySet().stream().filter(a -> a.getValue().getClass().equals(Grass.class)).
                map(Map.Entry::getKey).collect(Collectors.toSet());
//        System.out.println(food);
        // сет достижимых клеток
        Set<Node> reachable = new LinkedHashSet<>();
        // добавляем текущий узел
        reachable.add(startNode);
//        System.out.println(reachable);

        Node target = new Node(0, 0);
        double difference = World.MAP_SIDE;
        // присваиваем таргету самую ближнюю пищу
        for (Node node : food) {

            double curentDiff = Math.sqrt((Math.pow(node.getX() - startNode.getX(), 2) + Math.pow(node.getY() - startNode.getY(), 2)));
            if (curentDiff < difference) {
                difference = curentDiff;
                target = node;
            }
        }
//        System.out.println(target);
        Set<Node> explored = new LinkedHashSet<>();
        List<Node> path = new LinkedList<>();
        // currentNode - текущее место зайца, target - цель зайца
        while (!reachable.isEmpty()) {

            double maxCost = 100.0;
            Node current = new Node(0, 0, 100);
            for (Node node : reachable) {
                if (node.equals(target)) {
                    current = node;
                    break;
                }
                if (node.getCost() < maxCost) {
                    maxCost = node.getCost();
                    current = node;
                }
                ;
            }
//            System.out.println("Current node" + current);

            if (current.equals(target)) {
                // buildPath
                // break
                try {
                    while (!current.equals(null)) {
                        path.add(current);
                        current = current.getPrevious();
                    }
                } catch (NullPointerException e) {
                    break;
                }

            }
            reachable.remove(current);
            explored.add(current);

            Set<Node> newReachable = new LinkedHashSet<>();

            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (Math.abs(x) + Math.abs(y) == 1) {
                        Node node = new Node(current.getY() + y, current.getX() + x);
                        if (node.getX() < 0 || node.getX() == World.MAP_SIDE || node.getY() < 0 || node.getY() == World.MAP_SIDE) {
                            continue;
                        }
                        if (node.equals(target)) {
                            newReachable.add(node);
                        }
                        if ((getMap().get(node).getClass().equals(EmptyPlace.class) && !explored.contains(node))) {
                            node.setCost(Math.sqrt((Math.pow(node.getX() - target.getX(), 2) + Math.pow(node.getY() - target.getY(), 2))));
                            newReachable.add(node);
                        }
                    }
                }
            }
            for (Node node : newReachable) {
                if (!reachable.contains(node)) {
                    node.setPrevious(current);
                    reachable.add(node);
                }
            }
//            System.out.println(reachable);
        }
//        System.out.println(path);

        int stepsLeft = this.getSpeed();
        while (stepsLeft > 0) {
            path.removeLast();
            if (path.getLast().equals(target)) {
//            eat();
                Grass grass = (Grass) getMap().get(target);
                getGrass().remove(grass);

                getMap().put(path.getLast(), this);
                getMap().put(path.getLast().getPrevious(), new EmptyPlace());
                break;
            }
            getMap().put(path.getLast(), this);
            getMap().put(path.getLast().getPrevious(), new EmptyPlace());
            stepsLeft--;
        }

    }

}

