package org.example;

import org.example.repositories.ClientRepository;
import org.example.requestsService.RequestServices;
import org.example.services.ClientService;

class Client {


  public static  void start()
  {
    var clientService = new ClientService(new RequestServices(), new ClientRepository());
    try
    {
      clientService.authenticate("vinicius","123");
    }
    catch (Exception e)
    {
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
