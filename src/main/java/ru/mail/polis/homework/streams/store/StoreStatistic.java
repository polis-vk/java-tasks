package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.LocalTime;
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
        return orders.stream()
                .filter(order -> order.getTime().after(from) && order.getTime().after(to) && order.getItemCount().containsKey(typeItem))
                .mapToLong(value -> value.getItemCount().get(typeItem))
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        Map<Timestamp, Map<Item, Integer>> result = new HashMap<>();
        orders.forEach(order -> {
            Timestamp day = Timestamp.valueOf(order.getTime().toLocalDateTime().with(LocalTime.MIN));
            if (result.containsKey(day)) {
                Map<Item, Integer> dayItemCount = result.get(day);
                order.getItemCount().forEach((item, counter) -> {
                    dayItemCount.computeIfPresent(item, (key, value) -> value + counter);
                    dayItemCount.computeIfAbsent(item, value -> counter);
                });
            } else {
                result.put(day, order.getItemCount());
            }
        });
        return result;
    }


    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.maxBy(Comparator.comparingInt(Map.Entry::getValue))
                ))
                .keySet().stream()
                .findAny()
                .get();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().collect(Collectors.groupingBy(
                order -> order,
                Collectors.summingLong(value -> value.getItemCount()
                        .values()
                        .stream()
                        .reduce(Integer::sum).get())
        )).entrySet().stream()
                .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
