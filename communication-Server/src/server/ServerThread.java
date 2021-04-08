package server;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Queue;

public class ServerThread implements Runnable{
    private Socket socket;
    private Account user = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int order = new DataInputStream(socket.getInputStream()).readInt();//指令
                if (this.user != null) System.out.print(this.user.getUserName());//==================================
                System.out.print(" 指令 ");//===========================================================================
                switch (order)
                {
                    case -1 ://注册 用户名+密码
                        System.out.println("注册");
                        register();
                        break;
                    case -2 ://登陆 账户+密码
                        System.out.println("登录");
                        login();
                        break;
                    case -3 ://退出程序
                        return;
                    case 0 ://退出登录
                        System.out.println("退出登录");
                        exitLogin();
                        break;
                    case 1 : //查看好友
                        System.out.println("查看好友");
                        sendFriendsList();
                        break;
                    case 2 : //发送好友申请
                        System.out.println("发送好友申请");
                        FriendApplication();
                        break;
                    case 3 : //好友审核
                        System.out.println("好友审核");
                        checkFriend();
                        break;
                    case 4 : //私聊
                        System.out.println("私聊");
                        personalChat();
                        break;
                    case 5 : //我的群聊
                        System.out.println("我的群聊");
                        sendGroupList();
                        break;
                    case 6 : //创建群聊
                        System.out.println("创建群聊");
                        createGroup();
                        break;
                    case 7 : //申请入群
                        System.out.println("申请入群");
                        GroupApplication();
                        break;
                    case 8 : //审核入群申请
                        System.out.println("审核入群");
                        checkGroup();
                        break;
                    case 9 : //邀请入群
                        System.out.println("邀请入群");
                        inviteTnGroup();
                        break;
                    case 10 ://处理入群邀请
                        System.out.println("处理入群申请");
                        dealGroupInvitations();
                        break;
                    case 11 ://群聊
                        System.out.println("请求群聊");
                        groupChat();
                        break;
                    case 12 ://退群
                        System.out.println("退群");
                        outGroup();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void groupChat() throws Exception {
        String groupId = new DataInputStream(socket.getInputStream()).readUTF();
        System.out.println(this.user.toString() + " 请求参与群聊 " + groupId);//===================================================
        String result;
        Group g = null;
        if (!Server.userBank.containGroup(groupId)) {
            result = "            群聊不存在";
        } else if ((g = this.user.getGroup(groupId)) == null) {
            result = "          您还未加入该群";
        } else {
            result = g.toString();
        }
        new DataOutputStream(socket.getOutputStream()).writeUTF(result);
        if (result.contains(" : ")) {
            SendInformation sendThread = new SendInformation(socket,this.user.getGroupReceiveQueue(groupId));
            sendThread.start();

            String massage;
            //将接收到的该用户的消息，存入对方用于的该用户的存储消息的队列
            while (!(massage = new DataInputStream(socket.getInputStream()).readUTF()).equals("&")) {
                g.sendGroupInformation(this.user.getUserId() , new Information(this.user.getUserName(),new Date(),massage));
            }
            sendThread.killThread();
        }
    }

    private void personalChat() throws IOException {
        String friendId = new DataInputStream(socket.getInputStream()).readUTF();
        System.out.println(this.user.toString() + " 请求与 " + friendId + " 聊天");//===================================================
        String result;
        Account friend;
        if (!Server.userBank.containAccount(friendId)) {
            result = "            用户不存在";
        } else if ((friend = this.user.getFriend(friendId)) == null) {
            result = "        您与该用户还不是好友";
        } else {
            result = friend.toString();
        }
        new DataOutputStream(socket.getOutputStream()).writeUTF(result);
        if (result.contains(" : ")) {

            //将好友的消息发送给该用户
            //取出该用户中，存储对方消息的队列
            SendInformation sendThread = new SendInformation(this.socket,this.user.getFriendReceiveQueue(friendId));
            sendThread.start();

            String massage;
            //将接收到的该用户的消息，存入对方用于的该用户的存储消息的队列
            Queue<Information> friendQueue = this.user.getFriendSendQueue(friendId);
            while (!(massage = new DataInputStream(socket.getInputStream()).readUTF()).equals("&")) {
                friendQueue.offer(new Information(this.user.getUserName(),new Date(),massage));
            }
            sendThread.killThread();
        }
    }

    private void outGroup() throws Exception {
        String groupId = new DataInputStream(socket.getInputStream()).readUTF();
        String result;
        if (Server.userBank.containGroup(groupId)) {
            result = "          该群不存在 ";
        } else if (!this.user.containGroup(groupId)) {
            result = "          您未加入该群 ";
        } else {
            result = "           退出成功 ";
            Group g = Server.userBank.getGroup(groupId);
            this.user.outGroup(g);
            g.leaveGroup(this.user);
            System.out.println(this.user.toString() + " 退出群 " + groupId);
        }
        new DataOutputStream(socket.getOutputStream()).writeUTF(result);
    }

    private void dealGroupInvitations() throws Exception {
        new ObjectOutputStream(socket.getOutputStream()).writeObject(this.user.getGroupInvitationToString());
        String[] result = (String[]) new ObjectInputStream(socket.getInputStream()).readObject();
        for (int i = 0; i < result.length && result[i] != null; i++) {
            String[] contents = result[i].split(" : ");
            if (contents[2].equals("1")) {
                Group g = Server.userBank.getGroup(contents[1]);
                g.getLeader().addGroupApplicant(this.user.getUserId() + " : " + contents[1]);
            }
            this.user.removeGroupInvitation(contents[0] + " : " + contents[1]);
        }
    }

    private void inviteTnGroup() throws IOException {
        //用户账号 ： 群号
        String[] s = new DataInputStream(socket.getInputStream()).readUTF().split(" : ");
        String userId = s[0];
        String groupId = s[1];
        Account user = Server.userBank.getAccount(s[0]);
        Group group = Server.userBank.getGroup(s[1]);
        String result;
        if (user == null) {
            result = "          邀请失败，用户不存在！";
        } else if (!this.user.containFriend(userId)) {
            result = "        邀请失败，您与该用户不是好友";
        } else if (group == null) {
            result = "         邀请失败，该群不存在";
        } else if (!this.user.containGroup(groupId)) {
            result = "         邀请失败，您不在该群";
        } else if (group.getMember(userId) != null) {
            result = "            该成员已在群内";
        } else {
            result = "         邀请成功，等待对方处理！";
            user.addGroupInvitation(this.user.getUserId() + " : " + groupId);
        }
        System.out.println(this.user.toString() + " 邀请 " + userId + " 加入群 " + groupId + "\n" + result);//=============================================
        new DataOutputStream(socket.getOutputStream()).writeUTF(result);
    }

    private void checkGroup() throws Exception {
        new ObjectOutputStream(socket.getOutputStream()).writeObject(this.user.getAddGroupApplicationToString());
        String[] result = (String[])new ObjectInputStream(socket.getInputStream()).readObject();
        for (int i = 0; i < result.length && result[i] != null; i++) {
            String[] massage = result[i].split(" : ");
            String applicantId = massage[0];
            String groupId = massage[1];
            String isAdd = massage[2];
            if (isAdd.equals("1")) {
                Group g = Server.userBank.getGroup(groupId);
                Account a = Server.userBank.getAccount(applicantId);
                g.interGroup(a);
                a.addGroup(g);
                System.out.println(a.toString() + " 成功添加群 " + g.toString() );//====================================
            }
            this.user.removeGroupApplicant(applicantId + " : " + groupId);
        }
    }

    private void checkFriend() throws Exception{
        new ObjectOutputStream(socket.getOutputStream()).writeObject(this.user.getFriendApplicantsToString());
        String[] result = (String[])new ObjectInputStream(socket.getInputStream()).readObject();
        for (int i = 0; i < result.length && result[i] != null; i++) {
            String id = result[i].split("[+]")[0];
            String isAdd = result[i].split("[+]")[1];
            Account applicant = Server.userBank.getAccount(id);
            if (isAdd.equals("1")) {
                this.user.addFriend(applicant);
                applicant.addFriend(this.user);
                System.out.println(this.user.toString() + " 成功添加 " + id + "为好友");//====================================
            }
            this.user.removeFriendApplicant(applicant);
        }
    }

    private void createGroup() throws IOException {
        String groupName = new DataInputStream(socket.getInputStream()).readUTF();
        System.out.println("客户端请求建立群" + groupName);//===========================================================
        String groupId = Server.userBank.createGroupId();
        Server.userBank.addGroup(groupName,groupId,this.user);
        System.out.println(this.user.toString() + " 建立群 " + groupName + "  " + groupId);//===========================================================
        new DataOutputStream(socket.getOutputStream()).writeUTF(groupId);
    }

    private void GroupApplication() throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String groupId = dis.readUTF();
        Group g = Server.userBank.getGroup(groupId);
        System.out.println(this.user.toString() + "  请求添加群  " + groupId);//===================================
        String result;
        if (g == null) {
            result = "                该群不存在";
        } else if (this.user.containGroup(groupId)) {
            result = "             您已经加入该群了";
        } else if (g.getLeader().containGroupApplicant(this.user.getUserId() + " : " + groupId)) {
            result = "     您已经发送过加群申请了，等待群主同意";
        } else {
            g.getLeader().addGroupApplicant(this.user.getUserId() + " : " + groupId);
            result = "            消息已经发送，等待群主同意！";
        }
        dos.writeUTF(result);
    }

