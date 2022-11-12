package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {
    private static final int MAX = 5;

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
                .filter(order -> order.getItemCount().containsKey(typeItem))
                .mapToLong(order -> order.getItemCount().getOrDefault(typeItem, 0))
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> Timestamp.valueOf(order
                                .getTime()
                                .toLocalDateTime()
                                .toLocalDate()
                                .atStartOfDay()),
                        Order::getItemCount, (old, cur) -> {
                            old.forEach((key, value) -> cur.merge(key, value, Integer::sum));
                            return cur;
                        }));
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(order -> order.getItemCount()
                        .values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum())))
                .limit(MAX)
                .collect(Collectors.toMap(order -> order, order -> order.getItemCount()
                        .entrySet()
                        .stream()
                        .map(datum -> datum.getKey().getPrice() * datum.getValue())
                        .mapToLong(Long::longValue).sum()));
    }
}
