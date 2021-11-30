package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {
    private static final double secondsInDay = 86400d;

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
        return orders.stream().
                filter(order -> order.getTime().compareTo(from) >= 0 &&
                        order.getTime().compareTo(to) < 0 && order.getItemCount().containsKey(typeItem)).
                mapToLong(order -> order.getItemCount().get(typeItem)).
                sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().
                map(order -> new Order(startOfDay(order.getTime()), order.getItemCount())).
                collect(Collectors.groupingBy(Order::getTime)).entrySet().stream().
                collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().
                        flatMap(order -> order.getItemCount().entrySet().stream()).
                        collect(Collectors.groupingBy(Map.Entry::getKey,
                                Collectors.summingInt(Map.Entry::getValue)))));
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream().
                flatMap(order -> order.getItemCount().entrySet().stream()).
                collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue))).
                entrySet().stream().
                max(Map.Entry.comparingByValue()).
                orElseThrow(NullPointerException::new).
                getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().
                map(order -> new AbstractMap.SimpleEntry<>(order, order.getItemCount().values().stream().
                        mapToLong(Integer::longValue).
                        sum())).
                sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).
                limit(5).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o2, HashMap::new));
    }

    private static Timestamp startOfDay(Timestamp ts) {
        return new Timestamp((long) (Math.floor(ts.getTime() / secondsInDay) * secondsInDay));
    }
}
