package main.ui;
import main.model.*;
import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsPanel extends JPanel implements ActionListener {
    private JLabel addNameLabel;
    private JLabel addCostLabel;
    private JLabel removeNameLabel;
    private JLabel nameSaveFileLabel;
    private JLabel nameLoadFileLabel;

    private JTextField addCostTextField;
    private JTextField addNameTextField;
    private JTextField removeNameTextField;
    private JTextField nameSaveFileTextField;
    private JTextField nameLoadFileTextField;

    private JCheckBox deliveryCheckBox;
    private JCheckBox inStoreCheckBox;

    private JButton addButton;
    private JButton removeButton;
    private JButton costButton;
    private JButton namesButton;
    private JButton informationButton;
    private JButton saveButton;
    private JButton loadButton;

    private EventListenerList listenerList = new EventListenerList();

    private List<String> names;
    private Map<String, Item> shoppingList;
    private Item i1;
    private Item i2;


    public DetailsPanel() {
        names = new ArrayList<>();
        shoppingList = new HashMap<>();
        i1 = new DeliveryItem("i1", 0);
        i2 = new InStoreItem("i2", 0);

        Dimension size = getPreferredSize();
        size.width = 400;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Operations"));

        // Labels
        addNameLabel = new JLabel("Name of the item: ");
        addCostLabel = new JLabel("Cost of the item: ");
        removeNameLabel = new JLabel("Name of the item: ");
        nameSaveFileLabel = new JLabel("Name of the file: ");
        nameLoadFileLabel = new JLabel("Name of the file: ");

        // Text Fields
        addNameTextField = new JTextField(10);
        addCostTextField = new JTextField(10);
        removeNameTextField = new JTextField(10);
        nameSaveFileTextField = new JTextField(10);
        nameLoadFileTextField = new JTextField(10);

        //  Check Boxes
        deliveryCheckBox = new JCheckBox("Delivery item");
        inStoreCheckBox = new JCheckBox("In Store item");

        // Buttons
        addButton = new JButton("Add to Cart");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);

        removeButton = new JButton("Remove from Cart");
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);

        costButton = new JButton("Show total cost of all items");
        costButton.setActionCommand("cost");
        costButton.addActionListener(this);

        namesButton = new JButton("Show the names of all items");
        namesButton.setActionCommand("names");
        namesButton.addActionListener(this);

        informationButton = new JButton("Show the information of all items");
        informationButton.setActionCommand("information");
        informationButton.addActionListener(this);

        saveButton = new JButton("Save items to file");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);

        loadButton = new JButton("Load file");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //// First column ////
        gc.anchor = GridBagConstraints.LINE_END;

        //// First 4 rows ////
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.gridy = 0;
        add(addNameLabel, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(addCostLabel, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        add(deliveryCheckBox, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        add(addButton, gc);

        //// Rows 4-5 ////
        gc.weightx = 0.5;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.gridy = 4;
        add(removeNameLabel, gc);

        gc.gridx = 0;
        gc.gridy = 5;
        add(removeButton, gc);

        //// Individual buttons ////
        gc.weightx = 0.5;
        gc.weighty = 2;

        gc.gridx = 0;
        gc.gridy = 6;
        add(costButton, gc);

        gc.gridx = 0;
        gc.gridy = 7;
        add(namesButton, gc);

        gc.gridx = 0;
        gc.gridy = 8;
        add(informationButton, gc);

        //// Save ////
        gc.weightx = 0.5;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.gridy = 9;
        add(nameSaveFileLabel, gc);

        gc.gridx = 0;
        gc.gridy = 10;
        add(saveButton, gc);

        //// Load ////
        gc.gridx = 0;
        gc.gridy = 11;
        add(nameLoadFileLabel, gc);

        gc.gridx = 0;
        gc.gridy = 12;
        add(loadButton, gc);

        //// Second column ////
        gc.weightx = 3.5;
        gc.weighty = 0.5;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(addNameTextField, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(addCostTextField, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(inStoreCheckBox, gc);

        gc.gridx = 1;
        gc.gridy = 4;
        add(removeNameTextField, gc);

        gc.gridx = 1;
        gc.gridy = 9;
        add(nameSaveFileTextField, gc);

        gc.gridx = 1;
        gc.gridy = 11;
        add(nameLoadFileTextField, gc);

    }

    // EFFECTS: runs detailEventOccured(DetailEvent event) method for every DetailListener in listenerList
    public void fireDetailEvent(DetailEvent event) {
        Object[] listeners = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == DetailListener.class) {
                ((DetailListener)listeners[i + 1]).detailEventOccured(event);
            }
        }
    }

    // MODIFIES: listenerList
    // EFFECTS: adds listener to listenerList
    public void addDetailListener(DetailListener listener) {
        listenerList.add(DetailListener.class, listener);
    }


    // MODIFIES: shoppingList, textArea, addNameTextField, removeNameTextField,nameSaveFileTextField,
    //           nameLoadFileTextField, deliveryCheckBox, inStoreCheckBox
    // EFFECTS: when a particular button is pressed, it generates the required response
    //          when addButton is pressed:
    //               the item that user specified is added to shopping list and then all the fields related to adding
    //               the item are cleared
    //          when remove button is pressed:
    //               the item with the name that the user wants removed is removed from the shopping list and then the
    //               removeNameTextField is cleared
    //          when cost button is pressed:
    //               the total cost of all items is printed on textArea
    //          when namesButton is pressed:
    //               the names of all the items in the shopping list is printed on textArea
    //          when informationButton is pressed:
    //               the name, type and cost of all items in the shopping list is printed on textArea
    //          when saveButton is pressed:
    //               the shopping list is saved on a file where the user wants the shopping list to be stored
    //          when loadButton is pressed:
    //               the information of all the items in the file with the name specified in nameLoadTextField is taken
    //               and a new shopping list is created with those items
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            addToCart();
            clearAddFields();
        } else if (e.getActionCommand().equals("remove")) {
            remove();
        } else if (e.getActionCommand().equals("cost")) {
            printCost();
        } else if (e.getActionCommand().equals("names")) {
            printNamesOfItemsInList();
        } else if (e.getActionCommand().equals("information")) {
            printDataOfAllItems();
        } else if (e.getActionCommand().equals("save")) {
            save();
        } else if (e.getActionCommand().equals("load")) {
            load();
        }
    }

    private void clearAddFields() {
        addNameTextField.setText("");
        addCostTextField.setText("");
        deliveryCheckBox.setSelected(false);
        inStoreCheckBox.setSelected(false);
    }

    private void printCost() {
        String text = String.format("%.2f", i1.cost(shoppingList));
        text = "\n" + "Cost: " + text;
        fireDetailEvent(new DetailEvent(this, text));
    }

    private void load() {
        String listName = nameLoadFileTextField.getText();
        try {
            i2.load(shoppingList, listName);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            nameLoadFileTextField.setText("");
        }
    }

    private void save() {
        String listName = nameSaveFileTextField.getText();
        try {
            i2.save(shoppingList, listName);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            nameSaveFileTextField.setText("");
        }
    }

    private void printDataOfAllItems() {
        List<String> data = i1.getAllDataOfItems(shoppingList);
        fireDetailEvent(new DetailEvent(this, "\n" + "Information: "));
        for (int i = 0; i < data.size(); i += 1) {
            String text = "\n" + "   " + data.get(i);
            fireDetailEvent(new DetailEvent(this, text));
        }
    }

    private void printNamesOfItemsInList() {
        try {
            List<String> nameList = i2.nameList(shoppingList);
            fireDetailEvent(new DetailEvent(this, "\n" + "Names:"));
            for (int i = 0; i < nameList.size(); i += 1) {
                String text = "\n  " + nameList.get(i);
                fireDetailEvent(new DetailEvent(this, text));
            }
        } catch (EmptyListException o) {
            String text = "Nothing was in your shopping list!";
            fireDetailEvent(new DetailEvent(this, text));
        }
    }

    private void remove() {
        String item = removeNameTextField.getText();
        try {
            i2.removeItem(shoppingList, item);
        } catch (ItemNotInListException o) {
            String text = "\n" + "Item was not in your list!" + "\n" + "Hence nothing was removed from your list";
            fireDetailEvent(new DetailEvent(this, text));
        } finally {
            removeNameTextField.setText("");
        }
    }

    private void addToCart() {
        String name = addNameTextField.getText();
        Double cost = Double.parseDouble(addCostTextField.getText());
        try {
            Boolean type = isDeliveryitem();
            i2.addToCart(name, cost, type, shoppingList);
        } catch (NegativeCostException o) {
            String text = "\n" + "Enter a positive cost!" + "\n" + "Item was not added to your shopping list";
            fireDetailEvent(new DetailEvent(this, text));
        } catch (NotDeliveryOrInStoreException i) {
            String text = "\n" + "An item can only be either a delivery or an in store item." + "\n"
                          + "Hence item was not added to your cart!";
            fireDetailEvent(new DetailEvent(this, text));
        } finally {
            String text = "\n" + "Remember to provide non-negative and before tax costs of any further items."
                          + "\n" + "Also provide a different name for each item.";
            fireDetailEvent(new DetailEvent(this, text));
        }
    }

    private boolean isDeliveryitem() throws NotDeliveryOrInStoreException {
        if (deliveryCheckBox.isSelected() && inStoreCheckBox.isSelected()) {
            String text = "\n" + "Please only select one of delivery item or in store item but not both.";
            fireDetailEvent(new DetailEvent(this, text));
            throw new NotDeliveryOrInStoreException();
        } else if (!deliveryCheckBox.isSelected() && !inStoreCheckBox.isSelected()) {
            String text = "\n" + "Please select one of delivery item or in store item.";
            fireDetailEvent(new DetailEvent(this, text));
            throw new NotDeliveryOrInStoreException();
        }
        Boolean type = deliveryCheckBox.isSelected();
        return type;
    }
}
