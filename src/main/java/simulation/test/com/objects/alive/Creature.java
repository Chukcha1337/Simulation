package simulation.test.com.objects.alive;

import simulation.test.com.map.Node;
import simulation.test.com.map.World;
import simulation.test.com.objects.Entity;
import simulation.test.com.objects.inanimate.EmptyPlace;

import java.util.*;
import java.util.stream.Collectors;

import static simulation.test.com.map.World.getHerbivores;
import static simulation.test.com.map.World.getMap;

public abstract class Creature extends Entity {
    protected int speed;
    protected int health;
    protected Node currentNode;
    protected Node targetNode;
    protected Node nodeBeingChecked;
    protected Entity food;
    protected Set<Node> foodLocations;
    protected Set<Node> reachableLocations = new LinkedHashSet<>();
    protected Set<Node> exploredLocations = new LinkedHashSet<>();
    protected List<Node> pathToTarget = new LinkedList<>();


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

    public void getCurrentNode() {
        currentNode = getMap().entrySet().stream().filter(a -> a.getValue().equals(this)).
                map(Map.Entry::getKey).toList().getFirst();
    }

    public void detectAllAims(Object ClassOfTarget) {
        foodLocations = getMap().entrySet().stream().filter(a -> a.getValue().getClass().equals(food.getClass())).
                map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public void resetPreviousPath() {

    }

    public double getShortestPathDistance(Node node) {
        return Math.sqrt((Math.pow(node.getX() - currentNode.getX(), 2) + Math.pow(node.getY() - currentNode.getY(), 2)));
    }

    public void getTargetNode() {
        double ShortestDistance = World.MAP_SIDE;
        // присваиваем таргету самую ближнюю пищу
        for (Node node : foodLocations) {
            double distanceToCurrentTarget = getShortestPathDistance(node);
            if (distanceToCurrentTarget < ShortestDistance) {
                ShortestDistance = distanceToCurrentTarget;
                targetNode = node;
            }
        }
    }

    public void getNodeBeingChecked() {
        double maxCost = World.MAP_MAX_DISTANCE;
        for (Node node : reachableLocations) {
            if (node.equals(targetNode)) {
                nodeBeingChecked = node;
                break;
            }
            if (node.getCost() < maxCost) {
                maxCost = node.getCost();
                nodeBeingChecked = node;
            }
            ;
        }
    }

    public boolean isNodeEqualsTarget() {
        if (nodeBeingChecked.equals(targetNode)) {
            try {
                while (nodeBeingChecked != null) {
                    pathToTarget.add(nodeBeingChecked);
                    nodeBeingChecked = nodeBeingChecked.getPrevious();
                }
            } catch (NullPointerException e) {
                return true;
            }
        }
        return false;
    }

    public

    public void createPathToTarget() {
        while (!reachableLocations.isEmpty()) {
            getNodeBeingChecked();
            if (isNodeEqualsTarget()) {
                break;
            }

            reachableLocations.remove(nodeBeingChecked);
            exploredLocations.add(nodeBeingChecked);

            Set<Node> newReachable = new LinkedHashSet<>();

            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (Math.abs(x) + Math.abs(y) == 1) {
                        Node node = new Node(nodeBeingChecked.getY() + y, nodeBeingChecked.getX() + x);
                        if (node.getX() < 0 || node.getX() == World.MAP_SIDE || node.getY() < 0 || node.getY() == World.MAP_SIDE) {
                            continue;
                        }
                        if (node.equals(targetNode)) {
                            newReachable.add(node);
                        }
                        if ((getMap().get(node).getClass().equals(EmptyPlace.class) && !exploredLocations.contains(node) && )) {
                            node.setCost(Math.sqrt((Math.pow(node.getX() - targetNode.getX(), 2) + Math.pow(node.getY() - targetNode.getY(), 2))));
                            newReachable.add(node);
                        }
                    }
                }
            }
            for (Node node : newReachable) {
                if (!reachableLocations.contains(node)) {
                    node.setPrevious(nodeBeingChecked);
                    reachableLocations.add(node);
                }
            }
//            System.out.println(reachable);
        }
//        System.out.println(path);

    }


    public void getPath() {
        // текущая координата существа
        getCurrentNode();
//        System.out.println(startNode);
        // сет координат пищи
        detectAllAims(food);
//        System.out.println(food);
        // сет достижимых клеток
        // добавляем текущий узел
        reachableLocations.add(currentNode);
//        System.out.println(reachable);
//
// присваиваем таргету самую ближнюю пищу
        getTargetNode();
        if (targetNode == null) {

        }

//        System.out.println(target);

        // currentNode - текущее место зайца, target - цель зайца
        while (!reachableLocations.isEmpty()) {

            getNodeBeingChecked();

//            System.out.println("Current node" + current);

            if (nodeBeingChecked.equals(targetNode)) {
                try {
                    while (nodeBeingChecked != null) {
                        pathToTarget.add(nodeBeingChecked);
                        nodeBeingChecked = nodeBeingChecked.getPrevious();
                    }
                } catch (NullPointerException e) {
                    break;
                }

            }
            reachableLocations.remove(nodeBeingChecked);
            exploredLocations.add(nodeBeingChecked);

            Set<Node> newReachable = new LinkedHashSet<>();

            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (Math.abs(x) + Math.abs(y) == 1) {
                        Node node = new Node(nodeBeingChecked.getY() + y, nodeBeingChecked.getX() + x);
                        if (node.getX() < 0 || node.getX() == World.MAP_SIDE || node.getY() < 0 || node.getY() == World.MAP_SIDE) {
                            continue;
                        }
                        if (node.equals(targetNode)) {
                            newReachable.add(node);
                        }
                        if ((getMap().get(node).getClass().equals(EmptyPlace.class) && !exploredLocations.contains(node))) {
                            node.setCost(Math.sqrt((Math.pow(node.getX() - targetNode.getX(), 2) + Math.pow(node.getY() - targetNode.getY(), 2))));
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
//            attack();
                Random rand = new Random();
                if (rand.nextInt(10) > 3) {
                    Herbivore herbivore = (Herbivore) getMap().get(target);

                    if (herbivore.getHealth() <= 0) {
                        getHerbivores().remove(herbivore);
                        getMap().put(path.getLast(), this);
                        getMap().put(path.getLast().getPrevious(), new EmptyPlace());
                    }
                } else stepsLeft--;

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


