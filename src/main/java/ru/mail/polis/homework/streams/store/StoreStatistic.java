package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {

    private static final int BEGINNING_OF_A_DAY_MILLISECONDS = 86400000;

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
        return orders.stream().filter(it -> it.getTime().before(to) && it.getTime().after(from))
                .mapToLong(it -> it.getItemCount().getOrDefault(typeItem, 0)).sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было продано
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().map(it ->
                new Order(new Timestamp(it.getTime().getTime() -
                        it.getTime().getTime() % BEGINNING_OF_A_DAY_MILLISECONDS),
                it.getItemCount()))
                .collect(Collectors.groupingBy(Order::getTime)).entrySet().stream().map(it ->
                        new Order(it.getKey(), it.getValue().stream()
                        .map(Order::getItemCount).reduce(new HashMap<>(), (subtotal, element) -> {
                            element.forEach((key, value) -> subtotal.put(key, subtotal.getOrDefault(key, 0) + value));
                            return subtotal;
                        }))).collect(Collectors.toMap(Order::getTime, Order::getItemCount));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream().map(Order::getItemCount).reduce(new HashMap<>(), (subtotal, element) -> {
            element.forEach((key, value) -> subtotal.put(key, subtotal.getOrDefault(key, 0) + value));
            return subtotal;
        }).entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().sorted((first, second) ->
                second.getItemCount().values().stream().mapToInt(integer -> integer).sum() -
                        first.getItemCount().values().stream().mapToInt(integer -> integer).sum())
                .limit(5).collect(Collectors.toMap(key -> key,
                        value -> value.getItemCount().values().stream().mapToLong(integer -> integer).sum()));
    }
}
