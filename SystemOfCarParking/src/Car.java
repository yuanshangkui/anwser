import java.io.Serializable;
import java.util.Objects;

public abstract class Car implements Serializable {
    private static final long serialVersionUID = 12345678912345L;
    private String number;//车牌号
    private final String brand = initBrand();
    private final String model = initModel();

    public Car(String number) {
        this.number = number;
    }

    protected abstract String initBrand();

    protected abstract String initModel();

    public String getNumber() {
        return this.number;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    @Override
    public String toString() {
        return "[ " +
                "number : '" + number + '\'' +
                ", brand : '" + brand + '\'' +
                ", model : '" + model + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(number, car.number) &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, brand, model);
    }
}
