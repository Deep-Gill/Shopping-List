package main.model;

import java.io.IOException;
import java.util.Map;

public interface Saveable {
    void save(Map<String, Item> shoppingList, String listName) throws IOException;
}
