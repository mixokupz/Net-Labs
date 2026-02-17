package org.example.router;

import org.example.route.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {

    static final int MNOGA = 16;
    private Map<String, Route> routingTable = new HashMap<>();
    private List<Router> neighbors = new ArrayList<>();
    private final String name;

    public Router(String name) {
        this.name = name;
        routingTable.put(name, new Route(0, this));
    }

    public void addNeighbor(Router neighbor, int cost) {
        neighbors.add(neighbor);
        if(cost<MNOGA){
            routingTable.put(neighbor.name, new Route(cost, neighbor));
        }
    }

    void receiveRoutingTable(Router neighbor, Map<String, Route> receivedTable) {
        for (var entry : receivedTable.entrySet()) {
            String destination = entry.getKey();
            Route receivedRoute = entry.getValue();

            if (destination.equals(this.name)){
                continue;
            }
            // разеделние горизонта
            if (receivedRoute.getNextHop() == this) {
                continue;
            }

            int newCost = Math.min(MNOGA, receivedRoute.getCost() + getCostToNeighbor(neighbor));

            Route currentRoute = routingTable.get(destination);


            if (currentRoute == null || newCost < currentRoute.getCost() || (currentRoute.getNextHop() == neighbor && newCost != currentRoute.getCost())) {
                if(newCost<MNOGA){
                    routingTable.put(destination, new Route(newCost, neighbor));
                }
            }
        }
    }

    private int getCostToNeighbor(Router neighbor) {
        Route route = routingTable.get(neighbor.name);
        if (route!=null){
            return route.getCost();
        }
        return MNOGA;
    }

    public void sendRoutingTable() {
        for (Router neighbor : neighbors) {
            Map<String, Route> advertisedTable = new HashMap<>();

            for (var entry : routingTable.entrySet()) {
                String dest = entry.getKey();
                Route route = entry.getValue();

                //не передаем инфу о них же самих
                if (route.getNextHop() != neighbor) {
                    advertisedTable.put(dest, new Route(route.getCost(), route.getNextHop()));
                }
            }
            neighbor.receiveRoutingTable(this, advertisedTable);
        }
    }

    public void printRoutingTable() {
        System.out.println("Routing Table for " + name + ":");
        for (var entry : routingTable.entrySet()) {
            String dest = entry.getKey();
            Route route = entry.getValue();
            System.out.printf("Destination: %-10s Cost: %-2d NextHop: %s%n", dest, route.getCost(), route.getNextHop().name);
        }
        System.out.println();
    }
}