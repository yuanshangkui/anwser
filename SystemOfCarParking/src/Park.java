import java.io.*;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class Park implements Serializable {
    public static final double HOUR_PRICE = 6.66;
    public static final int MAX_NUMBER = 1000;
    public static int carNumber = 0;
    public static long TransactionNumber = 0;
    public static Park park = new Park();
    private LinkedHashSet<Transaction> massages = new LinkedHashSet<>();

    private Park() {}

    public void interPark() throws InterruptedException {
        Car car = getCar();
        if (carNumber < MAX_NUMBER) {
            if (massages.add(new Transaction(TransactionNumber,car))) {
                ++ carNumber ;
                System.out.println("《"
                        + Thread.currentThread().getName()
                        + "》: 入库成功！\n订单号 ："
                        + TransactionNumber ++
                        + "    " + car.toString()
                        + "\n");
            }
        } else {
            System.out.println("\n车辆 ："
                    + car.toString()
                    + "入库失败！车位已满,请稍等 ！");
            Thread.sleep(1000);
        }
    }

    public void outerPark() throws InterruptedException {
        if (carNumber > 0) {
            for (Transaction t : massages) {
                System.out.print("《"
                        + Thread.currentThread().getName()
                        + "》:出库成功！这是您的订单 : ");
                System.out.println(t.outPark());
                massages.remove(t);
                -- carNumber;
                break;
            }
        } else {
            Thread.sleep(1000);
        }
    }

    public void readData() {
        File file1 = new File("ParkingMassages.txt");
        if (file1.exists() && file1.length() != 0) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(file1));
                Object o = ois.readObject();
                if (o instanceof LinkedHashSet) {
                    massages =  (LinkedHashSet<Transaction>)o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            boolean isDelete = file1.delete();
        }

        File file2 = new File("NUMBER.txt");
        if (file2.exists() && file2.length() != 0) {
            try (Scanner input = new Scanner(file2)) {
                TransactionNumber = input.nextLong();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                boolean isCreated = file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveData() {
        if (massages.size() != 0) {
            ObjectOutputStream oos = null;
            File file = new File("ParkingMassages.txt");
            try {
                oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(massages);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        File file2 = new File("NUMBER.txt");
            try (PrintWriter pw = new PrintWriter(file2)) {
                pw.print(TransactionNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private Car getCar() {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < 2; i++) {
            sb.append((char) (65 + r.nextInt(27)));
        }
        for (int i = 0; i < 4; i++) {
            sb.append(r.nextInt(10));
        }
        String number = sb.toString();

        switch (r.nextInt(3))
        {
            case 0 :return new HonQi(number);
            case 1 :return new ChangAn(number);
        }
        return new Chery(number);
    }
}