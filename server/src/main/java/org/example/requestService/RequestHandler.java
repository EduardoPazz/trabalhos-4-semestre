package org.example.requestService;

import org.example.IOHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public record  RequestHandler(Socket socket, RequestService _requestService) implements Runnable {
    @Override
    public void run() {
        try (
                socket;
                BufferedWriter writer = IOHelper.getBufferedWriter(socket.getOutputStream());
                BufferedReader reader = IOHelper.getBufferedReader(socket.getInputStream())
        )
        {
            System.out.println("Connection opened \n");
            System.out.println("Connection with client " + socket.getInetAddress() + "\t" + socket.getPort() + " established");
            String request = reader.readLine();
            System.out.println(request + "\n");

            String response = _requestService.handleRequestMessage(request) + "\n";
            System.out.println("response: " + response + "\n");
            
            writer.write(response);
            writer.flush();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
            throw new RuntimeException(e);
        }
    }
}
