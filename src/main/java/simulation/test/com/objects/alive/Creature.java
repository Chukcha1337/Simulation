package simulation.test.com.objects.alive;

import simulation.test.com.map.Node;
import simulation.test.com.map.Map;
import simulation.test.com.objects.Entity;
import simulation.test.com.objects.inanimate.EmptyPlace;

import java.util.*;
import java.util.stream.Collectors;

import static simulation.test.com.map.Map.getMap;

public abstract class Creature extends Entity {
    protected int speed;
    protected int health;
    protected int stepsLeft;
    protected Node currentLocationNode;
    protected Node targetNode;
    protected boolean isPathExist;
    protected Set<Node> nonReachableTargets = new HashSet<>();
    protected Node nodeBeingChecked;
    protected Node potentialNodeToCheck;
    protected Entity food;
    protected Set<Node> foodLocations = new HashSet<>();
    protected Set<Node> reachableLocations = new LinkedHashSet<>();
    protected Set<Node> exploredLocations = new LinkedHashSet<>();
    protected List<Node> pathToTarget = new LinkedList<>();
    protected Set<Node> reachableFromHere = new LinkedHashSet<>();


    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int value) {
        this.health -= value;
    }

    public abstract void makeMove();

    public void getCurrentNode() {
        currentLocationNode = getMap().entrySet().stream().filter(a -> a.getValue().equals(this)).
                map(java.util.Map.Entry::getKey).toList().getFirst();
    }

    public void detectAllAims() {
        foodLocations = getMap().entrySet().stream().filter(a -> a.getValue().getClass().equals(food.getClass())).
                map(java.util.Map.Entry::getKey).collect(Collectors.toSet());
        System.out.println("Это список еды для " + this.getClass() + " " + foodLocations);
        for (Node n : foodLocations) {
            if (getMap().get(n).getClass().equals(food.getClass())) {
                System.out.print(" true ");
            } else {
                System.out.print(" false ");
            }
        }
    }

    public void resetPreviousPath() {
        reachableLocations.clear();
        foodLocations.clear();
        exploredLocations.clear();
        pathToTarget.clear();
    }

    public double getShortestPathDistance(Node firstNode, Node secondNode) {
        return Math.sqrt((Math.pow(firstNode.getX() - secondNode.getX(), 2) + Math.pow(firstNode.getY() - secondNode.getY(), 2)));
    }

    public void getTargetNode() {
        double ShortestDistance = Map.MAP_MAX_DISTANCE;
        System.out.println("Food locations here " + foodLocations);
        for (Node node : foodLocations) {
            double distanceToCurrentTarget = getShortestPathDistance(node, currentLocationNode);
            if (distanceToCurrentTarget < ShortestDistance && !nonReachableTargets.contains(node)) {
                ShortestDistance = distanceToCurrentTarget;
                targetNode = node;
            }
        }
        if (getMap().get(targetNode).getClass().equals(food.getClass())) {
            System.out.println("Target is true " + targetNode);
        } else {
            System.out.println("Target is false " + targetNode);
        }
    }

    public void getNodeBeingChecked() {
        double maxCost = Map.MAP_MAX_DISTANCE;
        for (Node node : reachableLocations) {
            if (node.equals(targetNode)) {
                nodeBeingChecked = node;
                break;
            }
            if (node.getCost() < maxCost) {
                maxCost = node.getCost();
                nodeBeingChecked = node;
            }
        }
    }

    public boolean isNodeReachable(int x, int y) {
        return Math.abs(x) + Math.abs(y) == 1;
    }

    public void setPotentialNode(int x, int y) {
        potentialNodeToCheck = new Node(nodeBeingChecked.getY() + y, nodeBeingChecked.getX() + x);
    }

    public boolean isNodeNotExist() {
        return potentialNodeToCheck.getX() < 0 ||
                potentialNodeToCheck.getX() == Map.MAP_SIDE ||
                potentialNodeToCheck.getY() < 0 ||
                potentialNodeToCheck.getY() == Map.MAP_SIDE;
    }

    public boolean isNodeNewAndFree() {
        return (getMap().get(potentialNodeToCheck).getClass().equals(EmptyPlace.class) &&
                !exploredLocations.contains(potentialNodeToCheck));
    }

    public void setNewReachableLocation() {
        potentialNodeToCheck.setCost(getShortestPathDistance(potentialNodeToCheck, targetNode));
        reachableFromHere.add(potentialNodeToCheck);
    }

    public void setPathToTarget() {
        try {
            while (true) {
                pathToTarget.add(nodeBeingChecked);
                if (nodeBeingChecked.getPrevious() == null) {
                    break;
                }
                nodeBeingChecked = nodeBeingChecked.getPrevious();
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }
    }

    public void setPreviousLocations() {
        for (Node node : reachableFromHere) {
            if (!reachableLocations.contains(node)) {
                node.setPrevious(nodeBeingChecked);
                reachableLocations.add(node);
            }
        }
    }

    public void prepareCollectionsToAddition() {
        reachableLocations.remove(nodeBeingChecked);
        exploredLocations.add(nodeBeingChecked);
        reachableFromHere.clear();
    }

    public void createPathToTarget() {
        while (!reachableLocations.isEmpty()) {
            getNodeBeingChecked();
            if (nodeBeingChecked.equals(targetNode)) {
                setPathToTarget();
                break;
            }
            prepareCollectionsToAddition();
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (isNodeReachable(x, y)) {
                        setPotentialNode(x, y);
                        if (isNodeNotExist()) {
                            continue;
                        }
                        if (potentialNodeToCheck.equals(targetNode)) {
                            reachableFromHere.add(potentialNodeToCheck);
                        }
                        if (isNodeNewAndFree()) {
                            setNewReachableLocation();
                        }
                    }
                }
            }
            setPreviousLocations();
        }
    }

    public void getPath() {
        nonReachableTargets.clear();
        while (true) {
            resetPreviousPath();
            getCurrentNode();
            detectAllAims();
            reachableLocations.add(currentLocationNode);
            getTargetNode();
            if (targetNode != null) {
                createPathToTarget();
            } else {
                System.out.println("Target node is null");
            }
            if (pathToTarget.isEmpty()) {
                nonReachableTargets.add(targetNode);
                System.out.println(nonReachableTargets);
                if (nonReachableTargets.size() == foodLocations.size()) {
                    isPathExist = false;
                    break;
                }
            } else {
                break;
            }
        }
    }

    public void takeStep() {
        getMap().put(pathToTarget.getLast(), this);
        getMap().put(pathToTarget.getLast().getPrevious(), new EmptyPlace());
        stepsLeft--;
    }
}





