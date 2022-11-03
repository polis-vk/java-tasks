package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {

    private static final int BIGGEST_ORDERS_LIST_SIZE = 5;

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
            .filter(order -> from.compareTo(order.getTime()) <= 0 && to.compareTo(order.getTime()) >= 0)
            .mapToLong(order -> order.getItemCount().getOrDefault(typeItem, 0))
            .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(
            Collectors.toMap(order -> Timestamp.valueOf(order.getTime().toString()
                    .split(" ")[0] + " 00:00:00"),
                Order::getItemCount, (map1, map2) -> {
                map1.forEach((key, value) -> map2.merge(key, value, Integer::sum));
                return map2;
            }));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
            .map(Order::getItemCount)
            .flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
            .entrySet().stream()
            .max(Comparator.comparingInt(Map.Entry::getValue))
            .orElseThrow(IllegalStateException::new).getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
            .sorted((order1, order2) ->
                order2.getItemCount().values().stream()
                    .reduce(Integer::sum).orElseThrow(IllegalStateException::new)
                - order1.getItemCount().values().stream()
                    .reduce(Integer::sum).orElseThrow(IllegalStateException::new))
            .limit(BIGGEST_ORDERS_LIST_SIZE)
            .collect(Collectors.toMap(
                order -> order,
                order -> order.getItemCount().entrySet().stream()
                    .map(entry -> entry.getKey().getPrice() * entry.getValue())
                    .reduce(Long::sum).orElseThrow(IllegalStateException::new)));
    }
}
