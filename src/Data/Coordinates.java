package Data;
public class Coordinates {
    private Double x;
    private Float y;

    public Coordinates(Double x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Coordinates{x=%.2f, y=%.2f}", x, y);
    }

}
