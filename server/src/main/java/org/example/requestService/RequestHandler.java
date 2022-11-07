package org.example.requestService;

import org.example.entities.Auth;
import org.example.entities.MessagePackage;
import org.example.entities.ReceiveClientMessageRequestPackage;
import org.example.services.ServerService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


//função que identifica qual o tipo de requisição solicitada pelo cliente. 
//As funções são centralizadas na função request Server, que recebe o pacote de requisição e o 
//endereço do servidor. A partir daí, a função identifica qual o tipo de requisição, através da variável payload 
//e chama a função correspondente.
public record RequestHandler(Socket socket, ServerService serverService) implements Runnable {

    @Override
    public void run() {
        try (socket;
             final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            System.out.println(
                    "Connection with host " + socket.getInetAddress() + "\t" + socket.getPort() + " established");

            final Object requestPayload = inputStream.readObject();
            final Object responsePayload = reducePayload(requestPayload);

            outputStream.writeObject(responsePayload);

            System.out.println("Ending connection with host " + socket.getInetAddress() + "\t" + socket.getPort());
        } catch (final IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Object reducePayload(final Object payload) {
        if (payload instanceof Auth) {
            return serverService.authRequest((Auth) payload);
        }

        if (payload instanceof MessagePackage) {
            return serverService.receiveMessage((MessagePackage) payload);
        }

        if (payload instanceof ReceiveClientMessageRequestPackage) {
            return serverService.receiveClientMessageRequest((ReceiveClientMessageRequestPackage) payload);
        }

        return null;
    }
}
