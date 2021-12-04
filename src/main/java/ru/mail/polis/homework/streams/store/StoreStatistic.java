package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {

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
        return orders
                .stream()
                .filter(order -> from.before(order.getTime()) && to.after(order.getTime()))
                .map(order -> order.getItemCount().getOrDefault(typeItem, 0))
                .reduce(0, Integer::sum);
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders
                .stream()
                .collect(
                    Collectors.groupingBy(Order::getDay,
                        Collectors.collectingAndThen(
                            Collectors.reducing((Order x, Order y) -> {
                                    for (Item item : y.getItemCount().keySet()) {
                                        x.getItemCount().put(item, x.getItemCount().getOrDefault(item, 0)
                                                + y.getItemCount().get(item));
                                    }
                                    return x;
                                    }), o -> o.get().getItemCount())));

    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders
                .stream()
                .reduce((x, y) -> {
                    for (Item item : y.getItemCount().keySet()) {
                        x.getItemCount().put(item, x.getItemCount().getOrDefault(item, 0)
                                + y.getItemCount().get(item));
                    }
                    return x;
                }).get().getItemCount()
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders
                .stream()
                .collect(
                        Collectors.toMap(
                                o -> o,
                                o -> {
                                    Long totalCount = 0L;
                                    for (Integer count : o.getItemCount().values()) {
                                        totalCount += count;
                                    }
                                    return totalCount;
                                }
                        )
                )
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }
}


