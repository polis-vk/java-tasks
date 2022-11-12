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
        return orders.stream().filter(order ->
                    order.getTime().equals(from) || order.getTime().after(from)
                    && order.getTime().equals(to) || order.getTime().before(to))
                .map(order -> order.getItemCount().getOrDefault(typeItem, 0))
                .mapToLong(Integer::longValue).sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> Timestamp.valueOf(order.getTime().toLocalDateTime().toLocalDate().atStartOfDay()),
                        Order::getItemCount,
                        (map1, map2) -> {
                            map2.forEach((key, value) -> map1.merge(key, value, Integer::sum));
                            return map1;
                        }
                        ));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return Collections.max(orders.stream()
                .map(Order::getItemCount)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)
                )).entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Collections.reverseOrder(
                        Comparator.comparing(
                                order -> order.getItemCount().values().stream().mapToLong(Integer::longValue).sum()
                )))
                .limit(5)
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().entrySet().stream()
                                .mapToLong(item -> item.getValue() * item.getKey().getPrice()).sum()
                ));
    }
}
