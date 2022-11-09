package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {

    private static final int BIGGER_ORDERS_COUNT = 5;

    /**
     * Вернуть сколько было продано определенного товара за переданный промежуток времени
     *
     * @param orders - список заказов
     * @param typeItem - вид товара
     * @param from - с какого момента вести подсчет
     * @param to - по какой момент вести подсчет
     * @return - кол-во проданного товара
     */
    public long proceedsByItems(List<Order> orders, Item typeItem, Timestamp from, Timestamp to) {
        return orders.stream()
                .filter(order -> order.getTime().compareTo(from) >= 0 && order.getTime().compareTo(to) <= 0)
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .filter(itemIntegerEntry -> itemIntegerEntry.getKey().equals(typeItem))
                .mapToLong(Map.Entry::getValue)
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было продано
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> Timestamp.valueOf(
                                order.getTime().toLocalDateTime().toLocalDate().atStartOfDay()
                        ),
                        Order::getItemCount,
                        (itemIntegerMap1, itemIntegerMap2) ->
                                Stream.concat(itemIntegerMap1.entrySet().stream(), itemIntegerMap2.entrySet().stream())
                                        .collect(Collectors.groupingBy(Map.Entry::getKey,
                                        Collectors.summingInt(Map.Entry::getValue)))
                ));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказов.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().values().stream()
                                .mapToLong(Integer::longValue)
                                .sum()
                ))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(BIGGER_ORDERS_COUNT)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        orderLongEntry -> orderLongEntry.getKey().getItemCount().entrySet().stream()
                                .mapToLong(value -> value.getKey().getPrice() * value.getValue())
                                .sum()
                ));
    }
}
