package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
    private Socket socket;
    String userName;
    String userId;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            userName = new DataInputStream(socket.getInputStream()).readUTF();
            userId = new DataInputStream(socket.getInputStream()).readUTF();
            boolean online = true;
            while (online) {
                Menu.homePage(userName,userId);
                switch (getOrder())
                {
                    case 0 ://退出登录
                    {
                        online = outLogin();
                        break;
                    }
                    case 1 ://展示好友列表
                    {
                        showMyFriends();
                        break;
                    }
                    case 2 ://添加好友（发送好友申请
                    {
                        sendFriendApplication();
                        break;
                    }
                    case 3 ://添加好友（审核
                    {
                        checkFriend();
                        break;
                    }
                    case 4 ://私聊发消息
                    {
                        personalChat();
                        break;
                    }
                    case 5 ://我的群聊
                    {
                        showMyGroups();
                        break;
                    }
                    case 6 ://创建群聊
                    {
                        createGroup();
                        break;
                    }
                    case 7 ://加入群聊
                    {
                        sendGroupApplication();
                        break;
                    }
                    case 8 ://审核入群申请
                    {
                        checkGroup();
                        break;
                    }
                    case 9 ://邀请入群
                    {
                        inviteTnGroup();
                        break;
                    }
                    case 10 ://入群邀请
                    {
                        dealGroupInvitation();
                        break;
                    }
                    case 11://群聊
                    {
                        groupChat();
                        break;
                    }
                    case 12 ://退群
                    {
                        outGroup();
                        break;
                    }
                }
                Menu.cls();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void groupChat() throws Exception{
        String groupId;
        while (true) {
            System.out.println("        请输入群聊号（或者输入&取消）:");
            groupId = Input.getImporter().next();
            if (groupId.equals("&")) {
                System.out.println("              即将退出");
                Thread.sleep(1000);
                return;
            } else if (groupId.matches("[1-9][\\d]{7}")) {
                break;
            } else {
                System.out.println("    输入有误，请重新输入（或者输入&取消）");
            }
        }
        new DataOutputStream(socket.getOutputStream()).writeInt(11);
        new DataOutputStream(socket.getOutputStream()).writeUTF(groupId);
        String result = new DataInputStream(socket.getInputStream()).readUTF();
        if (result.contains(" : ")) {
            Menu.cls();
            System.out.println("        群聊: " + result);
            System.out.println("  <系统提示>: 回车键发送消息  输入&退出聊天 φ(>ω<*) ");

            ReceiveInformation receiver = new ReceiveInformation(this.socket);
            receiver.start();

            String massage;
            while (true) {
                massage = Input.getImporter().nextLine();
                if (massage.equals("&"))
                    break;
                else if (massage.equals(""))
                    System.out.println("\n<系统提示>:发送消息不能为空");
                else
                    new DataOutputStream(socket.getOutputStream()).writeUTF(massage);
            }
            new DataOutputStream(socket.getOutputStream()).writeUTF(massage);
            receiver.killThread();
        } else {
            System.out.println(result);
        }
        Thread.sleep(1000);
    }

    private void personalChat() throws Exception {
        String friendId;
        while (true) {
            System.out.println("         请输入好友的账号（或者输入‘&’取消）");
            friendId = Input.getImporter().next();
            if (friendId.equals("&")) {
                System.out.println("          即将退出...");
                Thread.sleep(1000);
                return;
            } else if (friendId.matches("[1-9][\\d]{9}")) {
                break;
            } else {
                System.out.println("          账号错误！请再试一次");
            }
        }
        new DataOutputStream(socket.getOutputStream()).writeInt(4);
        new DataOutputStream(socket.getOutputStream()).writeUTF(friendId);
        String result = new DataInputStream(socket.getInputStream()).readUTF();
        if (result.contains(" : ")) {
            Menu.cls();
            System.out.println("   您与好友: " + result + " 的聊天");
            System.out.println("  <系统提示>: 回车键发送消息  输入&退出聊天 φ(>ω<*) ");

            ReceiveInformation receiver = new ReceiveInformation(this.socket);
            receiver.start();

            String massage;
            while (true) {
                massage = Input.getImporter().nextLine();
                if (massage.equals("&"))
                    break;
                else if (massage.equals(""))
                    System.out.println("\n<系统提示>:发送消息不能为空");
                else
                    new DataOutputStream(socket.getOutputStream()).writeUTF(massage);
            }
            new DataOutputStream(socket.getOutputStream()).writeUTF(massage);
            receiver.killThread();
        } else {
            System.out.println(result);
        }
        Thread.sleep(1000);
    }

    private void outGroup() throws Exception {
        String groupId;
        while (true) {
            System.out.println("       请输入您要退出的群聊的账号(或输入&退出)");
            groupId = Input.getImporter().next();
            if (groupId.equals("&")) {
                System.out.println("                 取消成功");
                Thread.sleep(1000);
                return;
            } else if (!groupId.matches("[1-9][\\d]{7}")) {
                System.out.println("          群号错误，请重新输入！");
            } else {
                break;
            }
        }
        new DataOutputStream(socket.getOutputStream()).writeInt(12);
        new DataOutputStream(socket.getOutputStream()).writeUTF(groupId);
        System.out.println(new DataInputStream(socket.getInputStream()).readUTF());
        Thread.sleep(1500);
    }

    private void dealGroupInvitation() throws Exception {
        new DataOutputStream(socket.getOutputStream()).writeInt(10);
        String[] invitations = (String[]) new ObjectInputStream(socket.getInputStream()).readObject();
        String[] back = new String[invitations.length];
        int size = 0;
        if (invitations.length == 0) {
            System.out.println("          您还未收到任何入群邀请");
        } else {
            for (String invitation : invitations) {
                System.out.println("            您收到的入群邀请如下：");
                System.out.println("    邀请人账号    邀请加入的群号");
                System.out.println(invitation);
                System.out.println("是否同意加入该群？（1.同意  0.拒绝  2.返回）请输入：");
                String order;
                while (true) {
                    order = Input.getImporter().next();
                    if (order.matches("[0-2]")) {
                        break;
                    } else {
                        System.out.println("  输入有误，请重新输入（1.同意  0.拒绝  2.返回）");
                    }
                }
                if (order.equals("1")) {
                    back[size++] = invitation + " : 1";
                    System.out.println("             同意成功,等待群主同意");
                } else if (order.equals("0")) {
                    back[size++] = invitation + " : 0";
                    System.out.println("                 拒绝成功");
                } else {
                    System.out.println("                即将返回...");
                    break;
                }
                Thread.sleep(1000);
            }
        }
        new ObjectOutputStream(socket.getOutputStream()).writeObject(back);
    }

    private void inviteTnGroup() throws Exception {
        String groupId;
        String userId;
        {
            while (true) {
                System.out.println("     请输入您要邀请加入的群聊的账号(或输入&退出)");
                System.out.println("             前提:您必须是该群的群成员");
                groupId = Input.getImporter().next();
                if (groupId.equals("&")) {
                    System.out.println("                 取消成功");
                    Thread.sleep(1000);
                    return;
                } else if (!groupId.matches("[1-9][\\d]{7}")) {
                    System.out.println("          群号错误，请重新输入！");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.println("       请输入您邀请的人的账号(或输入&退出)");
                System.out.println("           前提:对方必须是您的好友");
                userId = Input.getImporter().next();
                if (userId.equals("&")) {
                    System.out.println("                 取消成功");
                    Thread.sleep(1000);
                    return;
                } else if (!userId.matches("[1-9][\\d]{9}")) {
                    System.out.println("          帐号错误，请重新输入！");
                } else {
                    break;
                }
            }
        }
        new DataOutputStream(socket.getOutputStream()).writeInt(9);
        new DataOutputStream(socket.getOutputStream()).writeUTF(userId + " : " + groupId);
        System.out.println(new DataInputStream(socket.getInputStream()).readUTF());
        Thread.sleep(1500);
    }

    private void createGroup() throws Exception {
        String groupName;
        System.out.println(" 请为您的群设置一个名称（名称不能为空,不能包含空格，或者输入&取消创建）");
        groupName = Input.getImporter().next();
        if (groupName.equals("&")) {
            System.out.println("                 取消成功");
            Thread.sleep(1000);
            return;
        }
        new DataOutputStream(socket.getOutputStream()).writeInt(6);
        new DataOutputStream(socket.getOutputStream()).writeUTF(groupName);
        String groupId = new DataInputStream(socket.getInputStream()).readUTF();
        System.out.println("                创建成功！");
        System.out.println("      群名 : " + groupName + " 群号 " + groupId);
        System.out.println("              即将返回......");
        Thread.sleep(3000);
    }

    private void checkFriend() throws Exception {
        new DataOutputStream(socket.getOutputStream()).writeInt(3);
        String[] applicationsOfFriend  = (String[]) new ObjectInputStream(this.socket.getInputStream()).readObject();
        String[] back = new String[applicationsOfFriend.length];
        int size = 0;
        if (applicationsOfFriend.length == 0) {
            System.out.println("             没有收到好友申请");
        } else {
            for (String application : applicationsOfFriend) {
                System.out.println("            您收到的好友申请如下：");
                System.out.println    ("申请人昵称     申请人账号    申请人在线状态");
                System.out.println(application);
                System.out.println("是否同意该人的好友申请？（1.同意  0.拒绝  2.返回）请输入：");
                String order;
                while (true) {
                    order = Input.getImporter().next();
                    if (order.matches("[0-2]")) {
                        break;
                    } else {
                        System.out.println("  输入有误，请重新输入（1.同意  0.拒绝  2.返回）");
                    }
                }
                if (order.equals("1")) {
                    back[size ++] = application.split(" : ")[1] + "+1";
                    System.out.println("                 添加成功");
                } else if (order.equals("0")) {
                    back[size ++] =  application.split(" : ")[1] + "+0";
                    System.out.println("                 拒绝成功");
                } else {
                    System.out.println("                即将返回...");
                    break;
                }
                Thread.sleep(500);
            }
        }
        new ObjectOutputStream(socket.getOutputStream()).writeObject(back);
        Thread.sleep(1500);
        Menu.cls();
    }

    private void checkGroup() throws Exception {
        new DataOutputStream(socket.getOutputStream()).writeInt(8);
        String[] applications  = (String[]) new ObjectInputStream(this.socket.getInputStream()).readObject();
        String[] result = new String[applications.length];
        int size = 0;
        if (applications.length == 0) {
            System.out.println("              没有收到入群申请");
        } else {
            for (String application : applications) {
                System.out.println("            您收到的入群申请如下：");
                System.out.println    (" 申请人账号    申请加入的群号");
                System.out.println(application);
                System.out.println("是否同意该人的入群申请？（1.同意  0.拒绝  2.返回）请输入：");
                String order;
                while (true) {
                    order = Input.getImporter().next();
                    if (order.matches("[0-2]")) {
                        break;
                    } else {
                        System.out.println("  输入有误，请重新输入（1.同意  0.拒绝  2.返回）");
                    }
                }
                if (order.equals("1")) {
                    result[size ++] = application + " : 1";
                    System.out.println("                 同意成功");
                } else if (order.equals("0")) {
                    result[size ++] =  application + " : 0";
                    System.out.println("                 拒绝成功");
                } else {
                    System.out.println("                即将返回...");
                    break;
                }
                Thread.sleep(1000);
            }
        }
        new ObjectOutputStream(socket.getOutputStream()).writeObject(result);
        Thread.sleep(1500);
        Menu.cls();
    }

    private void sendGroupApplication() throws Exception {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String group;
        while (true) {
            System.out.println("            请输入群号(或者输入&退出)：");
            group = Input.getImporter().next();
            if (group.equals("&"))
                break;
            if (!group.matches("[1-9][\\d]{7}")) {
                System.out.println("            群号错误，请重新输入(或者输入&退出)：");
            } else {
                dos.writeInt(7);
                dos.writeUTF(group);
                System.out.println(dis.readUTF());
                Thread.sleep(1000);
                break;
            }
        }
    }

    private void sendFriendApplication() throws Exception {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String friend;
        while (true) {
            System.out.println("            请输入对方账号(或者输入&退出)：");
            friend = Input.getImporter().next();
            if (friend.equals("&"))
                break;
            if (!friend.matches("[1-9][\\d]{9}")) {
                System.out.println("            账号错误，请重新输入(或者输入&退出)：");
            } else if (friend.equals(this.userId)) {
                System.out.println("              该账号是您本人的账号！");
            } else {
                dos.writeInt(2);
                dos.writeUTF(friend);
                System.out.println(dis.readUTF());
                Thread.sleep(1000);
                break;
            }
        }
    }

    private void showMyFriends() throws Exception {
        String[] friends = new String[0];
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(1);
        Object o = new ObjectInputStream(this.socket.getInputStream()).readObject();
        if (o instanceof String[]) {
            friends = (String[])o;
        }
        if (friends.length != 0) {
            System.out.println    ("好友号  ：  好友名    好友账号  好友在线状态");
            for (int i = 0; i < friends.length; i++) {
                System.out.println("第" + i + "位好友： " +friends[i]);
            }
            while (true) {
                System.out.println("                请输入&返回");
                String key = Input.getImporter().next();
                if (key.equals("&"))
                    break;
                else
                    System.out.println("            输入有误，请重新输入");
            }
        } else {
            System.out.println("              您未添加任何好友");
            Thread.sleep(1000);
        }
    }

    private void showMyGroups() throws Exception {
        String[] groups = new String[0];
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(5);
        Object o = new ObjectInputStream(this.socket.getInputStream()).readObject();
        if (o instanceof String[]) {
            groups = (String[])o;
        }
        if (groups.length != 0) {
            System.out.println    ("群聊名称  ：  群聊号   ");
            for (int i = 0; i < groups.length; i++) {
                System.out.println("第 " + i + " 个群聊 : " +groups[i]);
            }
            while (true) {
                System.out.println("                请输入&返回");
                String key = Input.getImporter().next();
                if (key.equals("&"))
                    break;
                else
                    System.out.println("            输入有误，请重新输入");
            }
        } else {
            System.out.println("              您未添加任何群聊");
            Thread.sleep(1000);
        }
    }

    private boolean outLogin() throws Exception {
        new DataOutputStream(socket.getOutputStream()).writeInt(0);
        System.out.println("                   退出成功 ！");
        Thread.sleep(1500);
        return false;
    }

    private int getOrder() {
        String order = Input.getImporter().next();
        while (!order.matches("[0-9]") && !order.equals("10") && !order.equals("11") && !order.equals("12")) {
            System.out.println("             输入有误，请重新输入:");
            order = Input.getImporter().next();
        }
        return Integer.parseInt(order);
    }
}