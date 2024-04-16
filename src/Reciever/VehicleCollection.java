package Reciever;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.LinkedHashMap;
import Data.*;
public class VehicleCollection {
    public static final LinkedHashMap<Long, Vehicle> collection = new LinkedHashMap<>();
    private java.time.ZonedDateTime creationDate;
    public VehicleCollection() {
        this.creationDate = ZonedDateTime.now();
    }

    public void put(long key, Vehicle vehicle) {
        collection.put(key, vehicle);
    }
    public int size() {
        return collection.size();
    }


    private static final Comparator<Vehicle> enginePowerComparator = Comparator.comparing(Vehicle::getEnginePower);
    public void sortCollectionByEnginePower() {
        collection.values().stream()
                .sorted(enginePowerComparator)
                .forEach(System.out::println);
    }
    public static Long getMaxIdFromCollection() {
        Long maxId = null;

        for (Map.Entry<Long, Vehicle> entry : collection.entrySet()) {
            Long id = entry.getKey();
            if (maxId == null || id > maxId) {
                maxId = id;
            }
        }

        return maxId;
    }

    private static FuelType tryParseFuelType(String value) {
        try {
            return FuelType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid FuelType: " + value);
            return null;
        }
    }

    private Long getKeyByValue(Hashtable<Long, Vehicle> map, Vehicle value) {
        for (Map.Entry<Long, Vehicle> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public VehicleCollection(String fileName) {
        readFromFile(fileName);
    }

    private void readFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Vehicle vehicle = parseCsvLine(line);

                if (vehicle != null) {
                    collection.put(vehicle.getId(), vehicle);
                    Vehicle.setNextId(Vehicle.getNextId()+1);
                } else {
                    System.out.println("Failed to parse line: " + line); // Отладочный вывод
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Vehicle parseCsvLine(String line) {
        String[] parts = line.split(",");

        if (parts.length < 8) {
            System.out.println("Invalid CSV line: " + line);
            System.out.println(""+parts[0]);
            // Handle or log the issue as needed, and return a default vehicle
            return new Vehicle( "Invalid Vehicle", new Coordinates(0.0, 0.0F), new Date(), 0.0F, 0, null, FuelType.ELECTRICITY);
        }

        try {
            Long id = Long.parseLong(parts[0].trim());
            String name = parts[1].trim();
            Coordinates coordinates = new Coordinates(Double.parseDouble(parts[2].trim()), Float.parseFloat(parts[3].trim()));
            Date date = new Date();

            Float enginePower;
            try {
                enginePower = Float.parseFloat(parts[4].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid enginePower: " + parts[4].trim());
                enginePower = 0.0F; // Set a default value or handle it accordingly
            }

            int numberOfWheels;
            try {
                numberOfWheels = Integer.parseInt(parts[5].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid numberOfWheels: " + parts[5].trim());
                numberOfWheels = 0; // Set a default value or handle it accordingly
            }

            Long capacity;
            try {
                capacity = parts[6].trim().isEmpty() ? null : Long.parseLong(parts[6].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid capacity: " + parts[6].trim());
                capacity = null; // Set a default value or handle it accordingly
            }

            FuelType fuelType;
            try {
                fuelType = FuelType.valueOf(parts[7].trim());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid FuelType: " + parts[7].trim());
                fuelType = FuelType.ELECTRICITY; // Set a default value or handle it accordingly
            }

            return new Vehicle( name, coordinates, date, enginePower, numberOfWheels, capacity, fuelType);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing line: " + line);
            return new Vehicle("Invalid Vehicle", new Coordinates(0.0, 0.0F), new Date(), 0.0F, 0, null, FuelType.ELECTRICITY);
        }
    }


    // Helper method to parse Float, handling invalid cases
    private Float tryParseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid value for Float: " + value);
            return 0.0F; // Set a default value or handle it accordingly
        }
    }

    // Helper method to parse Integer, handling invalid cases
    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid value for Integer: " + value);
            return 0; // Set a default value or handle it accordingly
        }
    }


    // Interactive commands

    public void help() {
        System.out.println("Available Commands:");
        System.out.println("help : Display available commands");
        System.out.println("info : Display information about the collection");
        System.out.println("show : Display all elements in the collection");
        System.out.println("insert null {element} : Add a new element with the specified key");
        System.out.println("update id {element} : Update the value of a collection element");
        System.out.println("remove_key null : Remove an element from the collection by its key");
        System.out.println("clear : Clear the collection");
        System.out.println("save : Save the collection to a file");
        System.out.println("execute_script file_name : Read and execute commands from a script file");
        System.out.println("exit : Terminate the program");
        System.out.println("remove_lower {element} : Remove all elements from the collection that are less than the specified one");
        System.out.println("replace_if_greater null {element} : Replace the value by key if the new value is greater than the old one");
        System.out.println("remove_lower_key null : Remove all elements from the collection whose key is less than the specified one");
        System.out.println("remove_any_by_fuel_type fuelType : Remove one element from the collection whose fuelType field is equivalent to the specified one");
        System.out.println("count_by_capacity capacity : Display the number of elements whose capacity field is equal to the specified one");
        System.out.println("print_field_ascending_number_of_wheels : Display values of the numberOfWheels field of all elements in ascending order");

        // Add descriptions for other commands
    }

    public void info() {
        System.out.println("Collection Type: Hashtable");
        System.out.println("Initialization Date: " + new Date());
        System.out.println("Number of Elements: " + collection.size());
    }

    public void show() {
        for (Vehicle vehicle : collection.values()) {
            System.out.println(vehicle);
        }
    }

    public void update(Long id, Vehicle newVehicle) {
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
    private String convertToCsv(Vehicle vehicle) {
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
    private void updateFile(String fileName) {
        // Считываем текущий файл
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Обновляем данные в памяти
        for (Vehicle vehicle : collection.values()) {
            if (vehicle.getId() != -1L) { // Exclude the default vehicle
                String csvLine = convertToCsv(vehicle);
                boolean updated = false;
                for (int i = 0; i < fileContent.size(); i++) {
                    if (fileContent.get(i).startsWith(vehicle.getId().toString())) {
                        fileContent.set(i, csvLine);
                        updated = true;
                        break;
                    }
                }
                if (!updated) {
                    fileContent.add(csvLine);
                }
            }
        }

        // Перезаписываем файл
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String line : fileContent) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clear() {
        collection.clear();
        Vehicle.setNextId(0);
        System.out.println("Collection cleared.");
    }

    // Utility method to parse vehicle input from the user
    private static Vehicle parseVehicleInput(Scanner scanner) {

        System.out.print("Enter vehicle name: ");
        String name = scanner.next();

        System.out.print("Enter vehicle coordinates (x y): ");
        Double x = scanner.nextDouble();
        Float y = scanner.nextFloat();
        Coordinates coordinates = new Coordinates(x, y);

        System.out.print("Enter engine power: ");
        Float enginePower = scanner.nextFloat();

        System.out.print("Enter number of wheels: ");
        int numberOfWheels = scanner.nextInt();

        System.out.print("Enter capacity: ");
        Long capacity;
        String capacityInput = scanner.next();
        if (capacityInput.equals("null")) {
            capacity = null;
        } else {
            capacity = Long.parseLong(capacityInput);
        }

        System.out.print("Enter fuel type: ");
        FuelType fuelType;
        try {
            fuelType = FuelType.valueOf(scanner.next().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid FuelType. Using default value ELECTRICITY.");
            fuelType = FuelType.ELECTRICITY;
        }

        return new Vehicle(name, coordinates, new Date(), enginePower, numberOfWheels, capacity, fuelType);
    }

    public boolean containsKey(Long key) {
        return collection.containsKey(key);
    }

    public Iterable<? extends Vehicle> values() {
        return new ArrayList<>(collection.values());
    }
}