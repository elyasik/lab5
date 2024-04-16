package Commands;

import Data.Vehicle;

import java.util.Comparator;

import static Reciever.VehicleCollection.collection;

public class PrintFieldAscendingNumberOfWheels implements Command{
    @Override
    public String descr() {
        return "print_field_ascending_number_of_wheels : print meanings of field numberOfWheels of all elements from the lowerst to the greatest ";
    }

    @Override
    public void execute() {

    }
    public static void printFieldAscendingNumberOfWheels() {
        // Вывести значения поля numberOfWheels всех элементов в порядке возрастания
        collection.values().stream()
                .sorted(Comparator.comparingInt(Vehicle::getNumberOfWheels))
                .map(v -> v.getNumberOfWheels())
                .forEach(System.out::println);
    }
}
