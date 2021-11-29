package ru.mail.polis.homework.streams.store;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

public class StoreStatisticTest {

    List<Order> list;
    Item[] items = {
            new Item("apple", 100),
            new Item("banana", 200),
            new Item("Bread", 30),
            new Item("Milk", 60)
    };

    @Before
    public void before() {
        list = new LinkedList<>();
        //yyyy-[m]m-[d]d hh:mm:ss
        Map<Item, Integer> position1 = new TreeMap<>(Comparator.comparing(Item::getName));
        position1.put(items[0], 10);
        position1.put(items[1], 5);
        Order o1 = new Order(Timestamp.valueOf("2021-1-1 1:1:1"), position1);
        Map<Item, Integer> position2 = new TreeMap<>(Comparator.comparing(Item::getName));
        position2.put(items[0], 10);
        position2.put(items[1], 5);
        position2.put(items[3], 100);
        Order o2 = new Order(Timestamp.valueOf("2021-1-1 1:1:1"), position2);

        list.add(o1);
        list.add(o2);
    }

    @Test
    public void testStatisticItemsByDay() {
        Map<Timestamp, Map<Item, Integer>> map = StoreStatistic.statisticItemsByDay(list);
        int l = 0;
    }

    @Test
    public void testMostPopularItem() {
        Item item = StoreStatistic.mostPopularItem(list);
        int l = 0;
    }

    @Test
    public void tastSum5biggerOrders() {
        Map<Order, Long> orderLongMap = StoreStatistic.sum5biggerOrders(list);
        int l = 0;
    }
}