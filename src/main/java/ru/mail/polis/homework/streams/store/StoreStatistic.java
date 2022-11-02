package ru.mail.polis.homework.streams.store;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
     * @param orders   - список заказов
     * @param typeItem - вид товара
     * @param from     - с какого момента вести подсчет
     * @param to       - по какой момент вести подсчет
     * @return - кол-во проданного товара
     */
    public long proceedsByItems(List<Order> orders, Item typeItem, Timestamp from, Timestamp to) {
        return orders.stream()
                .filter(order -> order.getTime().compareTo(to) <= 0 && order.getTime().compareTo(from) >= 0)
                .map(order -> order.getItemCount().getOrDefault(typeItem, 0))
                .reduce(0, Integer::sum);
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
                .map(order -> {
                    Timestamp timestamp = order.getTime();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date(timestamp.getTime()));
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    return new Order(new Timestamp(calendar.getTime().getTime()), order.getItemCount());
                })
                .collect(Collectors.groupingBy(Order::getTime))
                .entrySet().stream()
                .map(entry -> {
                    Map<Item, Integer> map = new HashMap<>();
                    entry.getValue().forEach(order ->
                            order.getItemCount().forEach((item, integer) ->
                                    map.merge(item, integer, Integer::sum)));
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), map);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .map(Order::getItemCount)
                .reduce(new HashMap<>(), (baseMap, map) -> {
                    map.forEach((item, integer) -> baseMap.merge(item, integer, Integer::sum));
                    return baseMap;
                }).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(null, null)).getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .map(order -> {
                    int count = order.getItemCount().values().stream()
                            .reduce(0, Integer::sum);
                    return new AbstractMap.SimpleEntry<>(order, count);
                })
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .limit(5)
                .map(entry -> {
                    long sum = entry.getKey().getItemCount().entrySet().stream()
                            .map(entryItem -> entryItem.getValue() * entryItem.getKey().getPrice())
                            .reduce(0L, Long::sum);
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), sum);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
