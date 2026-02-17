package org.example;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;


public class Main {
    public static void pingBase(String host){
        try{
            InetAddress destination = InetAddress.getByName(host);
            int countPack = 10;
            int lostPack = 0;
            ArrayList<Long> rtts = new ArrayList<>();
            for(int i=0;i<countPack;i++){
                long start = System.nanoTime();
                boolean reach = destination.isReachable(5000);
                long end = System.nanoTime();
                long rtt = (end-start)/1000000;
                if(reach){
                    rtts.add(rtt);
                    System.out.println(host + " is reachable with RTT: " +  rtt + " ms");
                }else{
                    lostPack+=1;
                    System.out.println("Oh no, can't reach host :("  +  rtt + " ms");
                }
            }
            System.out.println("Statistic:");
            System.out.println("Count of sended packets: " + countPack);
            System.out.println("Received packets: " + (countPack - lostPack));
            System.out.println("Packet loss: " + ((double)lostPack*100/countPack) +"%");
            System.out.println("MAX RTT: " + Collections.max(rtts) +" ms");
            System.out.println("Average RTT: " + (Collections.max(rtts) + Collections.min(rtts))/2 +" ms");
            System.out.println("MIN RTT: " + Collections.min(rtts) +" ms");

        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        String host = "math.nsc.ru";
        pingBase(host);

    }
}