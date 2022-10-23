import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.example.IOHelper;


public class auth {
    private String type;
    private String alias;
    private String password;

    public auth(String type, String alias, String password) {
        this.type = type;
        this.alias = alias;
        this.password = password;
    }

    public void sendAuthAction(String alias,String password) {
            auth AuthPack = new auth("auth", alias, password);
            
            try (Socket socket = new Socket("localhost", 666);
                BufferedWriter writer = IOHelper.getBufferedWriter(socket.getOutputStream());
                BufferedReader reader = IOHelper.getBufferedReader(socket.getInputStream())) {
                writer.write(AuthPack.type + " " + AuthPack.alias + " " + AuthPack.password);
                writer.flush();
                System.out.println(reader.readLine());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
