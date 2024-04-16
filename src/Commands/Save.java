package Commands;
import Reciever.*;
import Data.Vehicle;

import java.io.FileWriter;
import java.io.IOException;

import static Reciever.VehicleCollection.collection;

public class Save implements Command {
    @Override
    public String descr() {
        return "save — save collection to CSV file";
    }

    @Override
    public void execute() {

    }
    public static void saveToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) { // Указываем флаг true для добавления данных в конец файла
            for (Vehicle vehicle : collection.values()) {
                if (vehicle.getId() != -1L) { // Исключаем объекты по умолчанию
                    String csvLine = convertToCsv(vehicle);
                    writer.write(csvLine + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String convertToCsv(Vehicle vehicle) {
        return String.format("%d,%s,%.2f,%.2f,%.2f,%d,%s,%s",
                vehicle.getId(),
                vehicle.getName(),
                vehicle.getCoordinates().getX(),
                vehicle.getCoordinates().getY(),
                vehicle.getEnginePower(),
                vehicle.getNumberOfWheels(),
                vehicle.getCapacity() != null ? vehicle.getCapacity() : "",
                vehicle.getFuelType().name());
    }
}
