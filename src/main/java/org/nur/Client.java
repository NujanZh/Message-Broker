package org.nur;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            Thread t1 = new Thread(() -> sendMessage("Hello from client!"));
            Thread t2 = new Thread(() -> sendMessage("Hello from client!"));
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void sendMessage(String message) {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                out.println(message);
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
