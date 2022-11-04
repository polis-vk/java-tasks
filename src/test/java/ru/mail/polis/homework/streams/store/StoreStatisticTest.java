package ru.mail.polis.homework.streams.store;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StoreStatisticTest {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private static final Item banana = new Item("banana", 80L);
    private static final Item chery = new Item("chery", 120L);
    private static final Item apple = new Item("apple", 40L);

    @Test
    public void proceedsByItems() {
        Order order1 = new Order(new Timestamp(1000L), Collections.singletonMap(banana, 20));
        Order order2 = new Order(new Timestamp(1200L), Collections.singletonMap(chery, 7));
        Order order3 = new Order(new Timestamp(1500L), Collections.singletonMap(banana, 7));

        List<Order> orders = Stream.of(order1, order2, order3).collect(Collectors.toList());
        StoreStatistic storeStatistic = new StoreStatistic();
        assertEquals(27, storeStatistic.proceedsByItems(orders, banana, new Timestamp(1000L), new Timestamp(1500L)));
    }

    @Test
    public void statisticItemsByDay() throws ParseException {
        Date date1 = formatter.parse("2015-09-05");
        Date date2 = formatter.parse("2015-09-15");
        Date date3 = formatter.parse("2015-09-30");

        Order order_day_1_1 = generateByItemList(new Timestamp(date1.getTime()), Stream.of(banana, banana, chery, apple).collect(Collectors.toList()));
        Order order_day_1_2 = generateByItemList(new Timestamp(date1.getTime() + 10000L), Stream.of(chery, chery, chery, apple).collect(Collectors.toList()));
        Order order_day_2_1 = generateByItemList(new Timestamp(date2.getTime()), Stream.of(apple, apple, chery, apple).collect(Collectors.toList()));
        Order order_day_2_2 = generateByItemList(new Timestamp(date2.getTime() + TimeUnit.DAYS.toMillis(1) - 10000L), Stream.of(chery, banana, banana, banana).collect(Collectors.toList()));
        Order order_day_3_1 = generateByItemList(new Timestamp(date3.getTime()), Stream.of(chery, apple, chery, apple).collect(Collectors.toList()));
        Order order_day_3_2 = generateByItemList(new Timestamp(date3.getTime() + TimeUnit.DAYS.toMillis(1) / 2), Stream.of(chery, banana, apple).collect(Collectors.toList()));

        List<Order> orders = Stream.of(order_day_1_1, order_day_2_1, order_day_3_1, order_day_1_2, order_day_2_2, order_day_3_2).collect(Collectors.toList());

        StoreStatistic storeStatistic = new StoreStatistic();
        Map<Timestamp, Map<Item, Integer>> results = storeStatistic.statisticItemsByDay(orders);

        assertEquals(3, results.size());
        //Test first day statistic
        assertEquals(Integer.valueOf(2), results.get(new Timestamp(date1.getTime())).get(banana));
        assertEquals(Integer.valueOf(4), results.get(new Timestamp(date1.getTime())).get(chery));
        assertEquals(Integer.valueOf(2), results.get(new Timestamp(date1.getTime())).get(apple));

        //Test second day statistic
        assertEquals(Integer.valueOf(3), results.get(new Timestamp(date2.getTime())).get(banana));
        assertEquals(Integer.valueOf(2), results.get(new Timestamp(date2.getTime())).get(chery));
        assertEquals(Integer.valueOf(3), results.get(new Timestamp(date2.getTime())).get(apple));

        //Test third day statistic
        assertEquals(Integer.valueOf(1), results.get(new Timestamp(date3.getTime())).get(banana));
        assertEquals(Integer.valueOf(3), results.get(new Timestamp(date3.getTime())).get(chery));
        assertEquals(Integer.valueOf(3), results.get(new Timestamp(date3.getTime())).get(apple));
    }

    @Test
    public void mostPopularItem() throws ParseException {
        Date date = formatter.parse("2015-09-05");
        Order order1 = generateByItemList(new Timestamp(date.getTime()), Stream.of(chery, chery, chery, banana).collect(Collectors.toList()));
        Order order2 = generateByItemList(new Timestamp(date.getTime()), Stream.of(banana, banana, apple, apple).collect(Collectors.toList()));
        Order order3 = generateByItemList(new Timestamp(date.getTime()), Stream.of(apple, banana, apple, chery).collect(Collectors.toList()));
        Order decisiveOrder = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(chery));

        List<Order> orders = Stream.of(order1, order2, order3, decisiveOrder).collect(Collectors.toList());

        StoreStatistic storeStatistic = new StoreStatistic();
        Item result = storeStatistic.mostPopularItem(orders);

        assertEquals(chery, result);
    }

    @Test
    public void mostPopularItemWithSamePopularItems() throws ParseException {
        Date date = formatter.parse("2015-09-05");
        Order order1 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(banana));
        Order order2 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(chery));
        Order order3 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(apple));

        List<Order> orders = Stream.of(order1, order2, order3).collect(Collectors.toList());

        StoreStatistic storeStatistic = new StoreStatistic();
        Item result = storeStatistic.mostPopularItem(orders);

        assertEquals(banana, result);
    }

    @Test
    public void sum5biggerOrders() throws ParseException {
        Date date = formatter.parse("2015-09-05");
        Order order1 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(banana));
        Order order2 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(chery));
        Order order3 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(apple));
        Order order4 = generateByItemList(new Timestamp(date.getTime()), Stream.of(banana, apple).collect(Collectors.toList()));//80+40=120
        Order order5 = generateByItemList(new Timestamp(date.getTime()), Stream.of(apple, chery).collect(Collectors.toList()));//40+120=160
        Order order6 = generateByItemList(new Timestamp(date.getTime()), Stream.of(banana, chery).collect(Collectors.toList()));//80+120=200
        Order order7 = generateByItemList(new Timestamp(date.getTime()), Stream.of(banana, chery, chery, banana).collect(Collectors.toList()));//400
        Order order8 = generateByItemList(new Timestamp(date.getTime()), Stream.of(chery, apple, chery, apple).collect(Collectors.toList()));//320

        List<Order> orders = Stream.of(order1, order2, order3, order4, order5, order6, order7, order8).collect(Collectors.toList());

        StoreStatistic storeStatistic = new StoreStatistic();
        Map<Order, Long> result = storeStatistic.sum5biggerOrders(orders);

        assertEquals(5, result.size());
        assertNull(result.get(order1));
        assertNull(result.get(order2));
        assertNull(result.get(order3));
        assertEquals(Long.valueOf(120L), result.get(order4));
        assertEquals(Long.valueOf(160L), result.get(order5));
        assertEquals(Long.valueOf(200L), result.get(order6));
        assertEquals(Long.valueOf(400L), result.get(order7));
        assertEquals(Long.valueOf(320L), result.get(order8));
    }

    @Test
    public void sum5biggerOrdersIfCountOrdersLess5() throws ParseException {
        Date date = formatter.parse("2015-09-05");
        Order order1 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(banana));
        Order order2 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(chery));
        Order order3 = generateByItemList(new Timestamp(date.getTime()), Collections.singletonList(apple));

        List<Order> orders = Stream.of(order1, order2, order3).collect(Collectors.toList());

        StoreStatistic storeStatistic = new StoreStatistic();
        Map<Order, Long> result = storeStatistic.sum5biggerOrders(orders);

        assertEquals(3, result.size());
        assertEquals(Long.valueOf(banana.getPrice()), result.get(order1));
        assertEquals(Long.valueOf(chery.getPrice()), result.get(order2));
        assertEquals(Long.valueOf(apple.getPrice()), result.get(order3));
    }

    private Order generateByItemList(Timestamp timestamp, List<Item> items) {
        return new Order(timestamp, items.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(item -> 1)))
        );
    }

}