import java.util.*;

public class Main {
    public static void main(String[] args) {
        Park park = Park.getInstance();
        park.deserializable();
        Enter enter = new Enter(park);
        Exit exit = new Exit(park);
        Thread t1 = new Thread(enter);
        Thread t2 = new Thread(exit);
        Scanner in = new Scanner(System.in);
        String str;
        park.setStatus(true);
        t1.start();
        t2.start();
        //
        while (true){
            str = in.next();
            if(str.equals("exit")||str.equals("e")){
                park.setStatus(false);
                break;
            }
        }
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        park.serializable();
        //保存停车场信息
    }
}
class Enter implements Runnable{
    private Park park;
    public Enter(Park park) {
        this.park = park;
    }
    @Override
    public void run() {
        int random;
        Car car;
        while (park.getStatus()){
            random = (int)(Math.random()*3)+1;
            switch (random){
                default:
                case 1:car = new BenzCar("湘A"+buildCarNumber());break;
                case 2:car = new PorshceCar("湘A"+buildCarNumber());break;
                case 3:car = new HondaCar("湘A"+buildCarNumber());break;
            }
            synchronized (park) {
                park.enterPark(car);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private String buildCarNumber(){
        int random;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <5 ; i++) {
            random = (int)(Math.random()*2)+1;
            switch (random){
                default:
                case 1:str.append((char)((int)(Math.random()*25)+65));break;
                case 2:str.append((char)((int)(Math.random()*9)+48));break;
            }
        }
        return str.toString();
    }
}
class Exit implements Runnable{
    private Park park;
    public Exit(Park park) {
        this.park = park;
    }
    @Override
    public void run() {
        Car car;
        while (park.getStatus()){
            car = getRandomKeyFromMap(park.getTransactions());
            if(car == null) continue;
            park.leavePark(car);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private Car getRandomKeyFromMap(Map<Car,Transaction> map) {
        int rn = (int)(Math.random()*map.size());
        int i = 0;
        synchronized (park) {
            for (Car car : map.keySet()) {
                if(i==rn){
                    return car;
                }
                i++;
            }
        }
        return null;
    }
}



/*
    1.什么是单例？怎么实现？
        一种一个类只创建一个对象的设计模式
        懒汉式 饿汉式
        懒汉式
        private static instance = null;
        if(instance == null){
            instance = new 构造器();
        }
        public getInstance(){
            return instance;
        }
        以上线程不安全，加线程锁可以安全，但线程锁会影响效率
        饿汉式
        private static instance = new 构造器();
        public getInstance(){
            return instance;
        }
    2.什么是多线程？怎样理解并发，并行？
        多线程：多个线程同时运行
        并发：宏观上的同一时间同时进行，微观上是片段式的执行，不同线程之间来回切换，达成一种宏观上的同时进行的效果
        并行：宏观与微观上都是同一时间同时进行
    3.怎样模拟只有一个出口一个入口？
        出口一个线程，入口一个线程
    4.怎样保证容器的线程安全？
        同步锁
    5.怎样将对象序列化到文件？怎样从文件中反序列化？
        实现Serializable接口，写出相应的方法
    6.怎样将字符串输出到文本文件？怎样从文本文件中读取？
        字节流、缓冲流等
    7.常量应该用什么关键字修饰？什么时候使用静态常量？
        final
        创建多个对象时，用static修饰常量可以减少消耗的内存空间
 */