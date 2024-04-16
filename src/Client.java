import java.util.Locale;
import java.util.Scanner;
import java.util.Date;
import java.io.*;

import Commands.*;
import Data.*;
import Reciever.*;

import static Commands.Info.*;

public class Client {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        if (args.length != 1) {
            System.out.println("Usage: java VehicleCollection <file_name>");
            System.exit(1);
        }
        String fileName = args[0];
        VehicleCollection vehicleCollection = new VehicleCollection(fileName);

        Scanner scanner = new Scanner(System.in);
        String command="";
        do {
            try {
                System.out.print("Enter command (type 'help' for available commands): ");
                command = scanner.nextLine().trim();
                String[] commandParts = command.split("\\s+");
                String action = commandParts[0];
                boolean commandRecognized = false; // Флаг, указывающий, была ли команда распознана

                switch (action) {
                    case "help":
                        Help.help();
                        System.out.println("Done successfully.");
                        commandRecognized = true;
                        break;
                    case "info":
                        Info.info();
                        System.out.println("Done successfully.");
                        commandRecognized = true;
                        break;
                    case "show":
                        Show.show();
                        System.out.println("Done successfully. If there is no information the collection is empty.");
                        commandRecognized = true;
                        break;
                    case "insert":
                        if (VehicleCollection.getMaxIdFromCollection()==null){
                            Vehicle.setNextId(1);
                        }
                        else {
                            Vehicle.setNextId(VehicleCollection.getMaxIdFromCollection()+1);
                        }
                        Vehicle newVehicle = parseVehicleInput(scanner);
                        Insert.insert(newVehicle.getId(), newVehicle);
                        commandRecognized = true;
                        break;
                    case "update":
                        Update.update(parseId(commandParts), parseVehicleInput(scanner));
                        commandRecognized = true;
                        break;
                    case "remove_key":
                        RemoveKey.removeByKeyCommand(scanner);
                        commandRecognized = true;
                        break;
                    case "clear":
                        Clear.clear();
                        commandRecognized = true;
                        break;
                    case "save":
                        Save.saveToFile(fileName);
                        System.out.println("Done successfully. Changes have been saved to the file.");
                        commandRecognized = true;
                        break;
                    case "execute_script":
                        executeExecuteScriptCommand(commandParts, vehicleCollection);
                        System.out.println("Done successfully.");
                        commandRecognized = true;
                        break;
                    case "remove_lower":
                        Vehicle inputVehicle = parseVehicleInput(scanner);
                        RemoveLower.removeLower(inputVehicle);
                        commandRecognized = true;
                        break;
                    case "replace_if_greater":
                        executeReplaceIfGreaterCommand(commandParts, vehicleCollection, scanner);
                        commandRecognized = true;
                        break;
                    case "remove_lower_key":
                        executeRemoveLowerKeyCommand(commandParts, vehicleCollection, scanner);
                        commandRecognized = true;
                        break;
                    case "remove_any_by_fuel_type":
                        executeRemoveAnyByFuelTypeCommand(commandParts, vehicleCollection, scanner);
                        commandRecognized = true;
                        break;
                    case "count_by_capacity":
                        executeCountByCapacityCommand(commandParts, vehicleCollection, scanner);
                        commandRecognized = true;
                        break;
                    case "print_field_ascending_number_of_wheels":
                        PrintFieldAscendingNumberOfWheels.printFieldAscendingNumberOfWheels();
                        commandRecognized = true;
                        break;
                    case "exit":
                        System.out.println("Exiting program.");
                        commandRecognized = true;
                        break;
                    case "":
                        System.out.println("\nWaiting for the command.");
                        commandRecognized = true;
                        break;
                    default:
                        commandRecognized = false;
                        System.out.println("");
                }
                if (!commandRecognized) {
                    System.out.println("Unknown command. Type 'help' for available commands. ");
                }


            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (!"exit".equalsIgnoreCase(command));

        Save.saveToFile(fileName);
    }
    private static Long parseId(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Invalid command format for id.");
            return null;
        }

        try {
            return Long.parseLong(commandParts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id format: " + commandParts[1]);
            return null;
        }
    }


    private static Vehicle parseVehicleInput(Scanner scanner) {


        System.out.print("Enter vehicle name: ");
        String name = scanner.nextLine().trim(); // Считываем всю строку и убираем лишние пробелы в начале и конце
        while (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please enter again:");
            name = scanner.nextLine().trim(); // Запрашиваем ввод снова
        }
        System.out.print("Enter vehicle coordinates (x y): ");

        // Переменные для хранения координат
        Double x = null;
        Float y = null;
        // Запрашиваем ввод и проверяем, являются ли введенные значения числами и не являются ли пустыми
        while (x == null || y == null) {
            // Проверяем, является ли ввод числом
            if (scanner.hasNextDouble()) {
                x = scanner.nextDouble();
            } else {
                System.out.println("Invalid input for x coordinate. Please enter a valid numbers for x and y:");
                scanner.nextLine(); // Пропускаем некорректный ввод
                continue; // Переходим к следующей итерации цикла
            }
            // Проверяем, является ли ввод числом
            if (scanner.hasNextFloat()) {
                y = scanner.nextFloat();
            } else {
                System.out.println("Invalid input for y coordinate. Please enter a valid numbers for x and y:");
                scanner.nextLine(); // Пропускаем некорректный ввод
                continue; // Переходим к следующей итерации цикла
            }
            // Проверяем, что оба введенных числа не равны нулю
            if (x == 0 || y == 0) {
                System.out.println("Coordinates cannot be zero. Please enter again:");
                x = null;
                y = null;
            }
        }
        Coordinates coordinates = new Coordinates(x, y);


        System.out.print("Enter engine power: ");

    // Переменная для хранения значения мощности двигателя
        Float enginePower = null;
    // Запрашиваем ввод и проверяем, является ли введенное значение числом и больше ли оно нуля
        while (true) {
            if (scanner.hasNext()) {
                String input = scanner.next();
                if (input.equalsIgnoreCase("null")) {
                    enginePower = null;
                    break;
                }
                try {
                    enginePower = Float.parseFloat(input);
                    if (enginePower <= 0) {
                        System.out.println("Engine power must not be negative. Please enter a valid number:");
                        continue; // Продолжаем цикл, если ввод некорректный
                    } else {
                        break; // Выходим из цикла, если ввод корректный
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for engine power. Please enter a valid number:");
                    continue; // Продолжаем цикл, если ввод некорректный
                }
            } else {
                // Если ввод пустой, выводим сообщение об ошибке и запрашиваем ввод снова
                System.out.println("Invalid input for engine power. Please enter a valid number:");
                scanner.nextLine(); // Пропускаем некорректный ввод
            }
        }



        System.out.print("Enter number of wheels: ");
        int numberOfWheels = 0;

        while (true) {
            if (scanner.hasNext()) {
                String input = scanner.next();
                if (input.equalsIgnoreCase("null")) {
                    numberOfWheels = 0;
                    break;
                }
                try {
                    numberOfWheels = Integer.parseInt(input);
                    if (numberOfWheels <= 0) {
                        System.out.println("Number of wheels cannot be less than 0. Please enter a valid number:");
                    } else {
                        break; // Выходим из цикла, если ввод корректный
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for number of wheels. Please enter a valid integer:");
                    continue; // Продолжаем цикл, если ввод некорректный
                }
            } else {
                // Если ввод пустой, выводим сообщение об ошибке и запрашиваем ввод снова
                System.out.println("Invalid input for number of wheels. Please enter a valid integer:");
                scanner.nextLine(); // Пропускаем некорректный ввод
            }
        }

        System.out.print("Enter capacity: ");
        Long capacity = 0L;

        while (true) {
            if (scanner.hasNextLong()) {
                capacity = scanner.nextLong();
                if (capacity <= 0) {
                    System.out.println("Capacity cannot be less than 0. Please enter a valid number:");
                } else {
                    break; // Выходим из цикла, если ввод корректный
                }
            } else {
                // Если ввод не является целым числом, выводим сообщение об ошибке и просим ввести заново
                System.out.println("Invalid input for capacity. Please enter a valid integer:");
                scanner.next(); // Очищаем буфер ввода
            }
        }
        FuelType fuelType = null;
        while (fuelType == null) {
        System.out.println("Enter fuel type as a number:");
        System.out.println("1. Electricity");
        System.out.println("2. Diesel");
        System.out.println("3. Manpower");
        System.out.println("4. Plasma");

        if (scanner.hasNextInt()) {
        // Считываем выбор пользователя
       int choice = scanner.nextInt();

        // Обрабатываем выбор пользователя

        switch (choice) {
            case 1:
                fuelType = FuelType.ELECTRICITY;
                break;
            case 2:
                fuelType = FuelType.DIESEL;
                break;
            case 3:
                fuelType = FuelType.MANPOWER;
                break;
            case 4:
                fuelType = FuelType.PLASMA;
                break;
            default:
                System.out.println("Mistake:  choose from 1 to 4");

        }
        } else {
            System.out.println("Invalid input. Please enter a valid number:");
            scanner.next();
        }}
        return new Vehicle(name, coordinates, new Date(), enginePower, numberOfWheels, capacity, fuelType);
    }

    private static void executeExecuteScriptCommand(String[] commandParts, VehicleCollection vehicleCollection) {
        if (commandParts.length != 2) {
            System.out.println("Usage: execute_script file_name");
            return;
        }

        String fileName = commandParts[1];
        File scriptFile = new File(fileName);

        if (!scriptFile.exists() || !scriptFile.isFile()) {
            System.out.println("File does not exist or it is not a file: " + fileName);
            return;
        }

        try (Scanner fileScanner = new Scanner(scriptFile)) {
            while (fileScanner.hasNextLine()) {
                String scriptCommand = fileScanner.nextLine().trim();

                String[] scriptCommandParts = scriptCommand.split("\\s+");
                String scriptAction = scriptCommandParts[0];

                switch (scriptAction) {
                    case "help":
                        vehicleCollection.help();
                        break;
                    case "info":
                        vehicleCollection.info();
                        break;
                    case "show":
                        vehicleCollection.show();
                        break;
                    case "insert":
                        if (VehicleCollection.getMaxIdFromCollection()==null){
                            Vehicle.setNextId(1);
                        }
                        else {
                            Vehicle.setNextId(VehicleCollection.getMaxIdFromCollection()+1);
                        }
                        Vehicle newVehicle = parseVehicleInput(fileScanner);
                        Insert.insert(newVehicle.getId(), newVehicle);
                        break;
                    case "update":
                        Update.update(parseId(scriptCommandParts), parseVehicleInput(fileScanner));
                        break;
                    case "remove_key":
                        RemoveKey.removeByKeyCommand(fileScanner);
                        break;
                    case "clear":
                        Clear.clear();
                        break;
                    case "save":
                        Save.saveToFile(fileName);
                        System.out.println("Saved to : " + fileName);
                        break;
                    case "remove_lower":
                        Vehicle inputVehicle = parseVehicleInput(fileScanner);
                        RemoveLower.removeLower(inputVehicle);
                        break;
                    case "replace_if_greater":
                        executeReplaceIfGreaterCommand(scriptCommandParts, vehicleCollection, fileScanner);
                        break;
                    case "remove_lower_key":
                        Long lowerKey = Long.parseLong(scriptCommandParts[1].trim());
                        RemoveLowerKey.removeLowerKey(lowerKey);
                        break;
                    case "remove_any_by_fuel_type":
                        FuelType fuelType = tryParseFuelType(scriptCommandParts[1]);
                        RemoveAnyByFuelType.removeAnyByFuelType(fuelType);
                        break;
                    case "count_by_capacity":
                        Long capacity = Long.parseLong(scriptCommandParts[1].trim());
                        CountByCapacity.countByCapacity(capacity);
                        break;
                    case "print_field_ascending_number_of_wheels":
                        PrintFieldAscendingNumberOfWheels.printFieldAscendingNumberOfWheels();
                        break;
                    case "exit":
                        System.out.println("You are not in the script anymore.");
                        return;
                    default:
                        System.out.println("Unknown command in script: " + scriptAction);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File is not found: " + fileName);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in script.");
        }
    }
    private static void executeReplaceIfGreaterCommand(String[] commandParts, VehicleCollection vehicleCollection, Scanner scanner) {
        Long key = Long.parseLong(commandParts[1].trim());
        Vehicle.setNextId(key);
        Vehicle newVehicle = parseVehicleInput(scanner);
        ReplaceIfGreater.replaceIfGreater(key, newVehicle);
    }
    private static Long tryParseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            System.out.println("Invalid value for Long: " + value);
            return null;
        }
    }
    private static void executeRemoveLowerKeyCommand(String[] commandParts, VehicleCollection vehicleCollection, Scanner scanner) {
        if (commandParts.length != 2) {
            System.out.println("Usage: remove_lower_key {key}");
            return;
        }

        Long key = tryParseLong(commandParts[1]);
        if (key != null) {
            RemoveLowerKey.removeLowerKey(key);
        }
    }

    private static void executeRemoveAnyByFuelTypeCommand(String[] commandParts, VehicleCollection vehicleCollection, Scanner scanner) {
        if (commandParts.length != 2) {
            System.out.println("Usage: remove_any_by_fuel_type fuelType");
            return;
        }

        FuelType fuelType = tryParseFuelType(commandParts[1]);
        if (fuelType != null) {
            RemoveAnyByFuelType.removeAnyByFuelType(fuelType);
        }
    }



    private static FuelType tryParseFuelType(String value) {
        try {
            return FuelType.valueOf(value);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid FuelType: " + value);
            return null;
        }
    }
    private static void executeCountByCapacityCommand(String[] commandParts, VehicleCollection vehicleCollection, Scanner scanner) {
        if (commandParts.length != 2) {
            System.out.println("Usage: count_by_capacity capacity");
            return;
        }

        Long capacity = tryParseLong(commandParts[1]);
        if (capacity != null) {
            CountByCapacity.countByCapacity(capacity);
        }
    }
    private static void executeInsertCommand(String[] commandParts, VehicleCollection vehicleCollection, Scanner scanner) {
        try {
            if (commandParts.length < 3) {
                System.out.println("Invalid parameters for insert command.");
                return;
            }

            Long key = Long.parseLong(commandParts[1]);
            Vehicle newVehicle = parseVehicleInput(scanner);

            Insert.insert(key, newVehicle);
        } catch (NumberFormatException e) {
            System.out.println("Invalid key format for insert command.");
        }
    }


}
