package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

class Client {

  public static void start() {
    try (Socket socket = new Socket("127.0.0.1", 666);
        BufferedWriter writer = IOHelper.getBufferedWriter(
            socket.getOutputStream());
        BufferedReader reader = IOHelper.getBufferedReader(
            socket.getInputStream())) {
      System.out.println("Using address " + socket.getInetAddress() + "\t"
          + socket.getLocalPort());
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
  }
}
