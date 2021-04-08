package client;

public class Menu {
    private Menu() {}

    public static void homePage(String userName, String userId) {
        cls();
        System.out.println("**************************************************");
        System.out.println("                  》 个人主页 《                    ");
        System.out.println("             用户名 :" + userName                  );
        System.out.println("             用户账号:" + userId                    );
        System.out.println("                   1.我的好友                      ");//11
        System.out.println("                   2.添加好友                      ");//11
        System.out.println("                   3.好友申请                      ");//11
        System.out.println("                   4.进行私聊                      ");//11
        System.out.println("                   5.我的群聊                      ");//11
        System.out.println("                   6.创建群聊                      ");//11
        System.out.println("                   7.加入群聊                      ");//11
        System.out.println("                   8.入群申请                      ");//11
        System.out.println("                   9.邀请入群                      ");//11
        System.out.println("                  10.入群邀请                      ");//11
        System.out.println("                  11.进行群聊                      ");//
        System.out.println("                  12.退出群聊                      ");//11
        System.out.println("                   0.退出登陆                      ");//11
        System.out.println("                                                  ");
        System.out.println("**************************************************");
    }

    public static void welcomeMenu() {
        cls();
        System.out.println("**************************************************");
        System.out.println("                                                  ");
        System.out.println("            》欢迎来到Communication的世界《           ");
        System.out.println("                                                  ");
        System.out.println("                    1.注册新账号                    ");
        System.out.println("                    2. 登录                        ");
        System.out.println("                    3. 退出                        ");
        System.out.println("                                                  ");
        System.out.println("**************************************************");
        System.out.println("                  请输入操作编号(1~3):               ");
    }

    public static void registerMenu() {
        cls();
        System.out.println("**************************************************");
        System.out.println("                                                  ");
        System.out.println("                      》注册《                      ");
        System.out.println("                                                  ");
        System.out.println(" (密码由6-12位的数字,字母以及下划线组成，不要输错了哦！)     ");
        System.out.println("           请输入您的密码(或者输入'&'取消注册):         ");
    }

    public static void loginMenu() {
        cls();
        System.out.println("**************************************************");
        System.out.println("                     》登录《                      ");
        System.out.println("                   1.快捷登录                      ");
        System.out.println("                   2.账号密码登录                   ");
        System.out.println("                   3.返回主页面                     ");
        System.out.println("                 请输入操作编号(1~3):               ");
    }

    public static void exit() {
        cls();
        System.out.println("**************************************************");
        System.out.println("                                                  ");
        System.out.println("         退出成功！欢迎再次使用Communication           ");
        System.out.println("                                                  ");
        System.out.println("**************************************************");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清屏
     */
    public static void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
