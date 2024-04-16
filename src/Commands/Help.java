package Commands;

public class Help implements Command {

    public String descr() {
        return "help — help, print info about commands";
    }

    public void execute() {

    }
    public static void help() {
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
}