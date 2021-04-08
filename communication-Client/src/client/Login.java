package client;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {

    private Socket socket;

    public Login(Socket socket) {
        this.socket = socket;
    }

    public void login() throws InterruptedException {
        boolean key = false;
        while (!key) {
            Menu.loginMenu();
            String loginOrder = getLoginOrder();
            switch (loginOrder)
            {
                case "1" ://快捷登录
                    key = quickLogin();
                    break;
                case "2" ://账号密码登陆
                    showLoginRecord();
                    key = inputUserIdPassword();
                    break;
                case "3" ://取消登录
                    return;
            }
        }
        Thread.sleep(1000);
        Menu.cls();
    }

    public boolean inputUserIdPassword() {
        boolean isLogin = false;
        while (!isLogin) {
            System.out.println("         请输入账号(或输入“&”取消登陆)：");
            String id = Input.getImporter().next();
            if (id.equals("&")) {
                System.out.println("                   取消成功");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            System.out.println("         请输入密码(或输入“&”取消登陆)：");
            String password = Input.getImporter().next();
            if (password.equals("&")) {
                System.out.println("                   取消成功");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            isLogin = passwordLogin(id,password);
        }
        return isLogin;
    }

    public boolean quickLogin() {
        File data = new File("c:/communication/client/userIdPassword.txt");
        if (!data.exists() || data.length() == 0) {
            System.out.println("              没有可以快捷登录的账号！");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try (
                    Scanner scanner = new Scanner(data);
            ){
                Map<String,String> userIdPassword = new HashMap<>();
                while (scanner.hasNextLine()) {
                    String[] contents = scanner.nextLine().split(" : ");
                    userIdPassword.put(contents[0],contents[1]);
                }

                System.out.println("              您可以快捷登录的账号如下：");
                for (String s : userIdPassword.keySet()) {
                    System.out.println("                   " + s);
                }

                System.out.println(" 请输入您想登陆的账号（或者输入“&”,取消快捷登录）：");

                while (true) {
                    String id = Input.getImporter().next();
                    if (id.equals("&")) {
                        System.out.println("                  取消成功");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    if (userIdPassword.containsKey(id)) {
                        return passwordLogin(id,userIdPassword.get(id));
                    } else {
                        System.out.println("该账号不能快捷登录！请重新输入（或者输入“&”,取消快捷登录）\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean passwordLogin(String id, String password) {
        boolean isLogin = false;
        if (firstJudge(id,password)){
            try {
                new DataOutputStream(socket.getOutputStream()).writeInt(-2);
                new DataOutputStream(socket.getOutputStream()).writeUTF(id);
                new DataOutputStream(socket.getOutputStream()).writeUTF(password);

                int result = new DataInputStream(socket.getInputStream()).readInt();
                String massage = "";
                switch (result)
                {
                    case 1 :
                        isLogin = true;
                        massage = "登录成功！";
                        break;
                    case 0 :
                        massage = "用户不存在";
                        break;
                    case -1:
                        massage = "密码错误！";
                        break;
                    case -2:
                        massage = "该账号已登录";
                        break;
                }
                System.out.println("                  " + massage);
                Thread.sleep(1000);
                if (result == 1) {
                    saveQuickLogin(id, password);
                    Thread.sleep(1000);
                    Menu.cls();
                    //登录后线程
                    Thread user = new Thread(new ClientThread(socket));
                    user.start();
                    user.join();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\n       账号或者密码格式错误，请确认后再试一次！\n");
        }
        return isLogin;
    }

    public void saveQuickLogin(String id, String password) {
        String o;

        System.out.println("   是否保存密码？以便下次快捷登录（1.是  0.否）");
        while (!(o = Input.getImporter().next()).matches("[01]")) {
            System.out.println("          输入有误，请重新输入（1.是  0.否）：");
        }

        if (o.equals("1")) {
            String data = id + " : " + password + "\n";
            File userIdPassword = new File("c:/communication/client/userIdPassword.txt");
            if (!userIdPassword.exists()) {
                boolean temp;
                temp = userIdPassword.getParentFile().exists() || userIdPassword.getParentFile().mkdirs();
                try {
                    temp = userIdPassword.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (
                    RandomAccessFile writer = new RandomAccessFile(userIdPassword,"rw");
            ) {
                writer.seek(userIdPassword.length());
                writer.write(data.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("                  保存成功！");
        }
    }

    public boolean firstJudge(String id ,String password) {
        return id.matches("[1-9][\\d]{9}") && password.matches("[\\w]{6,13}");
    }

    public void showLoginRecord() {
        File loginRecord = new File("c:/communication/client/loginRecord.txt");
        try (
                Scanner scanner = new Scanner(loginRecord);
        ) {
            if (loginRecord.exists() && loginRecord.length() != 0) {
                System.out.println("            以下是您注册或登陆过的账号:");
                while (scanner.hasNextLine()) {
                    System.out.println("                 " + scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getLoginOrder() {
        String loginOrder;
        while (true) {
            loginOrder = Input.getImporter().next();
            if (loginOrder.matches("[123]")) {
                break;
            }
            else
                System.out.println("                输入错误，请重新输入:                    ");
        }
        return loginOrder;
    }

}