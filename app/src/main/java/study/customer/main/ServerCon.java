package study.customer.main;

import java.net.Socket;

public class ServerCon {

    public static Socket connectToServer() {

        //본인아이피
        final String SERVER_IP = "";

        //포트
        final int SERVER_PORT = 25565;

        try {
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            return clientSocket;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
