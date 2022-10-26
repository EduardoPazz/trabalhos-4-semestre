package org.example.repositories;

import org.example.entities.ClientAddressCredentials;
import org.example.entities.Message;
import org.example.entities.ServerAddress;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ServerRepository {


    private List<ClientAddressCredentials> clients;
    private List<ServerAddress> knownServer;


    private Map<String, List<Message>> clientMessagesBox;


    public ServerRepository()
    {
        clients = new ArrayList<>();
        clients.add(new ClientAddressCredentials(
                "127.0.0.1",
                666,
                "vinicius",
                "123"
        ));

        kownServer = new ArrayList<ServerAddress>();

        clientMessagesBox = new HashMap<>();
    }


    public ClientAddressCredentials getByAliasAndPassword(String alias, String password)
    {
        var client = clients.stream()
                .filter(
                        x -> x.getAlias().equals(alias)
                                && x.getPassword().equals(password)
                ).findFirst()
                .orElse(null);

        return client;
    }



    public List<Message> getMessagesFromClientByPeriod(String alias, LocalDate dateFrom, LocalDate dateTo)
    {
        if(!clientMessagesBox.containsKey(alias)){
            clientMessagesBox.put(alias, new ArrayList<>());
            return clientMessagesBox.get(alias); //vai retornar vazio
        }

        var messages = clientMessagesBox.get(alias).stream().filter(
                x -> x.getSendDate().compareTo(dateFrom) > 0 && x.getSendDate().compareTo(dateTo) < 0
        ).collect(Collectors.toList());

        return  messages;
    }


    public void addReceivedClientMessage(String alias, Message message)
    {
        if(!clientMessagesBox.containsKey(alias)){
            clientMessagesBox.put(alias, new ArrayList<>());
        }

        clientMessagesBox.get(alias).add(message);
    }


    public ServerAddress getServerAddressByDomain(String domainParam)
    {
        var serverAddresses = knownServer.stream()
                .filter(x -> x.domain == domainParam)
                .findFirst()
                .orElse(null);

        return serverAddresses;
    }


    public void UpdateTokenClientCredentials(String alias, String token, LocalDate expiresDate)
    {
        var client = clients.stream()
                .filter(
                        x -> x.getAlias().equals(alias)
                ).findFirst()
                .get();

        client.setToken(token);
        client.setExpiresTokenIn(expiresDate);
    }


}
