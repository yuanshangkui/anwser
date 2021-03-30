import java.io.Serializable;
import java.util.Objects;

public abstract class Car implements Serializable {
    private static final long serialVersionUID = -6039325800866438027L;
    private String number;
    private final String brand = initBrand();
    private final String model = initModel();
    public Car(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    protected abstract String initBrand();
    protected abstract String initModel();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return number.equals(car.number) &&
                brand.equals(car.brand) &&
                model.equals(car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, brand, model);
    }

    @Override
    public String toString() {
        String name;
        name =brand +" " + model + " - " + number;
        return name;
    }
}