package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static UserBank userBank = new UserBank();

    public static void main(String[] args) {
        {
            System.out.println();
            System.out.println("=======服务器启动======");
            System.out.println();
        }
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(9091);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}