import java.io.Serializable;

public class ChangAn extends Car implements Serializable {
    public ChangAn(String number) {
        super(number);
    }

    @Override
    protected String initBrand() {
        return "长安";
    }

    @Override
    protected String initModel() {
        return "CS55PLUS";
    }
}
