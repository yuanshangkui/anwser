import java.io.*;
import java.util.Date;

public class Transaction implements Serializable{
    private final long id;
    private final Date inTime;
    private Date outTime;
    private double cost;
    private Car car;

    public Transaction(long id ,Car car) {
        this.car = car;
        this.id = id;
        this.inTime = new Date();
    }

    public long getId() {
        return id;
    }

    public String outPark() {
        this.outTime = new Date();
        int hour = (int) Math.ceil((outTime.getTime() - inTime.getTime()) * 1.0 / 3600 / 1000);
        this.cost = hour * Park.HOUR_PRICE;

        String receipt =
                  "\n--------------------------------------"
                + "\n订单号   : " + id
                + "\n入库时间 : " + inTime.toString()
                + "\n出库时间 : " + outTime.toString()
                + "\n车辆信息 : " + car.toString()
                + "\n收费    : " + cost + "元"
                + "\n--------------------------------------\n";

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File("TransactionMassages.txt"),"rw");
            byte[] bytes = new byte[128];
            raf.seek(raf.length());
            raf.write(receipt.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return receipt;
    }
}
