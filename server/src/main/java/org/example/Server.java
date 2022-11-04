package org.example;

import org.example.entities.ClientAddressCredentials;
import org.example.entities.ServerCredentials;
import org.example.repositories.ServerRepository;
import org.example.requestService.RequestHandler;
import org.example.services.ServerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;

class Server {
    private Logger logger;

    void start(ServerCredentials ownCredentials, Set<ServerCredentials> knownServers,
               Set<ClientAddressCredentials> clients) {

        logger = new Logger(ownCredentials.toString());

        ServerRepository repository = new ServerRepository(ownCredentials, knownServers, clients);

        ServerService service = new ServerService(repository);

        try (ServerSocket welcomeSocket = new ServerSocket(ownCredentials.port())) {
            logger.log("Server started");
            while (true) {
                new Thread(
                        new RequestHandler(welcomeSocket.accept(), service)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create welcome socket", e);
        }
    }
}
