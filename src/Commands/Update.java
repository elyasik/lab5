package Commands;

import Data.Vehicle;

import static Reciever.VehicleCollection.collection;

public class Update implements Command {

    public String descr() {
        return "update <id> <element> : renew meaning of the element with specified id";
    }


    public void execute() {

    }
    public static void update(Long id, Vehicle newVehicle) {
        Vehicle.setNextId(id);
        if (id == null || newVehicle == null) {
            System.out.println("Invalid parameters for update command.");
            return;
        }

        if (collection.containsKey(id)) {
            collection.put(id, newVehicle);;
            System.out.println("Element updated successfully.");
        } else {
            System.out.println("Element with the specified id not found.");
        }
    }
}
