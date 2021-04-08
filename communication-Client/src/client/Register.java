package client;

import java.io.*;
import java.net.Socket;

public class Register {
    private Socket socket;

    public Register(Socket socket) {
        this.socket = socket;
    }

    public void registerAccount() {
        Menu.registerMenu();
        String password = inputPassword();
        if (password != null) {
            String name = inputName();
            if (name != null) {
                String id = getId(name,password);
                if (id != null) {
                    System.out.println("                     注册成功！           ");
                    System.out.println("         您的账号是: " + id + "  请妥善保管");
                    saveId(id);
                    System.out.println("                  即将返回主页面");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getId(String name , String password) {
        String id = null;
        try {
            new DataOutputStream(socket.getOutputStream()).writeInt(-1);
            new DataOutputStream(socket.getOutputStream()).writeUTF(name);
            new DataOutputStream(socket.getOutputStream()).writeUTF(password);
            id = new DataInputStream(socket.getInputStream()).readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    private String inputName() {
        String name;
        while (true) {
            System.out.println("请输入您的昵称(或者输入'&'取消注册)(不能包含空格，否则空格后的字段系统会自动忽略)：");
            name = Input.getImporter().next();
            if (name.equals("&")) {
                System.out.println("                取消成功");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            }
            else if (name.equals(""))
                System.out.println("      昵称不能为空！请重新输入");
            else
                return name;
        }
        return null;
    }

    private String inputPassword() {
        String password;
        while (true) {
            password = Input.getImporter().next();
            if (password.equals("&")) {
                System.out.println("                取消成功");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            if (password.matches("[\\w]{6,13}")) {
                return password;
            } else {
                System.out.println("     密码格式错误，请重新输入(或者输入'&'取消注册)");
            }
        }
        return null;
    }

    private void saveId(String id) {
        File loginRecord = new File("C:/communication/client/LoginRecord.txt");

        if (!loginRecord.exists()) {
            boolean isCreated;
            try {
                if (!loginRecord.getParentFile().exists()) {
                    isCreated = loginRecord.getParentFile().mkdirs();
                }
                isCreated = loginRecord.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (
                RandomAccessFile writeAccountID =
                        new RandomAccessFile(loginRecord,"rw")
        ){
            writeAccountID.seek(writeAccountID.length());
            writeAccountID.write((id + '\n').getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
