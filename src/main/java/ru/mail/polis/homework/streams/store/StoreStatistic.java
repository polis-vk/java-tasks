package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
                .filter(order -> from.compareTo(order.getTime()) <= 0 && to.compareTo(order.getTime()) >= 0)
                .map(Order::getItemCount)
                .flatMap(entry -> entry.entrySet().stream())
                .filter(entry -> entry.getKey() == typeItem)
                .mapToLong(Entry::getValue)
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было продано
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(
                groupingBy(
                        order -> {
                            Timestamp timestamp = order.getTime();
                            timestamp.setHours(0);
                            timestamp.setMinutes(0);
                            timestamp.setSeconds(0);
                            return timestamp;
                        },
                        Collectors.mapping(
                                Order::getItemCount,
                                toList()
                        )
                )
        ).entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().flatMap(list -> list.entrySet().stream())
                                .collect(
                                        Collectors.groupingBy(
                                                Map.Entry::getKey,
                                                Collectors.summingInt(
                                                        Map.Entry::getValue
                                                )
                                        )
                                )
                )
        );
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        Map<Item, Integer> entryMap = orders.stream().map(Order::getItemCount).flatMap(
                map -> map.entrySet().stream()
        ).collect(
                groupingBy(
                        Entry::getKey,
                        summingInt(
                                Entry::getValue
                        )
                )
        );
        return Collections.max(entryMap.entrySet(), Entry.comparingByValue()).getKey();
    }

    /**
     * x
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().sorted(Collections.reverseOrder(
                Comparator.comparingLong(
                        order -> order.getItemCount().values().stream().mapToLong(elem -> elem).sum()
                ))).limit(5).collect(
                groupingBy(
                        order -> order,
                        summingLong(
                                order -> order.getItemCount()
                                        .entrySet()
                                        .stream()
                                        .map(entry -> entry.getKey().getPrice() * entry.getValue())
                                        .mapToLong(elem -> elem)
                                        .sum()
                        )
                )
        );
    }

}