package Commands;

import Data.Vehicle;

import static Reciever.VehicleCollection.collection;

public class RemoveLower implements Command {
    @Override
    public void execute() {

    }

    @Override
    public String descr() {
        return "remove_lower <element> : remove elements which engine power is lower";
    }
    public static void removeLower(Vehicle vehicle) {
        if (vehicle == null) {
            System.out.println("Invalid vehicle for removeLower command.");
            return;
        }

        collection.values().removeIf(v -> v != null && v.compareTo(vehicle) < 0);
    }
}
