package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .filter(order -> order.getTime().after(from) && order.getTime().before(to))
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .filter(entry -> entry.getKey().equals(typeItem))
                .map(Map.Entry::getValue)
                .reduce(Integer::sum).orElse(0);
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(Collectors.toMap(Order::getTime, Order::getItemCount,
                (map1, map2) -> {
                    map1.putAll(map2);
                    return map1;
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
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey();

    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().collect(Collectors.toMap(Function.identity(),
                order -> order.getItemCount().entrySet().stream()
                        .mapToLong(itemCount -> itemCount.getKey().getPrice() * itemCount.getValue())
                        .reduce(Long::sum).orElse(0L)))
                .entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry<Order, Long>::getValue).reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
