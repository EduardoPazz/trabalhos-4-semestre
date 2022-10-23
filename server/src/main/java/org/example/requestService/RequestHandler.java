package org.example.requestService;

import org.example.entities.Auth;
import org.example.services.ServerService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public record  RequestHandler(Socket socket, RequestService _requestService, ServerService _serverService) implements Runnable {


    @Override
    public void run() {
        try (
                socket;
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        )
        {
            System.out.println("Connection opened \n");
            System.out.println("Connection with client " + socket.getInetAddress() + "\t" + socket.getPort() + " established");

            Auth auth = (Auth) inputStream.readObject();
            var obj = redirect(auth);
            outputStream.writeObject(obj);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private Object redirect(Object obj)
    {
        if(obj instanceof Auth)
        {
            return _serverService.AuthRequest((Auth) obj);
        }

        return null;
    }

/*    @Override
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
 */
}
