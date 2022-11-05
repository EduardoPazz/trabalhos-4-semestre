package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class authAck {
    private final String type;
    private final String alias;
    private final String password;

    public authAck(final String type, final String alias, final String password) {
        this.type = type;
        this.alias = alias;
        this.password = password;
    }

    public void receiveAuthRequest() {
        try (final ServerSocket welcomeSocket = new ServerSocket(666)) {
            System.out.println("Server on");
            while (true) {
                new Thread(new RequestHandler(welcomeSocket.accept())).start();
            }

        } catch (final IOException e) {
            throw new RuntimeException("Cannot create welcome socket", e);
        }
    }

    private record RequestHandler(Socket socket) implements Runnable {

        @Override
        public void run() {
            try (socket;
                 final BufferedWriter writer = IOHelper.getBufferedWriter(socket.getOutputStream());
                 final BufferedReader reader = IOHelper.getBufferedReader(socket.getInputStream())) {

                System.out.println(
                        "Connection with client " + socket.getInetAddress() + "\t" + socket.getPort() + " established");

                final String clientAliasString = reader.readLine().split(" ")[1];
                final String clientPasswordString = reader.readLine().split(" ")[2];

                System.out.println(
                        "Connection with client " + socket.getInetAddress() + "\t" + socket.getPort() + " closed");

            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
