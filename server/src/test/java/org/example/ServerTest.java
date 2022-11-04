package org.example;

import org.example.entities.ClientAddressCredentials;
import org.example.entities.ServerCredentials;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ServerTest {
    private ServerCredentials mockedServerUsp = new ServerCredentials("localhost", 666, "usp.br");
    private ServerCredentials mockedServerUnesp = new ServerCredentials("localhost", 777, "unesp.br");
    private ServerCredentials mockedServerUnicamp = new ServerCredentials("localhost", 888, "unicamp.br");
    private ClientAddressCredentials mockedClientEdu =
            new ClientAddressCredentials("localhost", 50001, "Edu Paz", "1234");
    private ClientAddressCredentials mockedClientVini =
            new ClientAddressCredentials("localhost", 50001, "Vinicius", "4321");
    private ClientAddressCredentials mockedClientJB = new ClientAddressCredentials("localhost", 50001, "JB", "hentai");

    @Test
    void start() {
        new Server().start(mockedServerUsp, Set.of(mockedServerUnesp, mockedServerUnicamp),
                           Set.of(mockedClientEdu, mockedClientVini, mockedClientJB));
    }

}
