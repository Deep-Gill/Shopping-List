package main.model;

import java.util.Map;

public class DeliveryItem extends Item {

    // REQUIRES: cost to be greater than 0
    // MODIFIES: this
    // EFFECTS:  construct a deliverable item with the given name and cost
    public DeliveryItem(String name, double cost) {
        super(name, cost);
        isDeliverable = true;
    }

    // REQUIRES: the list must be user's shoppingList and count >= 0
    // EFFECTS: add up the costs of all items in the list and then for each delivery item include
    //          its shipping cost to the total
    @Override
    public double totalCost(Map<String, Item> itemDeliverables, int count) {
        double pst = 0.05;
        double gst = 0.07;
        double shippingCost = 5;
        double money = super.totalCost(itemDeliverables, count);
        return ((1 + pst + gst) * money) + (count * shippingCost);
    }

}
