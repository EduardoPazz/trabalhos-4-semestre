package org.example.requestsService;

import org.example.entities.ServerAddress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestServices {


    public Object requestServer(ServerAddress serverAddress, Object objRequest)
    {
        Object response = null;
        try {
            Socket socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeObject(objRequest);
            response = inputStream.readObject();

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }


}
