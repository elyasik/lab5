package Commands;

import Data.FuelType;

import java.util.Map;
import java.util.Optional;

import static Reciever.VehicleCollection.collection;

public class RemoveAnyByFuelType implements Command {
    @Override
    public String descr() {
        return "remove any by fuel type - delete one element with specified field of FuelType";
    }

    @Override
    public void execute() {

    }
    public static void removeByKey(Long key) { collection.remove(key);
    }
    public static void removeAnyByFuelType(FuelType fuelType) {

        Optional<Long> key = collection.entrySet().stream()
                .filter(entry -> entry.getValue().getFuelType() == fuelType)
                .map(Map.Entry::getKey)
                .findFirst();

        key.ifPresent(RemoveAnyByFuelType::removeByKey);
    }
}
