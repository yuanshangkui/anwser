package server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Queue;

public class SendInformation extends Thread {
    private Socket socket;
    private Queue<Information> information;
    private boolean survival = true;

    public SendInformation(Socket socket, Queue<Information> information) {
        this.socket = socket;
        this.information = information;
    }

    public void killThread() {
        this.survival = false;
    }

    @Override
    public void run() {
        try {
            while (this.survival) {
                if (this.information.size() > 0) {
                    new DataOutputStream(socket.getOutputStream()).writeUTF(Objects.requireNonNull(this.information.poll()).toString());
                } else {
                    Thread.sleep(1000);
                }
            }
            new DataOutputStream(socket.getOutputStream()).writeUTF("             聊天结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
