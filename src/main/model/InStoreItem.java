package main.model;

import java.util.Map;

public class InStoreItem extends main.model.Item {

    // REQUIRES: cost to be greater than 0
    // MODIFIES: this
    // EFFECTS:  construct an in store item with the given name and cost
    public InStoreItem(String name, double cost) {
        super(name, cost);
        isDeliverable = false;
    }

    // REQUIRES: the list must be user's shoppingList and count >= 0
    // EFFECTS: add up the costs of all items in the list
    @Override
    public double totalCost(Map<String, main.model.Item> itemsInStore, int count) {
        double pst = 0.05;
        double gst = 0.07;
        double money = super.totalCost(itemsInStore, count);
        return (1 + pst + gst) * money;
    }

}
