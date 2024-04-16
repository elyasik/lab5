package Commands;

import Data.Vehicle;

import static Reciever.VehicleCollection.collection;

public class Clear implements Command {
    @Override
    public String descr() {
       return  "clear — clear the collection";
    }

    @Override
    public void execute() {

    }

    public static void clear() {
        collection.clear();
        Vehicle.setNextId(0);
        System.out.println("Collection cleared.");
    }
}
