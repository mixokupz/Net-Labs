package org.example;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

public class MulticastCopyFinder {

    private static final int PORT = 8888;
    private static final int HEARTBEAT = 1000;
    private static final int TIMEOUT = 5000;

    private final String nodeUUID = UUID.randomUUID().toString();
    private final Map<String, NodeInfo> nodeMap = new ConcurrentHashMap<>();
    private final InetAddress groupAddress;
    private final MulticastSocket multiSocket;

    public MulticastCopyFinder(String group) throws IOException {
        this.groupAddress = InetAddress.getByName(group);
        this.multiSocket = new MulticastSocket(PORT);

        if (groupAddress instanceof Inet4Address || groupAddress instanceof Inet6Address) {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            multiSocket.joinGroup(new InetSocketAddress(groupAddress, PORT), networkInterface);
            //multiSocket.joinGroup(groupAddress);
        } else {
            throw new IllegalArgumentException("Error: Use Ipv4 or Ipv6");
        }

        System.out.println("This is node: " + this.nodeUUID);

        Thread receiver = new Thread(this::receiving);
        Thread sender = new Thread(this::sending);
        Thread cleaner = new Thread(this::cleaning);

        //не надо
        receiver.setDaemon(true);
        sender.setDaemon(true);
        cleaner.setDaemon(true);

        receiver.start();
        sender.start();
        cleaner.start();
    }

    private void sending() {
        while (true) {
            try {
                byte[] msg = this.nodeUUID.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packet = new DatagramPacket(msg, msg.length, groupAddress, PORT);
                multiSocket.send(packet);
                Thread.sleep(HEARTBEAT);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void receiving() {
        byte[] buf = new byte[1024];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                multiSocket.receive(packet);
                String receivedId = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                String ip = packet.getAddress().getHostAddress();

                if (!receivedId.equals(nodeUUID)) {
                    boolean isNew = !nodeMap.containsKey(receivedId);
                    nodeMap.put(receivedId, new NodeInfo(receivedId, ip, System.currentTimeMillis()));
                    if (isNew){
                        printNodes();  
                    } 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cleaning() {
        while (true) {
            try {
                Thread.sleep(1000);
                boolean isDead = false;
                long now = System.currentTimeMillis();
                List<String> removeList = new ArrayList<>();
                for (Map.Entry<String, NodeInfo> entry : nodeMap.entrySet()) {
                    if (now - entry.getValue().lastTime > TIMEOUT) {
                        removeList.add(entry.getKey());
                        isDead = true;
                    }
                }
                for (String id : removeList) {
                    nodeMap.remove(id);
                }
                if (isDead){
                    printNodes();
                } 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printNodes() {

        System.out.println("Alive copies:");
        for (NodeInfo info : nodeMap.values()) {
            System.out.println(info.id + " from " + info.ip);
        }
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
    }

    private static class NodeInfo {
        String id;
        String ip;
        long lastTime;

        NodeInfo(String id, String ip, long lastTime) {
            this.id = id;
            this.ip = ip;
            this.lastTime = lastTime;
        }
    }
}
