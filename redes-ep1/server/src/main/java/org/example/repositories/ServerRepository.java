package org.example.repositories;

import org.example.entities.ClientAddressCredentials;
import org.example.entities.ClientCredentials;
import org.example.entities.Message;
import org.example.entities.ServerCredentials;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


// Classe que representa o repositório do servidor. 
public class ServerRepository {

    private final ServerCredentials ownCredentials;
    private final Set<ServerCredentials> knownServers;
    private final Map<ClientAddressCredentials, Set<Message>> clientToMessages;


    //Funççao construtora do repositório do servidor. 
    public ServerRepository(final ServerCredentials ownCredentials, final Set<ServerCredentials> knownServers,
            final Set<ClientAddressCredentials> clients) {
        this.ownCredentials = ownCredentials;
        this.knownServers = knownServers;
        this.clientToMessages = clients.stream().collect(Collectors.toMap(client -> client, client -> new HashSet<>()));
    }

    //Função que armazena mensagens recebidas de um cliente
    public void storeMessage(final String alias, final Message message) throws ClientNotFoundException {
        getClientMessagesByAlias(alias).add(message);
    }

    //Função que retorna as mensagens recebidas de um cliente
    private Set<Message> getClientMessagesByAlias(final String alias) throws ClientNotFoundException {
        return clientToMessages.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getAlias().equals(alias))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException(alias))
                .getValue();
    }

    //Função que retorna as credenciais de um cliente
    public ClientAddressCredentials getClientByAliasAndPassword(final String alias,
            final String password) throws ClientNotFoundException {
        return clientToMessages.keySet()
                .stream()
                .filter(client -> client.getAlias().equals(alias) && client.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException(alias + " " + password));
    }

    //Retorna o servidor conhecido pelo dominio
    public ServerCredentials getServerByDomain(final String domain) throws DomainNotFoundException {
        return knownServers.stream()
                .filter(server -> server.domain().equals(domain))
                .findFirst()
                .orElseThrow(() -> new DomainNotFoundException(domain));
    }

    //Atualiza o token de um cliente autenticado
    public void updateTokenClientCredentials(final String alias, final String token,
            final LocalDateTime expiresDate) throws ClientNotFoundException {
        final ClientAddressCredentials outdatedClient = clientToMessages.keySet()
                .stream()
                .filter(client -> client.getAlias().equals(alias))
                .findFirst()
                .orElseThrow(() -> new ClientNotFoundException(alias));

        outdatedClient.setToken(token);
        outdatedClient.setExpiresTokenIn(expiresDate);
    }

    
    public String getOwnDomain() {
        return ownCredentials.domain();
    }

    //Retorna as mensagens recebidas de um cliente de acordo com um range de datas. 
    //Recebe uma requisição contendo as informações do cliente e os limites do range de datas, 
    ///realiza o filtro, e envia um Set List que será armazenado no repositório do cliente
    public List<Message> getMessagesByClientAddressAndDateRange(final ClientCredentials clientCredentials,
            final LocalDateTime dateFrom, final LocalDateTime dateTo) {
        try {
            return clientToMessages.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().getAlias().equals(clientCredentials.username()))
                    .findFirst()
                    .orElseThrow(() -> new ClientNotFoundException(clientCredentials.username()))
                    .getValue()
                    .stream()
                    .filter(message ->
                                    (message.getSendDate().isAfter(dateFrom) || message.getSendDate().isEqual(dateFrom))
                                            && (message.getSendDate().isBefore(dateTo) || message.getSendDate()
                                            .isEqual(dateTo)))
                    .collect(Collectors.toList());
        } catch (final ClientNotFoundException e) {

            e.printStackTrace();
        }
        return null;
    }
}
