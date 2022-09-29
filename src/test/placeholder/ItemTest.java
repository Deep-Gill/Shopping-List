package test.placeholder;

import main.model.InStoreItem;
import main.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Item i1;
    private Item i2;
    private Item i3;
    private Item i4;
    private Map<String, Item> testList;

    @BeforeEach
    public void runBefore() {
        i1 = new InStoreItem("item1", 5.99);
        i2 = new DeliveryItem("item2", 6.99);
        i3 = new InStoreItem("item3", 9.99);
        i4 = new DeliveryItem("item4", 14.99);
        testList = new HashMap<>();
    }

    @Test
    public void testChangeName() {
        assertEquals(i1.getName(), "item1");
        i1.changeName("test1");
        assertEquals(i1.getName(), "test1");
        assertEquals(i2.getName(), "item2");
        i2.changeName("test2");
        assertEquals(i2.getName(), "test2");
    }

    @Test
    public void testChangeCost() {
        assertEquals(i1.getCost(), 5.99);
        i1.changeCost(6.99);
        assertEquals(i1.getCost(), 6.99);
        assertEquals(i2.getCost(), 6.99);
        i2.changeCost(7.99);
        assertEquals(i2.getCost(), 7.99);
    }

    @Test
    public void testGetName() {
        assertEquals(i1.getName(), "item1");
        assertEquals(i2.getName(), "item2");
    }

    @Test
    public void testGetCost() {
        assertEquals(i1.getCost(), 5.99);
        assertEquals(i2.getCost(), 6.99);
    }

    @Test
    public void testGetType() {
        assertFalse(i1.getType());
        assertTrue(i2.getType());
    }

    /*
    @Test
    public void testNameListWithNoItem() throws EmptyListException {
        List<String> nameList = new ArrayList<>();
        assertEquals(i1.nameList(testList), nameList);
    }

     */

    @Test
    public void testNameListWithSingleItem() throws EmptyListException {
        testList.put(i1.getName(), i1);
        List<String> nameList = new ArrayList<>();
        nameList.add("item1");
        assertEquals(i1.nameList(testList), nameList);
    }

    @Test
    public void testNameListWithMultipleItems() throws EmptyListException {
        testList.put(i1.getName(), i1);
        testList.put(i2.getName(), i2);
        testList.put(i3.getName(), i3);
        List<String> nameList = i1.nameList(testList);
        assertEquals(nameList.size(), 3);
        assertTrue(nameList.contains("item1"));
        assertTrue(nameList.contains("item2"));
        assertTrue(nameList.contains("item3"));
    }

    /*
    @Test
    public void testRemoveItemWithItemNotInList() {
        testList.add(i1);
        String str = "item2";
        i1.removeItem(testList, str);
        assertEquals(testList.size(), 1);
        assertTrue(testList.contains(i1));
        testList.add(i2);
        testList.add(i3);
        str = "item4";
        i1.removeItem(testList, str);
        assertEquals(testList.size(), 3);
        assertTrue(testList.contains(i1));
        assertTrue(testList.contains(i2));
        assertTrue(testList.contains(i3));
    }

     */

    @Test
    public void testRemoveItemWithItemInList() throws ItemNotInListException {
        testList.put(i1.getName(), i1);
        i1.removeItem(testList, "item1");
        assertEquals(testList.size(), 0);
        assertFalse(testList.containsValue(i1));
        testList.put(i1.getName(), i1);
        testList.put(i2.getName(), i2);
        testList.put(i3.getName(), i3);
        i1.removeItem(testList, "item2");
        assertEquals(testList.size(), 2);
        assertTrue(testList.containsValue(i1));
        assertFalse(testList.containsValue(i2));
        assertTrue(testList.containsValue(i3));
    }

    /*
    @Test
    public void testRemoveItemWithEmptyList() throws ItemNotInListException {
        String str = "item2";
        i1.removeItem(testList, str);
        assertEquals(testList.size(), 0);
    }
     */

    @Test
    public void testAddToCartWithInStoreItem() throws NegativeCostException {
        i1.addToCart("item1", 5.99, false, testList);
        assertEquals(testList.size(), 1);
        assertTrue(testList.get("item1").getName().equals("item1"));
        assertTrue(testList.get("item1").getCost() == 5.99);
        assertFalse(testList.get("item1").getType());
    }

    @Test
    public void testAddToCartWithDeliveryItem() throws NegativeCostException {
        i1.addToCart("item2", 6.99, true, testList);
        assertEquals(testList.size(), 1);
        assertTrue(testList.get("item2").getName().equals("item2"));
        assertTrue(testList.get("item2").getCost() == 6.99);
        assertTrue(testList.get("item2").getType());
    }


    @Test
    public void testTotalCostWithMultipleInStoreItems() {
        Map<String, Item> foodInStoreItems = new HashMap<>();
        assertEquals(i1.totalCost(foodInStoreItems, 0), 0, 0.01);
        foodInStoreItems.put(i1.getName(), i1);
        assertEquals(i1.totalCost(foodInStoreItems, 0), 6.7088, 0.01);
        foodInStoreItems.put(i3.getName(), i3);
        assertEquals(i1.totalCost(foodInStoreItems, 0), 17.8976, 0.01);
    }

   @Test
    public void testTotalCostWithMultipleDeliveryItems() {
        Map<String, Item> foodDeliveryItems = new HashMap<>();
        assertEquals(i2.totalCost(foodDeliveryItems, 0), 0, 0.01);
        foodDeliveryItems.put(i2.getName(), i2);
        assertEquals(i2.totalCost(foodDeliveryItems, 1), 12.8288, 0.01);
        foodDeliveryItems.put(i4.getName(), i4);
        assertEquals(i2.totalCost(foodDeliveryItems, 2), 34.6176, 0.01);
    }

    @Test
    public void testCostWithMultipleItems() {
        Map<String, Item> foodItems = new HashMap<>();
        assertEquals(i2.cost(foodItems), 0, 0.01);
        foodItems.put(i1.getName(), i1);
        foodItems.put(i2.getName(), i2);
        foodItems.put(i3.getName(), i3);
        foodItems.put(i4.getName(), i4);
        assertEquals(i2.cost(foodItems), 52.5152, 0.01);
    }

    @Test
    public void testSaveAndLoadWithNoItemsInList() throws IOException {
        i1.save(testList, "list0");
        Map<String, Item> testList1 = new HashMap<>();
        i1.load(testList1, "list0");
        assertEquals(testList1.size(), 0);
    }

    @Test
    public void testSaveAndLoadWithOneItemInList() throws IOException {
        testList.put(i1.getName(), i1);
        i1.save(testList, "list2");
        Map<String, Item> testList1 = new HashMap<>();
        i1.load(testList1, "list2");
        assertEquals(testList1.size(), 1);
        assertFalse(testList1.get("item1").getType());
        assertEquals(testList1.get("item1").getCost(), 5.99, 0.01);
        assertEquals(testList1.get("item1").getName(), "item1");
    }

    @Test
    public void testSaveAndLoadWithMultipleItemsInList() throws IOException {
        testList.put(i1.getName(), i1);
        testList.put(i2.getName(), i2);
        testList.put(i3.getName(), i3);
        testList.put(i4.getName(), i4);
        i1.save(testList, "list1");
        Map<String, Item> testList1 = new HashMap<>();
        i1.load(testList1, "list1");
        assertEquals(testList1.size(), 4);

        assertFalse(testList1.get("item1").getType());
        assertEquals(testList1.get("item1").getCost(), 5.99, 0.01);
        assertEquals(testList1.get("item1").getName(), "item1");

        assertTrue(testList1.get("item2").getType());
        assertEquals(testList1.get("item2").getCost(), 6.99, 0.01);
        assertEquals(testList1.get("item2").getName(), "item2");

        assertFalse(testList1.get("item3").getType());
        assertEquals(testList1.get("item3").getCost(), 9.99, 0.01);
        assertEquals(testList1.get("item3").getName(), "item3");

        assertTrue(testList1.get("item4").getType());
        assertEquals(testList1.get("item4").getCost(), 14.99, 0.01);
        assertEquals(testList1.get("item4").getName(), "item4");
    }

    @Test
    public void testEmptyListException() {
        try {
            i1.nameList(testList);
            fail("Exception was not thrown!");
        } catch (EmptyListException e) {
            // e.printStackTrace();
        }
    }

    @Test
    public void testItemNotInListException() {
        try {
            i1.removeItem(testList, "str");
            fail("Exception was not thrown!");
        } catch (ItemNotInListException e) {
            // e.printStackTrace();
        }
        testList.put(i1.getName(), i1);
        testList.put(i2.getName(), i2);
        testList.put(i3.getName(), i3);
        testList.put(i4.getName(), i4);
        try {
            i1.removeItem(testList, "i5");
            fail("Exception was not thrown!");
        } catch (ItemNotInListException e) {
            // e.printStackTrace();
        }
    }

    @Test
    public void testNegativeCostException() {
        try {
            i1.addToCart("test", -10.5, false, testList);
            fail("Exception was not thrown!");
        } catch (NegativeCostException e) {
            // e.printStackTrace();
        }
    }

    /*
    @Test
    public void testPrintAllDataOfItems() {
        testList.put(i1.getName(), i1);
        testList.put(i2.getName(), i2);
        testList.put(i3.getName(), i3);
        testList.put(i4.getName(), i4);
        assertEquals(i1.printAllDataOfItems(testList), "\n" + "In store item: item1 Cost: 5.99"
                + "\n" + "Deliverable item: item2 Cost: 6.99"
                + "\n" + "In store item: item3 Cost: 9.99"
                + "\n" + "Deliverable item: item4 Cost: 14.99")
    }
     */
}



