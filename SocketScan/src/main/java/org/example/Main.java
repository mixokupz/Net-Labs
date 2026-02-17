package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    private static final Map<Integer, String> portServiceMap = new HashMap<>();

    public static void loadServices() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Windows\\System32\\drivers\\etc\\services"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+"); //http 80
                if (parts.length < 2) continue;

                String[] portAndProtocol = parts[1].split("/");
                if (portAndProtocol.length != 2) continue;

                int port = Integer.parseInt(portAndProtocol[0]);
                String proto = portAndProtocol[1];

                if (proto.equalsIgnoreCase("tcp")) {
                    portServiceMap.putIfAbsent(port, parts[0]);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки /etc/services: " + e.getMessage());
        }
    }

    public static Future<Integer> checkPort(final ExecutorService es, final String ip, final int port, final int timeout) {
        return es.submit(() -> {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ip, port), timeout);
                return port;
            } catch (Exception ex) {
                return -1;
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        final String ip = "127.0.0.1";
        final int timeout = 300;

        System.out.println("Сканирование портов 1 - 1024: \n");

        loadServices();

        final ExecutorService es = Executors.newFixedThreadPool(100);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int port = 1; port <= 65535; port++) {
            futures.add(checkPort(es, ip, port, timeout));
        }

        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("\nОткрытые порты:");
        for (Future<Integer> future : futures) {
            try {
                int result = future.get();
                if (result != -1) {
                    String service = portServiceMap.getOrDefault(result, "неизвестно");
                    System.out.println("Порт" + result + " открыт со службой: " + service);
                }
            } catch (Exception e) {
                System.err.println("Ошибка :( : " + e.getMessage());
            }
        }
    }
}
