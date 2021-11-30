package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * черновые тесты
 */

public class StoreStatisticTest {
    final List<Order> orders = new LinkedList<>();
    final Map<Item, Integer> items1 = new HashMap<>();
    final Map<Item, Integer> items2 = new HashMap<>();
    final Map<Item, Integer> items3 = new HashMap<>();
    final Map<Item, Integer> items4 = new HashMap<>();
    final StoreStatistic storeStatistic = new StoreStatistic();

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
        items3.put(new Item("Pencil1", 30), 500);
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
        final long ans1 = storeStatistic.proceedsByItems(orders, new Item("Axe2", 132),
                new Timestamp(1638130950), new Timestamp(1638131005));
        final long ans2 = storeStatistic.proceedsByItems(orders, new Item("Laptop2", 11),
                new Timestamp(1638130900), new Timestamp(1638131001));
        final long ans3 = storeStatistic.proceedsByItems(orders, new Item("Laptop2", 11),
                new Timestamp(1638131000), new Timestamp(1638131001));
        //asserts
        assertEquals(21, ans2);
        assertEquals(60, ans1);
        assertEquals(0, ans3);

    }

    @Test
    public void statisticItemsByDay() {
        final List<Order> ordersStatistic = new LinkedList<>();
        final Map<Item, Integer> itemsStatistic1 = new HashMap<>();
        itemsStatistic1.put(new Item("Axe1", 9000), 15);
        itemsStatistic1.put(new Item("Bottle1", 80), 32);
        itemsStatistic1.put(new Item("Pencil1", 30), 501);
        final Map<Item, Integer> itemsStatistic2 = new HashMap<>();
        itemsStatistic2.put(new Item("Axe1", 9000), 15);
        itemsStatistic2.put(new Item("Bottle1", 80), 32);
        itemsStatistic2.put(new Item("Pencil1", 30), 0);
        itemsStatistic2.put(new Item("Pencil1", 30), 0);
        ordersStatistic.add(new Order(new Timestamp(1638130950), itemsStatistic1));
        ordersStatistic.add(new Order(new Timestamp(1638234149), itemsStatistic2));
        final Map<Timestamp, Map<Item, Integer>> itemsByDay = storeStatistic.statisticItemsByDay(ordersStatistic);
        final Map<Timestamp, Map<Item, Integer>> ans = new HashMap<>() {{
            put(new Timestamp(1638057600), new HashMap<>() {{
                put(new Item("Axe1", 9000), 15);
                put(new Item("Bottle1", 80), 32);
                put(new Item("Pencil1", 30), 501);
            }});
            put(new Timestamp(1638230400), new HashMap<>() {{
                put(new Item("Axe1", 9000), 15);
                put(new Item("Bottle1", 80), 32);
                put(new Item("Pencil1", 30), 0);
            }});
        }};
        assertEquals(ans, itemsByDay);
    }

    @Test
    public void mostPopularItem() {
        final Item item1 = storeStatistic.mostPopularItem(orders);
        orders.get(0).getItemCount().remove(new Item("Pencil1", 30));
        orders.get(2).getItemCount().remove(new Item("Pencil1", 30));
        final Item item2 = storeStatistic.mostPopularItem(orders);
        //asserts
        assertEquals(new Item("Pencil1", 30), item1);
        assertEquals(new Item("Axe2", 132), item2);
    }

    @Test
    public void sum5biggerOrders() {
        orders.add(new Order(new Timestamp(1638130900), items1));
        orders.add(new Order(new Timestamp(1638130950), items2));
        orders.add(new Order(new Timestamp(1638130980), items3));
        orders.add(new Order(new Timestamp(1638131000), items4));
        final Map<Order, Long> sum5biggerOrders = storeStatistic.sum5biggerOrders(orders);
        final HashMap<Order, Long> first = new HashMap<>() {{
            put(orders.get(2), 558L);
            put(orders.get(6), 558L);
            put(orders.get(0), 559L);
            put(orders.get(4), 559L);
            put(orders.get(3), 68L);

        }};
        final HashMap<Order, Long> second = new HashMap<>() {{
            put(orders.get(2), 558L);
            put(orders.get(6), 558L);
            put(orders.get(0), 559L);
            put(orders.get(4), 559L);
            put(orders.get(7), 68L);
        }};
        /*System.out.println("Method result:");
        sum5biggerOrders.forEach((x, y) -> System.out.println(x.getTime().getTime() + " " + x + " " + y));
        System.out.println("\nExpectation:");
        first.forEach((x, y) -> System.out.println(x.getTime().getTime() + " " + x + " " + y));
        System.out.println("\nExpectation:");
        second.forEach((x, y) -> System.out.println(x.getTime().getTime() + " " + x + " " + y));*/
        //asserts
        assertTrue(sum5biggerOrders.equals(first) || sum5biggerOrders.equals(second));
    }
}