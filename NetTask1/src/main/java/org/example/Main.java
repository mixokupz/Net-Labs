package org.example;

import java.io.IOException;
import java.util.concurrent.*;
public class Main {
    public static void main(String[] args) throws IOException{
        if (args.length != 1) {
            System.out.println("Use: java <program name> <ip of multicast group>");
            System.exit(1);
        }

        try{
            new MulticastCopyFinder(args[0]);
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
