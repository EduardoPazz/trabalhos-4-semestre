package org.example.requestService;

import org.example.entities.Auth;
import org.example.services.ServerService;

public class RequestService {

    private final ServerService _serverService;

    public RequestService(ServerService serverService) {
        _serverService = serverService;
    }


    public String handleRequestMessage(String requestMsg) {
        String type = requestMsg.split(";")[0];
        String response = "";
        if (type.equals("auth")) {
            response = handleAuthRequest(requestMsg);
        }

        return response;
    }

    public String handleAuthRequest(String request)
    {
        String[] authCredentialsArray  = request.split(";");
        var auth = new Auth(
          authCredentialsArray[1],
          authCredentialsArray[2]
        );

        var authResponse = _serverService.AuthRequest(auth);
        return authResponse.toString();
    }
}
