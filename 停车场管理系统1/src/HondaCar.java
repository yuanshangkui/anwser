public class HondaCar extends Car{
    //本田 奥德赛  Honda Odyssey
    public HondaCar(String number) {
        super(number);
    }

    @Override
    protected String initBrand() {
        return "本田";
    }

    @Override
    protected String initModel() {
        return "奥德赛";
    }
}
