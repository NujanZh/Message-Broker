package org.nur;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor();
             var serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> {
                    System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                    handleClient(clientSocket);
                });
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (socket) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String line = in.readLine();
                System.out.println(Thread.currentThread().threadId() + ": " + line);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}