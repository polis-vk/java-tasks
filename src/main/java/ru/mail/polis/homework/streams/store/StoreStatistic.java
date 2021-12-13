package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.*;
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
                .filter(order -> order.getTime().compareTo(from) >= 0)
                .filter(order -> order.getTime().compareTo(to) <= 0)
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .mapToLong(item -> item.getKey().equals(typeItem) ? item.getValue() : 0)
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
                .map(order -> new Order(toStartOfDay(order.getTime()), order.getItemCount()))
                .collect(Collectors.toMap(Order::getTime, Order::getItemCount,
                        (leftMap, rightMap) ->
                                Stream.of(leftMap, rightMap)
                                        .flatMap(map -> map.entrySet().stream())
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))));
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return Objects.requireNonNull(orders.stream()
                        .flatMap(order -> order.getItemCount().entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                        .entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .orElse(null))
                .getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return null;
    }

    private Timestamp toStartOfDay(Timestamp dataAndTime) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dataAndTime);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }
}
