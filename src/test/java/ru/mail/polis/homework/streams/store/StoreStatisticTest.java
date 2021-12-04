package ru.mail.polis.homework.streams.store;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.internal.matchers.Or;
import ru.mail.polis.homework.objects.MaxTask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;

public class StoreStatisticTest extends TestCase {

    private StoreStatistic ss;
    private List<Order> orderList;
    private Item i1;
    private Item i2;
    private Order o1;
    private Order o2;
    private Order o3;
    private Order o4;

    @Override
    protected void setUp() throws Exception {
        i1 = new Item("car", 1000);
        i2 = new Item("lemon", 22);
        o1 = new Order(new Timestamp(121, 10, 7, 13, 52, 13, 0),
                Stream.of(new Object[][]{
                        {i1, 5},
                        {i2, 7},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        o2 = new Order(new Timestamp(121, 11, 7, 15, 52, 13, 0),
                Stream.of(new Object[][]{
                        {i1, 2},
                        {i2, 6},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        o3 = new Order(new Timestamp(121, 7, 7, 7, 52, 13, 0),
                Stream.of(new Object[][]{
                        {i1, 0},
                        {i2, 8},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        o4 = new Order(new Timestamp(121, 11, 7, 18, 54, 3, 0),
                Stream.of(new Object[][]{
                        {i1, 1},
                        {i2, 2},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));


        orderList = new ArrayList<>();
        orderList.add(o1);
        orderList.add(o2);
        orderList.add(o3);
        orderList.add(o4);

        ss = new StoreStatistic();
    }


    @Test
    public void testProceedsByItems() {

        assertEquals(ss.proceedsByItems(orderList, i1,
                        new Timestamp(121, 9, 7, 12, 52, 13, 0),
                        new Timestamp(121, 12, 7, 12, 52, 13, 0)),
                8);

        assertEquals(ss.proceedsByItems(orderList, i1,
                        new Timestamp(121, 5, 7, 12, 52, 13, 0),
                        new Timestamp(121, 12, 7, 12, 52, 13, 0)),
                8);

        assertEquals(ss.proceedsByItems(orderList, i1,
                        new Timestamp(121, 10, 15, 12, 52, 13, 0),
                        new Timestamp(121, 12, 7, 12, 52, 13, 0)),
                3);
    }

    @Test
    public void testStatisticItemsByDay() {

        Map<Timestamp, Map<Item, Integer>> expected = new HashMap<>();
        expected.put(
                new Timestamp(121, 11, 7, 0, 0, 0, 0),
                Stream.of(new Object[][]{
                        {i1, 3},
                        {i2, 8},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));
        expected.put(
                new Timestamp(121, 7, 7, 0, 0, 0, 0),
                Stream.of(new Object[][]{
                        {i1, 0},
                        {i2, 8},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));
        expected.put(
                new Timestamp(121, 10, 7, 0, 0, 0, 0),
                Stream.of(new Object[][]{
                        {i1, 5},
                        {i2, 7},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        assertEquals(expected, ss.statisticItemsByDay(orderList));
    }

    @Test
    public void testMostPopularItem() {
        assertEquals(i2, ss.mostPopularItem(orderList));

        Order o5 = new Order(new Timestamp(121, 11, 7, 18, 54, 3, 0),
                Stream.of(new Object[][]{
                        {i1, 100},
                        {i2, 0},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        orderList.add(o5);
        assertEquals(i1, ss.mostPopularItem(orderList));
        orderList.remove(o5);
    }

    @Test
    public void testSum5biggerOrders() {
        Order o5 = new Order(new Timestamp(121, 11, 7, 18, 54, 3, 0),
                Stream.of(new Object[][]{
                        {i1, 100},
                        {i2, 0},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        orderList.add(o5);

        Order o6 = new Order(new Timestamp(121, 11, 7, 18, 54, 3, 0),
                Stream.of(new Object[][]{
                        {i1, 20},
                        {i2, 20},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        orderList.add(o6);

        Order o7 = new Order(new Timestamp(121, 11, 7, 18, 54, 3, 0),
                Stream.of(new Object[][]{
                        {i1, 21},
                        {i2, 23},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        orderList.add(o7);

        Order o8 = new Order(new Timestamp(121, 11, 7, 18, 54, 3, 0),
                Stream.of(new Object[][]{
                        {i1, 2},
                        {i2, 12},
                }).collect(Collectors.toMap(data -> (Item) data[0], data -> (Integer) data[1])));

        orderList.add(o8);

        Map<Order, Long> expected = new HashMap<>();
        expected.put(o5, 100L);
        expected.put(o7, 44L);
        expected.put(o6, 40L);
        expected.put(o8, 14L);
        expected.put(o1, 12L);

        assertEquals(expected, ss.sum5biggerOrders(orderList));
    }
}