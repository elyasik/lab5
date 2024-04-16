package Commands;

import Data.Vehicle;

import java.util.Date;

import static Reciever.VehicleCollection.collection;

public class Insert implements Command {
        public void execute() {}
        public String descr() {
            return "insert - add a new element with specified key)";
        }

    public static void insert(Long key, Vehicle vehicle) {
        if (key == null || vehicle == null) {
            System.out.println("Invalid parameters for insert command.");
            return;
        }

        if (collection.containsKey(key)) {
            System.out.println("An element with the specified key already exists.");
            return;
        }

        collection.put(key, vehicle);
        System.out.println("Element inserted successfully.");
    }

}
