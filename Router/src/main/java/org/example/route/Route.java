package org.example.route;

import org.example.router.Router;

public class Route {
    private int cost;
    Router nextHop;

    public Route(int cost, Router nextHop) {

        if(cost>=0){
            this.cost = cost;
        }else{
            throw new IllegalArgumentException("Wrong argument, must be > 0");
        }
        this.nextHop = nextHop;
    }
    public int getCost(){
        return cost;
    }
    public Router getNextHop(){
        return nextHop;
    }
}