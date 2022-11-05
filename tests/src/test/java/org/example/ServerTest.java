package org.example;

import org.example.entities.ClientAddressCredentials;
import org.example.entities.ServerCredentials;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Set;

class ServerTest {
    private static final ServerCredentials mockedServerUsp = new ServerCredentials("localhost", 666, "usp.br");
    private static final ServerCredentials mockedServerUnesp = new ServerCredentials("localhost", 777, "unesp.br");
    private static final ServerCredentials mockedServerUnicamp = new ServerCredentials("localhost", 888, "unicamp.br");
    private static final ClientAddressCredentials mockedClientEdu =
            new ClientAddressCredentials("localhost", 50001, "Edu Paz", "1234");
    private static final ClientAddressCredentials mockedClientVini =
            new ClientAddressCredentials("localhost", 50001, "Vinicius", "4321");
    private static final ClientAddressCredentials mockedClientJB =
            new ClientAddressCredentials("localhost", 50001, "JB", "hentai");

    @Test
    void serverShouldReceiveMessageProperly() {

        final String simulatedInput = String.format("""
                                                            %s
                                                            %s
                                                            1
                                                            %s
                                                            Assunto teste
                                                            Mensagem teste
                                                                            
                                                            """, mockedClientEdu.getAlias(),
                                                    mockedClientEdu.getPassword(),
                                                    mockedClientVini.getAlias() + "@usp.br");
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        new Client().start(mockedServerUsp);
    }

}
