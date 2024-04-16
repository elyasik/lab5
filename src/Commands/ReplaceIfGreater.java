package Commands;

import Data.Vehicle;

import java.util.Comparator;

import static Reciever.VehicleCollection.collection;


public class ReplaceIfGreater implements Command {
    @Override
    public void execute() {

    }

    @Override
    public String descr() {
        return "replace_if_greater <key> <element> : replace meaning by the key in case the new one is greater";
    }
    private static final Comparator<Vehicle> enginePowerComparator = Comparator.comparing(Vehicle::getEnginePower);
    public void sortCollectionByEnginePower() {
        collection.values().stream()
                .sorted(enginePowerComparator)
                .forEach(System.out::println);
    }
    public static void replaceIfGreater(Long key, Vehicle newVehicle) {
        System.out.println(key);
        if (collection.containsKey(key)) {
            Vehicle existingVehicle = collection.get(key);
            if (enginePowerComparator.compare(newVehicle, existingVehicle) > 0) {
                collection.put(key, newVehicle);
                System.out.println("Replaced successfully.");
            } else {
                System.out.println("New meaning does not grater the previous one.");
            }
        } else {
            System.out.println("An element with specified key is not found.");
        }
    }}
