import java.io.Serializable;

public class HonQi extends Car implements Serializable {
    public HonQi(String number) {
        super(number);
    }

    @Override
    protected String initBrand() {
        return "红旗";
    }

    @Override
    protected String initModel() {
        return "红旗H7 1.8T";
    }
}
