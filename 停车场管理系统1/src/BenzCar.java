public class BenzCar extends Car {
    //奔驰 威霆
    public BenzCar(String number) {
        super(number);
    }

    @Override
    protected String initBrand() {
        return "奔驰";
    }

    @Override
    protected String initModel() {
        return "威霆";
    }
}
