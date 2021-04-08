import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 6725132611555138815L;
    private static final int HOUR_PRICE = 5;
    private int number;
    private String enterTime;
    private String leaveTime;
    private int received;
    private Car car;

    public Transaction(int number, Car car) {
        this.number = number;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        enterTime = df.format(new Date());
        this.car = car;
    }




    public String leavePark(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        leaveTime=df.format(new Date());
        long enterTime;
        long leaveTime;
        int hours;
        long remains;
        try{
            enterTime = df.parse(this.enterTime).getTime();
            leaveTime = df.parse(this.leaveTime).getTime();
            hours = (int) (leaveTime - enterTime)/(1000 * 60 * 60);
            remains = (leaveTime - enterTime)%(1000 * 60 * 60);
            if(remains > 0) hours++;
            received = HOUR_PRICE * hours;
        } catch (ParseException e) {
            System.out.println("订单结账失败:"+car.toString());
            e.printStackTrace();
        }
        return this.toString();
    }
    @Override
    public String toString() {
        return "\n订单号：" + number +
                "\n入库时间：" + enterTime +
                "\n出库时间：" + leaveTime +
                "\n收费：" + received +
                "\n车辆：" + car;
    }
}
