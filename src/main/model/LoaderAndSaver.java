package main.model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LoaderAndSaver {

    public LoaderAndSaver() { }

    // REQUIRES: the list must be the user's shoppingList and listName must be the name of the file
    //           where the user wants to save the shoppingList
    // EFFECTS:  Opens the file and then re-write its contents to include the name, cost and the type
    //           of item with spaces in between. Finish by closing the file
    public void save(Map<String, Item> shoppingList, String listName) throws IOException {
        FileWriter fw = new FileWriter(listName);
        writeAllitemsIntoFile(shoppingList, fw);
        fw.close();
    }

    private void writeAllitemsIntoFile(Map<String, Item> shoppingList, FileWriter fw) throws IOException {
        // JSONObject json = new JSONObject();
        for (Map.Entry<String, Item> i : shoppingList.entrySet()) {
            fw.write(i.getValue().name + "," + i.getValue().cost + "," + i.getValue().isDeliverable + "\n");
        }
    }

    // REQUIRES: the shoppingList must be empty and listName must be the name of the file where the user
    //           stored the shoppingList previously
    // MODIFIES: shoppingList
    // EFFECTS:  use the file where the user stored the shoppingList previously to
    //           create the user's previous shoppingList
    public void load(Map<String, Item> shoppingList, String listName) throws IOException {
        File file = new File(listName);
        if (!(file.length() == 0)) {
            putAllitemsFromFileIntoList(shoppingList, listName);
        }
    }

    private void putAllitemsFromFileIntoList(Map<String, Item> shoppingList, String listName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(listName));
        for (String line : lines) {
            String[] data = new String[3];
            data = line.split(",");
            String name = data[0];
            double cost = Double.valueOf(data[1]);
            Boolean type = Boolean.parseBoolean(data[2]);
            putItemInList(shoppingList, name, cost, type);
        }
    }

    private void putItemInList(Map<String, Item> shoppingList, String name, double cost, Boolean type) {
        if (type) {
            Item i0 = new DeliveryItem(name, cost);
            shoppingList.put(i0.name, i0);
        } else {
            Item i0 = new main.model.InStoreItem(name, cost);
            shoppingList.put(i0.name, i0);
        }
    }

    // REQUIRES: a line from the file where the user previously stored the shoppingList
    // EFFECTS: creates an ArrayList where every word between spaces get added to an Array List in order
    //          from left to right (most left ones in the line being added first)
    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

}
