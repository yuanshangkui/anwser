import java.io.*;
import java.util.LinkedHashMap;

public class Park {
    private int transactionID = 1;
    private LinkedHashMap<Car,Transaction> transactions = new LinkedHashMap<>();
    private boolean status = false;
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    private Park(){

    }
    private static Park instance = new Park();
    public static Park getInstance(){
        return instance;
    }
    public void enterPark(Car car){
        Transaction transaction = new Transaction(transactionID++,car);
        transactions.put(car,transaction);
    }

    public LinkedHashMap<Car, Transaction> getTransactions() {
        return transactions;
    }

    public void leavePark(Car car){
        Transaction transaction = transactions.get(car);
        System.out.println(transaction.leavePark());
        printTransaction(transaction);
        transactions.remove(car);
    }
    private void printTransaction(Transaction transaction){
        File file = new File("transactions.txt");
        FileOutputStream fout= null;
        try {
            fout = new FileOutputStream(file,true);
            byte[] bytes = transaction.toString().getBytes();
            fout.write(bytes);
        } catch (Exception e) {
            System.out.println("订单增加到文件失败");
            e.printStackTrace();
        }finally {
            if (fout != null){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void serializable(){
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try{
            fout = new FileOutputStream("TranscactionID.txt");
            fout.write((transactionID+"*").getBytes());
            oos = new ObjectOutputStream(new FileOutputStream("ParkStatus.txt"));
            oos.writeObject(transactions);
            oos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fout != null){
                    fout.close();
                }
                if(oos != null){
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void deserializable(){
        boolean isNumber = true;
        File file = new File("TranscactionID.txt");
        if(!file.exists()) return;
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        char ch;
        StringBuilder transactionID = new StringBuilder();
        try{
            fin = new FileInputStream(file);
            while((ch=(char)fin.read())!='*'&&fin.available()!=0){
                if('0'>ch||ch>'9') {
                    isNumber = false;
                    break;
                }
                transactionID.append(ch);
            }
            if(ch == '*' && isNumber){
                this.transactionID = Integer.parseInt(transactionID.toString());
            }
            fin = new FileInputStream("ParkStatus.txt");
            if(fin.available() == 0){
                return;
            }
            ois = new ObjectInputStream(fin);
            this.transactions = (LinkedHashMap<Car, Transaction>) ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (fin != null){
                    fin.close();;
                }
                if(ois != null){
                    ois.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
