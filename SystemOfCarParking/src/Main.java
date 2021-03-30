import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("\n          ****欢迎使用停车场管理系统！****");
        System.out.println("\n              输入“e”退出系统\n");
        System.out.println("****************************************************\n");
        Park.park.readData();//载入数据

        Thread entrance = new Thread(new Entrance());
        Thread exit = new Thread(new Exit());

        entrance.setName("入口");
        exit.setName("出口");

        entrance.start();
        exit.start();

        Scanner input = new Scanner(System.in);
        while (!input.next().equals("e"));

        Park.park.saveData();//保存数据
        System.out.println("        ****欢迎再次使用停车场管理系统！****");
        System.exit(0);//结束进程
    }
}
