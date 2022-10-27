package org.example.requestService;

import org.example.entities.Auth;
import org.example.entities.MessagePackage;
import org.example.services.ServerService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public record RequestHandler(Socket socket, ServerService serverService) implements Runnable {


    @Override
    public void run() {
        try (socket;
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            System.out.println(
                    "Connection with host " + socket.getInetAddress() + "\t" + socket.getPort() + " established");

            Object objRequest = inputStream.readObject();
            Object obj = redirect(objRequest);

            outputStream.writeObject(obj);

            System.out.println("Ending connection with host " + socket.getInetAddress() + "\t" + socket.getPort());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private Object redirect(Object payload) {
        if (payload instanceof Auth) {
            return serverService.authRequest((Auth) payload);
        }

        if (payload instanceof MessagePackage) {
            return serverService.receiveMessageRedirect((MessagePackage) payload);
        }

        return null;
    }

}
