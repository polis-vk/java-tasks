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
                .filter(order -> order.getTime().compareTo(from) >= 0
                        && order.getTime().compareTo(to) <= 0
                        && order.getItemCount().containsKey(typeItem))
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
                .collect(Collectors.toMap(order -> Timestamp.valueOf(order.getTime().toLocalDateTime().toLocalDate().atStartOfDay()),
                        Order::getItemCount,
                        (order1, order2) -> {
                            order2.forEach((key, value) -> order1.merge(key, value, Integer::sum));
                            return order1;
                        }
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
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders
                .stream()
                .collect(Collectors.toMap(order -> order,
                        order -> order.getItemCount().values().stream()
                                .mapToLong(Integer::longValue).sum()))
                .entrySet()
                .stream()
                .sorted((entry1, entry2) -> (int) (entry2.getValue() - entry1.getValue()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, order -> order.getKey().getItemCount()
                        .entrySet().stream()
                        .mapToLong(item -> item.getKey().getPrice() * item.getValue()).sum()));
    }
}
