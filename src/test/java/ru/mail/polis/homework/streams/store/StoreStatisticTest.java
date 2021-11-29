package ru.mail.polis.homework.streams.store;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.internal.matchers.Or;
import ru.mail.polis.homework.objects.MaxTask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    protected void setUp() throws Exception {
        i1 = new Item("car", 1000);
        i2 = new Item("lemon", 22);
        o1 = new Order(new Timestamp(2021, 10, 7, 12, 52, 13, 0),
                Stream.of(new Object[][] {
                        { i1, 5},
                        { i2, 7},
                }).collect(Collectors.toMap(data -> (Item)data[0], data -> (Integer) data[1])));

        o2 = new Order(new Timestamp(2021, 11, 7, 12, 52, 13, 0),
                Stream.of(new Object[][] {
                        { i1, 2},
                        { i2, 6},
                }).collect(Collectors.toMap(data -> (Item)data[0], data -> (Integer) data[1])));

        o3 = new Order(new Timestamp(2021, 7, 7, 12, 52, 13, 0),
                Stream.of(new Object[][] {
                        { i1, 0},
                        { i2, 8},
                }).collect(Collectors.toMap(data -> (Item)data[0], data -> (Integer) data[1])));

        orderList = new ArrayList<>();
        orderList.add(o1);
        orderList.add(o2);
        orderList.add(o3);

        ss = new StoreStatistic();
    }


    @Test
    public void testProceedsByItems() {

        assertEquals(ss.proceedsByItems(orderList, i1,
                new Timestamp(2021, 9, 7, 12, 52, 13, 0),
                new Timestamp(2021, 12, 7, 12, 52, 13, 0)),
                7);

        assertEquals(ss.proceedsByItems(orderList, i1,
                        new Timestamp(2021, 5, 7, 12, 52, 13, 0),
                        new Timestamp(2021, 12, 7, 12, 52, 13, 0)),
                7);

        assertEquals(ss.proceedsByItems(orderList, i1,
                        new Timestamp(2021, 10, 15, 12, 52, 13, 0),
                        new Timestamp(2021, 12, 7, 12, 52, 13, 0)),
                2);
    }
}