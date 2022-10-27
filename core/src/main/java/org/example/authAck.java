package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.example.IOHelper;

public class authAck {
    private String type;
    private String alias;
    private String password;

    public authAck(String type, String alias, String password) {
        this.type = type;
        this.alias = alias;
        this.password = password;
    }

    public void receiveAuthRequest() {
        try (ServerSocket welcomeSocket = new ServerSocket(666)) {
            System.out.println("Server on");
            while (true) {
                new Thread(new RequestHandler(welcomeSocket.accept())).start();
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot create welcome socket", e);
        }
    }

    private record RequestHandler(Socket socket) implements Runnable {

        @Override
        public void run() {
            try (socket;
                BufferedWriter writer = IOHelper.getBufferedWriter(
                    socket.getOutputStream());
                BufferedReader reader = IOHelper.getBufferedReader(
                    socket.getInputStream())) {

                System.out.println(
                    "Connection with client " + socket.getInetAddress()
                        + "\t" + socket.getPort() + " established");

                String clientAliasString = reader.readLine().split(" ")[1];
                String clientPasswordString = reader.readLine().split(" ")[2];

                System.out.println(
                    "Connection with client " + socket.getInetAddress()
                        + "\t" + socket.getPort() + " closed");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
