package main.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Item implements Loadable, Saveable {

    protected String name;
    protected double cost;
    protected List<String> names;
    protected boolean isDeliverable;
    protected LoaderAndSaver loaderAndSaver = new LoaderAndSaver();

    public Item(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    // MODIFIES: this
    // EFFECTS:  changes name of the item to the newName
    public void changeName(String newName) {
        this.name = newName;
    }

    // MODIFIES: this
    // EFFECTS:  changes cost of the item to newCost
    public void changeCost(double newCost) {
        this.cost = newCost;
    }

    // EFFECTS: returns the name of the item
    public String getName() {
        return name;
    }

    // EFFECTS: returns the cost of the item
    public double getCost() {
        return cost;
    }

    // EFFECTS: returns the type of the item, true if delivery item and false if in-store item
    public boolean getType() {
        return isDeliverable;
    }

    // REQUIRES: the list of items that is in the users shopping list
    // EFFECTS:  creates a list of strings with all the item's names
    public List<String> nameList(Map<String, Item> lst) throws EmptyListException {
        names = new ArrayList<>();
        for (Map.Entry<String, Item> i : lst.entrySet()) {
            names.add(i.getValue().name);
        }
        if (names.size() == 0) {
            throw new EmptyListException();
        } else {
            return names;
        }
    }

    // REQUIRES: the list of items that is in the user's shopping list
    // EFFECTS:  removes an item that the user doesn't want from the shopping List
    public void removeItem(Map<String, Item> lst, String i) throws ItemNotInListException {
        int size = lst.size();
        if (lst.containsKey(i)) {
            lst.remove(i);
        }
        if (lst.size() == size) {
            throw new ItemNotInListException();
        }
    }

    // REQUIRES: the list must be the user's shoppingList
    // MODIFIES: shoppingList
    // EFFECTS:  creates and adds the delivery or in-store item to the shoppingList
    public void addToCart(String name, Double cost, Boolean type, Map<String, Item> shoppingList)
            throws NegativeCostException {
        if (cost < 0) {
            throw new NegativeCostException();
        }
        if (type) {
            Item i3 = new DeliveryItem(name, cost);
            addItemToList(shoppingList, i3);
        } else {
            Item i4 = new InStoreItem(name, cost);
            addItemToList(shoppingList, i4);
        }
    }

    private void addItemToList(Map<String, Item> shoppingList, Item i) {
        shoppingList.put(i.name, i);
    }


    // REQUIRES: the list must be user's shoppingList
    // EFFECTS: add up the costs of all items in the list and then for each delivery item include
    //          its shipping cost to the total
    public double cost(Map<String, Item> shoppingList) {
        int count = countTheNumberOfDeliveryItems(shoppingList);
        if (count == 0) {
            Item i5 = new InStoreItem("i1", 0);
            return i5.totalCost(shoppingList, count);
        } else {
            Item i5 = new DeliveryItem("I5", 0);
            return i5.totalCost(shoppingList, count);
        }
    }

    private int countTheNumberOfDeliveryItems(Map<String, Item> shoppingList) {
        int count = 0;
        for (Map.Entry<String, Item> item : shoppingList.entrySet()) {
            if (item.getValue().isDeliverable) {
                count = count + 1;
            }
        }
        return count;
    }

    // REQUIRES: the list of items must be the user's shopping List
    // EFFECTS:  calculates and returns the total cost of all the items
    public double totalCost(Map<String, Item> items, int count) {
        return addTheCostsOfAllitems(items);
    }

    private double addTheCostsOfAllitems(Map<String, Item> items) {
        double money = 0;
        for (Map.Entry<String, Item> i : items.entrySet()) {
            money = money + i.getValue().cost;
        }
        return money;
    }

    // REQUIRES: the list must be the user's shoppingList and listName must be the name of the file
    //           where the user wants to save the shoppingList
    // EFFECTS:  Opens the file and then re-write its contents to include the name, cost and the type
    //           of item with spaces in between. Finish by closing the file
    public void save(Map<String, Item> shoppingList, String listName) throws IOException {
        loaderAndSaver.save(shoppingList, listName);
    }

    // REQUIRES: the shoppingList must be empty and listName must be the name of the file where the user
    //           stored the shoppingList previously
    // MODIFIES: shoppingList
    // EFFECTS:  use the file where the user stored the shoppingList previously to
    //           create the user's previous shoppingList
    public void load(Map<String, Item> shoppingList, String listName) throws IOException {
        loaderAndSaver.load(shoppingList, listName);
    }

    // REQUIRES: the items must be the user's shopping list
    // MODIFIES: data
    // EFFECTS: creates and returns an array list that stores the type, name and cost of every item in the list
    public List<String> getAllDataOfItems(Map<String, Item> items) {
        List<String> data = new ArrayList<>();
        for (Map.Entry<String, Item> i : items.entrySet()) {
            String name = i.getValue().name;
            Double cost = i.getValue().cost;
            String type;
            if (i.getValue().isDeliverable) {
                type = "Deliverable item: ";
            } else {
                type = "In store item: ";
            }
            String text = type + name +  " , Cost: " + cost;
            data.add(text);
        }
        return data;
    }

}