package org.example;

import org.example.repositories.ClientRepository;
import org.example.requestsService.RequestServices;
import org.example.services.ClientService;

import java.util.Scanner;

class Client {


    public static void start() {
        var clientService = new ClientService(new RequestServices(), new ClientRepository());
        var clientRepository = new ClientRepository();

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

            System.out.println("Insira o email de destino: ");
            String recipientEmail = scanner.nextLine();

            System.out.println("Insira o assunto: ");
            String subject = scanner.nextLine();

            System.out.println("Insira o corpo da mensagem: ");
            String messageBody = scanner.nextLine();


            System.out.println("Pressione enter para enviar: ");
            scanner.nextLine();

            clientService.sendMessage(recipientEmail, subject, messageBody);

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
