package Commands;

import static Reciever.VehicleCollection.collection;

public class CountByCapacity implements Command {
    @Override
    public void execute() {

    }

    @Override
    public String descr() {
        return "count_by_capacity <capacity> — print number of elements with specified capacity (%d)";
    }
    public static void countByCapacity(Long capacity) {
        // Filter and count elements with the specified capacity or null
        long count = collection.values().stream()
                .filter(v -> (v.getCapacity() == null && capacity == null) || (v.getCapacity() != null && v.getCapacity().equals(capacity)))
                .count();

        System.out.println("Number of elements with the specified capacity or null: " + count);
    }
}
