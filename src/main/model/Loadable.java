package main.model;

import java.io.IOException;
import java.util.Map;

public interface Loadable {
    void load(Map<String, Item> shoppingList, String listName) throws IOException;
}
