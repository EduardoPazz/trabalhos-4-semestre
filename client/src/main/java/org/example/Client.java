package org.example;

import org.example.entities.Message;
import org.example.entities.ServerCredentials;
import org.example.exceptions.ClientNotFoundException;
import org.example.exceptions.DomainNotFoundException;
import org.example.exceptions.NotAuthenticatedException;
import org.example.repositories.ClientRepository;
import org.example.requestsService.RequestServices;
import org.example.services.ClientService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Scanner;

public class Client {

    private static final ServerCredentials mockedServerUsp = new ServerCredentials("localhost", 666, "usp.br");
    private static final ServerCredentials mockedServerUnesp = new ServerCredentials("localhost", 777, "unesp.br");
    private static final ServerCredentials mockedServerUnicamp = new ServerCredentials("localhost", 888, "unicamp.br");

    public static void main(final String[] args) {
        final String configuration = args[0];

        final ServerCredentials mockedServer = switch (configuration) {
            case "1" -> mockedServerUsp;
            case "2" -> mockedServerUnesp;
            case "3" -> mockedServerUnicamp;
            default -> throw new RuntimeException();
        };

        new Client().start(mockedServer);
    }
    public void start(final ServerCredentials serverCredentials) {
        final ClientRepository clientRepository = new ClientRepository(serverCredentials);
        final var clientService = new ClientService(new RequestServices(), clientRepository);

        try (final Scanner scanner = new Scanner(System.in)) {

            System.out.println("Bem vindo ao cliente de email " + serverCredentials.domain() + "!");

            login(clientService, scanner);

            while (true) {
                System.out.println("""
                                           **********FUNCIONALIDADES**********

                                           (Digite uma das opções abaixo)

                                           [1] Enviar e-mail
                                           [2] Visualizar e-mails
                                           [Q] Sair
                                           """);

                final String menu = scanner.nextLine();

                switch (menu) {
                    case "1" -> {
                        final String recipientEmail = getValidInput(scanner, "Insira o email de destino: ");
                        final String subject = getValidInput(scanner, "Insira o assunto: ");
                        final String messageBody = getValidInput(scanner, "Insira o corpo da mensagem: ");

                        System.out.println("Pressione enter para enviar: ");
                        scanner.nextLine();

                        try {
                            clientService.sendMessage(recipientEmail, subject, messageBody);
                        } catch (final ClientNotFoundException | DomainNotFoundException e) {
                            System.out.println(e.getMessage());
                            break;
                        } catch (final NotAuthenticatedException e) {
                            login(clientService, scanner);
                            continue;
                        }
                        catch(Exception e)
                        {
                            System.out.println("Falha ao conectar-se ao servidor, tente novamente mais tarde!");
                            continue;
                        }
                        System.out.println("Mensagem enviada com sucesso!");
                    }
                    case "2" -> {
                        System.out.println("Buscando mensagens...");

//                        final LocalDate dateFrom = messages.get(messages.size() - 1).getSendDate();
                        List<Message> messages = clientRepository.getReceivedMessages();

                        final LocalDateTime dateFrom = messages.size() == 0 ? LocalDateTime.of(1982, Month.JANUARY, 1, 1,1) : messages.get(messages.size() - 1).getSendDate();


                        try {
                            clientService.receiveMessage(dateFrom, LocalDateTime.now());
                        }catch(Exception e) {
                            System.out.println("Falha ao conectar-se ao servidor, exibindo mensagens locais!");
                        }

                        messages = clientRepository.getReceivedMessages();

                        System.out.println("E-mail(s):\n---------------------------");
                        for (int i = 0; i < messages.size(); i++) {
                            System.out.println("[" + i + "] - " + messages.get(i).getSubject());
                        }

                        System.out.println("---------------------------\n\nSelecione uma das mensagens: ");

                        while (true) {
                            final String nMessage = scanner.nextLine();
                            if (nMessage.equals("Q") || nMessage.equals("q")) break;
                            boolean isNumber = false;
                            for (int i = 0; i < messages.size(); i++) {
                                if (nMessage.equals(Integer.toString(i))) {
                                    isNumber = true;
                                    break;
                                }
                            }
                            if (isNumber) {
                                final int indexMessage = Integer.parseInt(nMessage);
                                System.out.println(
                                        "Remetente: " + messages.get(indexMessage).getFromAlias() + "\nDomínio: "
                                                + messages.get(indexMessage).getFromDomain() + "\nAssunto: "
                                                + messages.get(indexMessage).getSubject() + "\n\n" + messages.get(
                                                indexMessage).getBody());
                                break;
                            } else {
                                System.out.println("Por favor, digite uma opção válida.");
                            }
                        }
                    }

                    case "Q", "q" -> {
                        System.out.println("Saindo...");
                        System.exit(0);
                    }

                    default -> System.out.println("Por favor, digite uma opção válida.");
                }
            }
        } catch (final Exception e) {
            System.out.println("Algo deu errado, tente novamente.");
            System.exit(1);
        }
    }
    private void login(final ClientService clientService, final Scanner scanner) {
        while (true) {
            final String username = getValidInput(scanner, "Insira um nome de usuário: ");
            final String password = getValidInput(scanner, "Insira sua senha: ");

            try {
                clientService.authenticate(username, password);
                break;
            } catch (final NotAuthenticatedException e) {
                System.out.println(e.getMessage());
            } catch (final Exception e) {
                System.out.println("Erro ao conectar-se ao servidor. Tente novamente mais tarde.");
            }
        }
    }
    private String getValidInput(final Scanner scanner, final String message) {
        System.out.println(message);
        String input = scanner.nextLine();
        while (input.trim().isEmpty()) {
            System.out.println("Por favor, digite uma opção válida.");
            input = scanner.nextLine();
        }
        return input;
    }
}
