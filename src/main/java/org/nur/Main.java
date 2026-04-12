package org.nur;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor();
             var serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleClient(clientSocket));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void handleClient(Socket socket) {
        try (socket) {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String str = in.readUTF();
            System.out.println(Thread.currentThread().getName() + ": " + str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}