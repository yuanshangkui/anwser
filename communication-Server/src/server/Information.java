package server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Information implements Comparable<Date>{
    private String from;
    private Date time;
    private String content;
    private static final SimpleDateFormat formatTime = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");

    public Information(String from, Date time, String content) {
        this.from = from;
        this.time = time;
        this.content = content;
    }

    @Override
    public String toString() {
        return "\n" + formatTime.format(time) + "\n"
                +from + " : " + content + "\n";
    }

    @Override
    public int compareTo(Date date) {
        return this.time.compareTo(date);
    }
}
