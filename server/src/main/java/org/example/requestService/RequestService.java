package org.example.requestService;

import org.example.entities.ServerCredentials;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestService {

    public static Object requestServer(ServerCredentials server, Object payload) {
        Object response = null;
        try (Socket socket = new Socket(server.address(), server.port());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ) {

            outputStream.writeObject(payload);
            response = inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
