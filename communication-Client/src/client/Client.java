package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket client;
        try {
            client = new Socket("127.0.0.1",9091); //new Socket(InetAddress.getLocalHost(),9090);
            boolean key = true;
            while (key) {
                Menu.welcomeMenu();
                switch (getMainOrder())
                {
                    case -1:
                        new Register(client).registerAccount();
                        break;
                    case -2:
                        new Login(client).login();
                        break;
                    case -3:
                        new DataOutputStream(client.getOutputStream()).writeInt(-3);
                        exit();
                        key = false;
                        break;
                }
                Menu.cls();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exit() {
        Menu.exit();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getMainOrder() {
        String order;
        while (true) {
            order = Input.getImporter().next();
            if (order.matches("[123]")) {
                return - Integer.parseInt(order);
            } else {
                System.out.println("               输入错误，请重新输入:                    ");
            }
        }
    }

}