package Data;
import java.util.Date;

public class Vehicle implements Comparable<Vehicle> {
    private static long nextId = 1;
    public static long getNextId() {
        return nextId;
    }
    public static void setNextId(long value) {
        nextId = value;
    }

    private Long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Float enginePower;
    private int numberOfWheels;
    private Long capacity;
    private FuelType fuelType;

    // Constructors, getters, setters
    // Дополнительный конструктор для установки значений для остальных полей
    public Vehicle(String name, Coordinates coordinates, Date creationDate,
                   Float enginePower, int numberOfWheels, Long capacity, FuelType fuelType) {
        this.id = getNextId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.capacity = capacity;
        this.fuelType = fuelType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Float getEnginePower() {
        return enginePower;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public Long getCapacity() {
        return capacity;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    // Добавим сеттеры для остальных полей
    public void setEnginePower(Float enginePower) {
        this.enginePower = enginePower;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    // Other methods

    @Override
    public String toString() {
        return String.format("Vehicle{id=%d, name='%s', coordinates=%s, creationDate=%s, enginePower=%s, numberOfWheels=%d, capacity=%s, fuelType=%s}",
                id, name, coordinates, creationDate, enginePower, numberOfWheels, capacity, fuelType);
    }
    public int compareTo(Vehicle other) {
        return Float.compare(this.enginePower, other.enginePower);
    }
}
