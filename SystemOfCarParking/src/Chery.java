import java.io.Serializable;

public class Chery extends Car implements Serializable {
    public Chery(String number) {
        super(number);
    }

    @Override
    protected String initBrand() {
        return "奇瑞";
    }

    @Override
    protected String initModel() {
        return "艾瑞泽GX 冠军版";
    }
}
