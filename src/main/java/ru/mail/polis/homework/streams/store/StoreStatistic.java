package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
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
     * @param orders - список заказов
     * @param typeItem - вид товара
     * @param from - с какого момента вести подсчет
     * @param to - по какой момент вести подсчет
     * @return - кол-во проданного товара
     */
    public long proceedsByItems(List<Order> orders, Item typeItem, Timestamp from, Timestamp to) {
        return orders
                .stream()
                .filter(order -> order.getTime().after(from) && order.getTime().before(to) && order.getItemCount().containsKey(typeItem))
                .mapToLong(orderMap -> orderMap.getItemCount().get(typeItem))
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        Map<Timestamp, Map<Item, Integer>> itemStatistics = new HashMap<>();
        for (Order o : orders) {
            Timestamp orderDate = Timestamp.valueOf(o.getTime().toLocalDateTime().with(LocalDateTime.MIN));
            if (itemStatistics.containsKey(orderDate)) {
                Map<Item, Integer> currentItem = itemStatistics.get(orderDate);
                o.getItemCount().forEach((item, counter) -> {
                    currentItem.computeIfPresent(item, (key, value) -> value + counter);
                    currentItem.computeIfAbsent(item, value -> counter);
                });
            } else {
                itemStatistics.put(orderDate, o.getItemCount());
            }
        }
        return itemStatistics;
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
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.maxBy(Comparator.comparingInt(Map.Entry::getValue))))
                .keySet()
                .stream()
                .findFirst()
                .get();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders
                .stream()
                .collect(Collectors.groupingBy(order -> order, Collectors.summingLong(value -> value.getItemCount().values().stream().reduce(Integer::sum).get())))
                .entrySet()
                .stream()
                .sorted((item1, item2) -> (int) (item2.getValue() - item1.getValue()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
