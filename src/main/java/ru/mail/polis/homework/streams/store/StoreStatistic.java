package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
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
                .filter(order -> isTimestampBetween(order.getTime(), from, to))
                .flatMap(order -> order.getItemCount().keySet().stream())
                .filter(item -> item.equals(typeItem))
                .count();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .map(order -> new Order(normalizeTimestamp(order.getTime()), order.getItemCount()))
                .collect(Collectors.toMap(Order::getTime, Order::getItemCount));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().collect(Collectors.toMap(order -> order, this::countTotalPrice))
                .entrySet().stream()
                .sorted((pair1, pair2) -> pair2.getValue().compareTo(pair1.getValue()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isTimestampBetween(Timestamp src, Timestamp from, Timestamp to) {
        return src.compareTo(from) >= 0 && src.compareTo(to) <= 0;
    }

    private Timestamp normalizeTimestamp(Timestamp src) {
        String normalized = src.toString().substring(0, 10);
        return Timestamp.valueOf(normalized + " 00:00:00 000 UTC");
    }

    private long countTotalPrice(Order o) {
        long totalPrice = 0;
        for (Map.Entry<Item, Integer> item: o.getItemCount().entrySet()) {
            totalPrice += item.getValue();
        }
        return totalPrice;
    }

}
