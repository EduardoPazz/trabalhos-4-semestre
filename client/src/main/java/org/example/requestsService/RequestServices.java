package org.example.requestsService;

import org.example.IOHelper;
import org.example.entities.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;

public class RequestServices {


    public SendMessageResponse SendMessageRequest(ServerAddress serverAddress, Message message, String token)
    {
        var sendMessageRequest = "message;"
                            + token  + ";"
                            + message.getSendDate().toString() + ";"
                            + message.getToAlias() + ";"
                            + message.getToDomain() + ";"
                            + message.getSubject() + ";"
                            + message.getBody();
        var responseString = sendRequest(serverAddress, sendMessageRequest).split(";");

        return new SendMessageResponse(
                responseString[0],
                LocalDate.parse(responseString[1]),
                Integer.getInteger(responseString[2])
        );
    }



    public AuthResponse SendRequestAuth(ServerAddress serverAddress, Auth auth)
    {
        var responseString = sendRequest(serverAddress, auth.toString());
        var responseArr = responseString.split(";");

        return new AuthResponse(
                responseArr[0],
                Integer.getInteger(responseArr[1]),
                responseArr[2],
                LocalDate.parse(responseArr[3])
        );
    }




    private String sendRequest(ServerAddress serverAddress, String message)
    {
        try (
                Socket socket = new Socket(serverAddress.getAddress(), serverAddress.getPort());
                BufferedWriter writer = IOHelper.getBufferedWriter(socket.getOutputStream());
                BufferedReader reader = IOHelper.getBufferedReader(socket.getInputStream())
        ) {
            writer.write(message);
            writer.flush();
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
