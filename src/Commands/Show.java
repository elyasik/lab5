package Commands;

import Data.Vehicle;

import static Reciever.VehicleCollection.collection;

public class Show implements Command {
    @Override
    public void execute() {

    }

    @Override
    public String descr() {
        return "show — elements of the collection";
    }
    public static void show() {
        for (Vehicle vehicle : collection.values()) {
            System.out.println(vehicle);
        }
    }
}
