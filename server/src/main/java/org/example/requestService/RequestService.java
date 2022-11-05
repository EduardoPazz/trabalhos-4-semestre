package org.example.requestService;

import org.example.entities.ServerCredentials;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public enum RequestService {
    ;

    public static Object requestServer(final ServerCredentials server, final Object payload) {
        Object response = null;
        try (final Socket socket = new Socket(server.address(), server.port());
             final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject(payload);
            response = inputStream.readObject();

        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
