package org.example.requestsService;

import org.example.entities.ServerCredentials;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
Classe que representa um serviço de requisição. É utilizada pra enviar requisições ao servidor.
 */

public class RequestServices {

    public Object requestServer(final ServerCredentials serverAddress,
            final Object payload) throws IOException, ClassNotFoundException {
        try (final Socket socket = new Socket(serverAddress.address(), serverAddress.port());
             final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject(payload);

            return inputStream.readObject();
        }
    }
}
