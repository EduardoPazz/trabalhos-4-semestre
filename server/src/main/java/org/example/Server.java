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
    private static final ServerCredentials mockedServerUsp = new ServerCredentials("localhost", 666, "usp.br");
    private static final ServerCredentials mockedServerUnesp = new ServerCredentials("localhost", 777, "unesp.br");
    private static final ServerCredentials mockedServerUnicamp = new ServerCredentials("localhost", 888, "unicamp.br");
    private static final ClientAddressCredentials mockedClientEdu =
            new ClientAddressCredentials("localhost", 50001, "Edu", "1234");
    private static final ClientAddressCredentials mockedClientVini =
            new ClientAddressCredentials("localhost", 50001, "Vinicius", "4321");
    private static final ClientAddressCredentials mockedClientJB =
            new ClientAddressCredentials("localhost", 50001, "JB", "hentai");
    private Logger logger;
    public static void main(final String[] args) {
        final String configuration = args[0];

        switch (configuration) {
            case "1" -> {
                new Server().start(mockedServerUsp, Set.of(mockedServerUnesp, mockedServerUnicamp),
                                   Set.of(mockedClientEdu, mockedClientJB));
            }

            case "2" -> {
                new Server().start(mockedServerUnesp, Set.of(mockedServerUsp, mockedServerUnicamp),
                                   Set.of(mockedClientVini));
            }
        }
    }

    void start(final ServerCredentials ownCredentials, final Set<ServerCredentials> knownServers,
            final Set<ClientAddressCredentials> clients) {

        logger = new Logger(ownCredentials.toString());

        final ServerRepository repository = new ServerRepository(ownCredentials, knownServers, clients);

        final ServerService service = new ServerService(repository);

        try (final ServerSocket welcomeSocket = new ServerSocket(ownCredentials.port())) {
            logger.log("Server started");
            while (true) {
                new Thread(new RequestHandler(welcomeSocket.accept(), service)).start();
            }
        } catch (final IOException e) {
            throw new RuntimeException("Cannot create welcome socket", e);
        }
    }
}
