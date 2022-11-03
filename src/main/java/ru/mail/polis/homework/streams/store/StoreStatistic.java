package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        return orders.stream()
                .filter(order -> order.getTime().compareTo(from) >= 0 && order.getTime().compareTo(to) <= 0)
                .peek(System.out::println)
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .filter(itemEntry -> itemEntry.getKey().equals(typeItem))
                .map(itemEntry -> (long) itemEntry.getValue())
                .reduce(0L, Long::sum);
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
                .collect(Collectors.toMap(order -> Timestamp.valueOf(order.getTime().toString().split(" ")[0] + " 00:00:00"),
                        Order::getItemCount,
                        (itemMap1, itemMap2) -> {
                            itemMap1.forEach((key, value) -> itemMap2.merge(key, value, Integer::sum));
                            return itemMap2;
                        }));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders
                .stream()
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(Comparator.naturalOrder()))
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
                .collect(Collectors.toMap(order -> order,
                        order -> order.getItemCount()
                                .values()
                                .stream()
                                .mapToLong(l -> l)
                                .sum()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toMap(order -> order,
                        order -> order.getItemCount()
                                .entrySet().stream()
                                .mapToLong(item -> item.getKey().getPrice() * item.getValue())
                                .sum()));
    }
}
