package Commands;

import static Reciever.VehicleCollection.collection;
import java.util.Date;


public class Info implements Command {
    public void execute() {}
    public String descr() {
        return "info - print information about collection (type, initialization date, number of elements)";
    }

    public static void info() {
        System.out.println("Collection Type: Hashtable");
        System.out.println("Initialization Date: " + new Date());
        System.out.println("Number of Elements: " + collection.size());
    }
}