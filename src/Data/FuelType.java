package Data;
public enum FuelType {
    ELECTRICITY,
    DIESEL,
    MANPOWER,
    PLASMA;
    public static FuelType tryParse(String value) {
        try {
            return FuelType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid FuelType: " + value);
            return null;
        }
    }
}

