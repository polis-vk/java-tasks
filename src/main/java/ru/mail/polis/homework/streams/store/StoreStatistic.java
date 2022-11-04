package ru.mail.polis.homework.streams.store;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.org.apache.xpath.internal.operations.Or;

import static java.util.Comparator.comparingInt;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {

    /**
     * Вернуть сколько было продано определенного товара за переданный промежуток времени
     *
     * @param orders   - список заказов
     * @param typeItem - вид товара
     * @param from     - с какого момента вести подсчет
     * @param to       - по какой момент вести подсчет
     * @return - кол-во проданного товара
     */
    public long proceedsByItems(List<Order> orders, Item typeItem, Timestamp from, Timestamp to) {
        return orders.stream()
                .filter(order -> order.getTime().compareTo(from) >= 0 && order.getTime().compareTo(to) <= 0)
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .filter(entry -> entry.getKey().equals(typeItem))
                .mapToLong(Map.Entry::getValue).sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        Set<String> ordersDatesSet = orders.stream().
                map(order -> order.getTime().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .collect(Collectors.toSet());
        Map<Timestamp, Map<Item, Integer>> itemsMap = new HashMap<>();
        ordersDatesSet.forEach(date -> {
            orders.forEach(order -> {
                String orderDate = order.getTime().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
                if (orderDate.equals(date)) {
                    Timestamp tsTemp = convertStringDateToTimestampAtStartOfDay(orderDate);
                    if (itemsMap.containsKey(tsTemp)) {
                        order.getItemCount().forEach((key, value) -> {
                            if (itemsMap.get(tsTemp).containsKey(key)) {
                                itemsMap.get(tsTemp).put(
                                        key,
                                        itemsMap.get(tsTemp).get(key) + value
                                );
                            } else {
                                itemsMap.get(tsTemp).put(
                                        key,
                                        value
                                );
                            }
                        });
                    } else {
                        Timestamp ts = convertStringDateToTimestampAtStartOfDay(date);
                        itemsMap.put(ts, order.getItemCount());
                    }
                }
            });
        });
        return itemsMap;
    }

    /**
     * Converts String date, contains only days (example: "2015-09-15") to Timestamp of this date at start of day
     * Input: "2015-09-15"
     * Return: 2015-09-15 00:00:00.0
     *
     * @param date String date, contains only days
     * @return Timestamp of this date at start of the day
     */
    public Timestamp convertStringDateToTimestampAtStartOfDay(String date) {
        LocalDateTime ldtTemp = LocalDateTime.of(
                Integer.parseInt(date.split("-")[0]),
                Integer.parseInt(date.split("-")[1]),
                Integer.parseInt(date.split("-")[2]),
                0,
                0);
        return Timestamp.valueOf(ldtTemp);
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        Map<Item, Integer> allItemsMap = new HashMap<>();
        for (Order order : orders) {
            allItemsMap = Stream.concat(allItemsMap.entrySet().stream(), order.getItemCount().entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            Integer::sum
                    ));
        }
        return Collections.max(allItemsMap.entrySet(), comparingInt(Map.Entry::getValue)).getKey();
    }

    private final int ORDERS_LIMIT = 5;

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        Map<Order, Integer> ordersProductCountMap = new HashMap<>();
        orders.forEach(order ->
                ordersProductCountMap.put(
                        order,
                        order.getItemCount().values().stream()
                                .mapToInt(i -> i)
                                .sum()
                )
        );
        Map<Order, Long> ordersPricesMap = new HashMap<>();
        ordersProductCountMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<Order, Integer>::getValue).reversed())
                .limit(ORDERS_LIMIT).collect(Collectors.toList())
                .forEach(order ->
                        ordersPricesMap.put(
                                order.getKey(),
                                order.getKey().getItemCount().entrySet().stream()
                                        .mapToLong(
                                                entry -> entry.getKey().getPrice() * entry.getValue()
                                        ).sum()
                        )
                );
        return ordersPricesMap;
    }
}
