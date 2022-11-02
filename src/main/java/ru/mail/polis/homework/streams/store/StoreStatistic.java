package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {
    private final static int LIMIT = 5;
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
        long startTime = from.getTime();
        long endTime = to.getTime();
        return orders.stream()
                .filter(order -> {
                    long orderTime = order.getTime().getTime();
                    return orderTime >= startTime && orderTime <= endTime && order.getItemCount().get(typeItem) != null;
                })
                .map(order -> order.getItemCount().get(typeItem))
                .mapToLong(Integer::longValue)
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return null;
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItemCount()
                        .entrySet()
                        .stream())
                .collect(Collectors
                        .groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparingLong(order -> -order.getItemCount()
                        .entrySet()
                        .stream()
                        .mapToLong(Map.Entry::getValue)
                        .sum()))
                .limit(LIMIT)
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount()
                                .entrySet()
                                .stream()
                                .mapToLong(entry -> entry.getValue() * entry.getKey().getPrice())
                                .sum()
                ));
    }
}
