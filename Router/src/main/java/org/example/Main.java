package org.example;

import org.example.router.Router;

import java.util.*;

public class Main {
    static final int UPDATE_TIME = 5000;

    public static void main(String[] args) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAa");
        Router A = new Router("A");
        Router B = new Router("B");
        Router C = new Router("C");
        Router D = new Router("D");
        A.addNeighbor(B, 1);
        B.addNeighbor(A, 1);

        B.addNeighbor(C, 3);
        C.addNeighbor(B, 3);
        A.addNeighbor(C,2);
        C.addNeighbor(A,2);
        D.addNeighbor(A,6);
        A.addNeighbor(D,6);
        D.addNeighbor(B,1);
        B.addNeighbor(D,1);

        List<Router> network = Arrays.asList(A, B, C,D);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Router router : network) {
                    router.printRoutingTable();
                }
                for (Router router : network) {
                    router.sendRoutingTable();
                }
            }
        }, 0, UPDATE_TIME);
    }
}
