package Commands;

import static Reciever.VehicleCollection.collection;

public class RemoveLowerKey implements Command {
    @Override
    public void execute() {

    }

    @Override
    public String descr() {
        return "remove lower key - delete all the elements from the collection which key is lower. ";
    }
    public static void removeLowerKey(Long key) {
        // Удалить из коллекции все элементы, ключ которых меньше, чем заданный
        collection.keySet().removeIf(k -> k < key);
    }
}
