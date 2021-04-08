package client;

import java.io.DataInputStream;
import java.net.Socket;

public class ReceiveInformation extends Thread {
    private Socket socket;
    private boolean survival = true;

    public ReceiveInformation(Socket socket) {
        this.socket = socket;
    }

    public void killThread() {
        this.survival = false;
    }

    @Override
    public void run() {
        while (this.survival) {
            try {
                System.out.println(new DataInputStream(socket.getInputStream()).readUTF());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
