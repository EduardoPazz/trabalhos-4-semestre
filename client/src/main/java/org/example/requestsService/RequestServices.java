package org.example.requestsService;

import org.example.IOHelper;
import org.example.entities.Auth;
import org.example.entities.AuthResponse;
import org.example.entities.Message;
import org.example.entities.ServerAddress;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;

public class RequestServices {


    public /*SendMessageReponse*/ void SendMessageRequest(ServerAddress serverAddress, Message message)
    {

    }

    public AuthResponse SendRequestAuth(ServerAddress serverAddress, Auth auth)
    {
        try (
                Socket socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
                BufferedWriter writer = IOHelper.getBufferedWriter(socket.getOutputStream());
                BufferedReader reader = IOHelper.getBufferedReader(socket.getInputStream())
        ) {
            writer.write("auth|" + auth.getAlias() + "|" + auth.getPassword());
            writer.flush();
            String[] response = reader.readLine().split("|");
            var authReponse = new AuthResponse(response[0], response[1],response[1], LocalDate.parse(response[3]));
            return authReponse;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
