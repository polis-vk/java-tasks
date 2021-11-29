package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StoreStatisticTest {
    List<Order> orders = new LinkedList<>();

    Map<Item, Integer> items1 = new HashMap<>();
    Map<Item, Integer> items2 = new HashMap<>();
    Map<Item, Integer> items3 = new HashMap<>();
    Map<Item, Integer> items4 = new HashMap<>();

    @Before
    public void setStartValues() {
        items1.put(new Item("Axe1", 9000), 15);
        items1.put(new Item("Bottle1", 80), 32);
        items1.put(new Item("Pencil1", 30), 501);
        items1.put(new Item("Mouse1", 1100), 11);
        items1.put(new Item("Laptop1", 89000), 0);

        items2.put(new Item("Axe2", 132), 5);
        items2.put(new Item("Bottle2", 1), 2);
        items2.put(new Item("Pencil2", 2), 0);
        items2.put(new Item("Mouse2", 3), 13);
        items2.put(new Item("Laptop2", 11), 21);

        items3.put(new Item("Axe1", 10000), 15);
        items3.put(new Item("Bottle1", 10), 32);
        items3.put(new Item("Pencil1", 3), 500);
        items3.put(new Item("Mouse1", 100), 11);
        items3.put(new Item("Laptop1", 77), 0);

        items4.put(new Item("Axe2", 132), 55);
        items4.put(new Item("Bottle2", 3), 2);
        items4.put(new Item("Pencil2", 5), 0);
        items4.put(new Item("Mouse2", 88), 1);
        items4.put(new Item("Laptop2", 10), 10);

        orders.add(new Order(new Timestamp(1638130900), items1));
        orders.add(new Order(new Timestamp(1638130950), items2));
        orders.add(new Order(new Timestamp(1638130980), items3));
        orders.add(new Order(new Timestamp(1638131000), items4));
    }

    @Test
    public void proceedsByItems() {
        final StoreStatistic storeStatistic = new StoreStatistic();
        final long ans1 = storeStatistic.proceedsByItems(orders, new Item("Axe2", 132),
                new Timestamp(1638130950), new Timestamp(1638131005));
        assertEquals(60, ans1);

        final long ans2 = storeStatistic.proceedsByItems(orders, new Item("Laptop2", 11),
                new Timestamp(1638130900), new Timestamp(1638131001));
        assertEquals(21, ans2);

        final long ans3 = storeStatistic.proceedsByItems(orders, new Item("Laptop2", 11),
                new Timestamp(1638131000), new Timestamp(1638131001));
        assertEquals(0, ans3);

    }

    @Test
    public void statisticItemsByDay() {
    }

    @Test
    public void mostPopularItem() {
    }

    @Test
    public void sum5biggerOrders() {
    }
}