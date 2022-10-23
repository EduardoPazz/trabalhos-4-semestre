package org.example;

import org.example.repositories.ServerRepository;
import org.example.requestService.RequestHandler;
import org.example.requestService.RequestService;
import org.example.services.ServerService;

import java.io.IOException;
import java.net.ServerSocket;

class Server {


  public static void start()
  {
    try (
            ServerSocket welcomeSocket = new ServerSocket(666)
    ) {
        System.out.println("Server on");
      while (true)
      {
        new Thread(
                new RequestHandler(
                        welcomeSocket.accept(),
                        new RequestService(
                                new ServerService(
                                        new ServerRepository()
                                )
                        )
                )
        ).start();
      }
    } catch (IOException e) {
      throw new RuntimeException("Cannot create welcome socket", e);
    }
  }

  /*
  public static void start() {
    try (ServerSocket welcomeSocket = new ServerSocket(666)) {
      System.out.println("Server on");
      while (true) {
        new Thread(new RequestHandler(welcomeSocket.accept())).start();
      }

    } catch (IOException e) {
      throw new RuntimeException("Cannot create welcome socket", e);
    }
  }*/

  /*private record RequestHandler(Socket socket) implements Runnable {

    @Override
    public void run() {
      try (socket;
          BufferedWriter writer = IOHelper.getBufferedWriter(
              socket.getOutputStream());
          BufferedReader reader = IOHelper.getBufferedReader(
              socket.getInputStream())) {

        System.out.println(
            "Connection with client " + socket.getInetAddress()
                + "\t" + socket.getPort() + " established");

        writer.write(reader.readLine().toUpperCase());
        writer.flush();

        System.out.println(
            "Connection with client " + socket.getInetAddress()
                + "\t" + socket.getPort() + " closed");

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }
  */
}
