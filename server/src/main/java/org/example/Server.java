package org.example;

import org.example.repositories.ServerRepository;
import org.example.requestService.RequestHandler;
import org.example.services.ServerService;

import java.io.IOException;
import java.net.ServerSocket;

class Server {

    void start() {
        ServerRepository repository = new ServerRepository();
        ServerService service = new ServerService(repository);

        try (ServerSocket welcomeSocket = new ServerSocket(666)) {
            System.out.println("Server on");
            while (true) {
                new Thread(
                        new RequestHandler(welcomeSocket.accept(), service)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create welcome socket", e);
        }
    }
}
