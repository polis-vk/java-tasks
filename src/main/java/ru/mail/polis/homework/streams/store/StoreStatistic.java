package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {
    private static final int DAYS_DIVIDER = 1000 * 60 * 60 * 24;

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
        long fromTime = from.getTime();
        long toTime = to.getTime();
        return orders.stream()
                .filter(order -> {
                    long orderTime = order.getTime().getTime();
                    return order.getItemCount().get(typeItem) != null &&
                            orderTime >= fromTime &&
                            orderTime <= toTime;
                })
                .mapToLong(order -> order.getItemCount().get(typeItem))
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> {
                            long orderTime = order.getTime().getTime();
                            return new Timestamp((orderTime / DAYS_DIVIDER) * DAYS_DIVIDER);
                        }
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .flatMap(order -> order.getItemCount().entrySet().stream())
                                .collect(Collectors.groupingBy(
                                        Map.Entry::getKey,
                                        Collectors.summingInt(Map.Entry::getValue)
                                ))
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
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingLong(Map.Entry::getValue)))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
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
                .limit(5)
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount()
                                .entrySet()
                                .stream()
                                .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue())
                                .sum()
                ));

    }
}
