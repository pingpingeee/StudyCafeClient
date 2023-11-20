package com.example.mysecondproject;

import java.net.Socket;

public class ServerCon {

    public static Socket connectToServer() {

        //본인아이피
        //final String SERVER_IP = "192.168.200.104";
        final String SERVER_IP = "58.226.144.224";
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

    //닉넴가져오기
/*    public static String getNicknameFromServer(String account, String password) {
        String serverResponse = null;

        try {
            Socket clientSocket = connectToServer();
            if (clientSocket != null) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out.println("GET_NICKNAME");
                out.println(account);
                out.println(password);

                serverResponse = in.readLine();

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverResponse;
    }*/

}