    private void FriendApplication() throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String id = dis.readUTF();
        System.out.println(this.user.toString() + "请求添加" + id + "为好友");//===================================
        String result;
        Account other = Server.userBank.getAccount(id);
        if (other == null) {
            result = "                该用户不存在";
        } else if (this.user.containFriend(id)) {
            result = "             您与该用户已经是好友了";
        } else if (other.containFriendApplicant(this.user.getUserId())) {
            result = "    您已经发送过好友申请了，等待对方回应";
        } else {
            other.addFriendApplicant(this.user);
            result = "            消息已经发送，等待对方同意！";
        }
        dos.writeUTF(result);
    }

    private void sendFriendsList() throws IOException {
        new ObjectOutputStream(socket.getOutputStream()).writeObject(this.user.getFriendsToString());
    }

    private void sendGroupList() throws IOException {
        new ObjectOutputStream(socket.getOutputStream()).writeObject(this.user.getGroupsToString());
    }

    private void exitLogin() {
        System.out.println(this.user.toString() + "退出登录了");//==========================================
        this.user.setOnline(false);
        this.user = null;
    }

    private void login() throws IOException {
        String id = new DataInputStream(socket.getInputStream()).readUTF();
        String password = new DataInputStream(socket.getInputStream()).readUTF();
        System.out.println("请求登录  " + id +"  " + password);////============================================
        int result = Server.userBank.canLogin(id,password);
        new DataOutputStream(socket.getOutputStream()).writeInt(result);
        if (result == 1) {
            System.out.println("登录成功");//==================================================================
            this.user = Server.userBank.getAccount(id);
            new DataOutputStream(socket.getOutputStream()).writeUTF(this.user.getUserName());
            new DataOutputStream(socket.getOutputStream()).writeUTF(this.user.getUserId());
        }
    }

    private void register() throws Exception {
        String name = new DataInputStream(socket.getInputStream()).readUTF();
        String password = new DataInputStream(socket.getInputStream()).readUTF();
        String id = Server.userBank.createAccountId();
        Server.userBank.addAccount(name,id,password);
        new DataOutputStream(socket.getOutputStream()).writeUTF(id);
        System.out.println("注册  用户名 ： " + name + " 账号 " + id + "  密码  " + password);//==============================================
    }

}
