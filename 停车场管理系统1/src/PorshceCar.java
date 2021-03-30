public class PorshceCar extends Car {
    //保时捷
    public PorshceCar(String number) {
        super(number);
    }

    @Override
    protected String initBrand() {
        return "保时捷";
    }

    @Override
    protected String initModel() {
        return "911";
    }
}
