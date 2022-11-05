package org.example.requestsService;

import org.example.entities.ServerCredentials;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestServices {

    public Object requestServer(final ServerCredentials serverAddress,
            final Object payload) throws IOException, ClassNotFoundException {
        try (final Socket socket = new Socket(serverAddress.address(), serverAddress.port());
             final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject(payload);
            final Object response = inputStream.readObject();

            return response;
        }
    }
}
