package org.example;

import lombok.Getter;
import org.example.entities.Message;
import org.example.repositories.ClientRepository;
import org.example.requestsService.RequestServices;
import org.example.services.ClientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class Client {

    @Getter
    private ArrayList<Message> messages;

    public static void start() {
        var clientService = new ClientService(new RequestServices(), new ClientRepository());
        var clientRepository = new ClientRepository();
        ArrayList<Message> messages = new Client().getMessages();

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Insira seu domínio: ");
            String domain =  scanner.nextLine();

            System.out.println("Insira seu usuário: ");
            String alias =  scanner.nextLine();

            //String alias = "vinicius";

            System.out.println("Insira sua senha: ");
            String password = scanner.nextLine();
            //String password = "123";

            clientService.authenticate(domain,alias,password);

            var serverDomain = clientRepository.getConnectedServer(domain);
            System.out.println("Você está conectado no servidor: " + serverDomain.domain());

            /*
            *  TODO:
            *   - Opções para acessar as funcionalidades do email
            *   - Visualizar Mensagens
            *   - Enviar mensagem
            * */
            while (true){
                System.out.println("**********FUNCIONALIDADES**********\n\n" +
                                           "(Digite uma das opções abaixo)\n" +
                                           "Enviar e-mail: (1)\n" +
                                           "Ver e-mail(s) novos: (2)\n" +
                                           "Sair (Q)\n");
                String menu = scanner.nextLine();
                switch (menu) {
                    case "1" -> {
                        System.out.println("Insira o email de destino: ");
                        String recipientEmail = scanner.nextLine();
                        System.out.println("Insira o assunto: ");
                        String subject = scanner.nextLine();
                        System.out.println("Insira o corpo da mensagem: ");
                        String messageBody = scanner.nextLine();
                        System.out.println("Pressione enter para enviar: ");
                        scanner.nextLine();
                        clientService.sendMessage(recipientEmail, subject, messageBody);

                        break;
                    }
                    case "2" -> {
                        System.out.println("Buscando mensagens...");
                        clientRepository.saveMessages(messages);
                        messages.sort(Message::compareTo);
                        LocalDate dateFrom = messages.get(messages.size()-1).getSendDate();
                        clientService.receiveMessage(dateFrom, LocalDate.now());

                        System.out.println("E-mail(s):\n---------------------------");
                        for(int i =0; i < messages.size()-1; i++){
                            System.out.println("["+i+"] - " + messages.get(i).getSubject());
                        }
                        System.out.println("---------------------------\n\nSelecione uma das mensagens: ");
                        while(true){
                            String nMessage = scanner.nextLine();
                            if (nMessage.equals("Q") || nMessage.equals("q")) break;
                            boolean isNumber = false;
                            for(int i = 0; i < messages.size()-1; i++){
                                if(nMessage.equals(Integer.toString(i))){
                                    isNumber=true;
                                    break;
                                }
                            }
                            if(isNumber){
                                int indexMessage= Integer.parseInt(nMessage);
                                System.out.println("Remetente: " + messages.get(indexMessage).getFromAlias() +
                                                           "\nDomínio: " + messages.get(indexMessage).getFromDomain() +
                                                           "\nAssunto: " + messages.get(indexMessage).getSubject() +
                                                           "\n\n" + messages.get(indexMessage).getBody()
                                );
                                break;
                            } else {
                                System.out.println("Por favor, digite uma opção válida.");
                            }
                        }

                        break;
                    }

                    case "Q", "q" -> {
                        return;
                    }

                    default -> {
                        System.out.println("Por favor, digite uma opção válida.");
                        break;
                    }
                }
            }



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


 /* public static void start() {
    try (
            Socket socket = new Socket("127.0.0.1", 666);
            BufferedWriter writer = IOHelper.getBufferedWriter(socket.getOutputStream());
            BufferedReader reader = IOHelper.getBufferedReader(socket.getInputStream())
    ) {
      System.out.println("Using address " + socket.getInetAddress() + "\t" + socket.getLocalPort());
      System.out.println("Requesting");
      writer.write("Hello.java \n");
      writer.flush();
      System.out.println("Reading response:");
      System.out.println(reader.readLine());
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException("Cannot create client socket", e);
    }
  }*/
}
