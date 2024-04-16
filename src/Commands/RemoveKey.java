package Commands;

import java.util.Scanner;

import static Reciever.VehicleCollection.collection;

public class RemoveKey implements Command {
    @Override
    public String descr() {
        return "remove by key - remove an element by its key";
    }

    @Override
    public void execute() {

    }
    public static void removeByKey(Long key) { collection.remove(key);
    }
    public static void removeByKeyCommand(Scanner scanner) {
        System.out.print("Enter key to remove: ");
        Long key = scanner.nextLong();
        removeByKey(key);
        System.out.println("Element with key " + key + " removed.");
    }

}
