package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {
    private static final long TIME_PER_DAY = 24 * 60 * 60 * 1000;

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
        return orders
                .stream()
                .filter(order -> {
                    Timestamp currentTime = order.getTime();
                    return (currentTime.before(to) || currentTime.equals(to)) && (currentTime.after(from) || currentTime.equals(from));
                })
                .map(order -> order.getItemCount().getOrDefault(typeItem, 0))
                .mapToLong(productAmount -> (long) productAmount)
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
        return orders
                .stream()
                .collect(Collectors.toMap(
                        order -> new Timestamp(order.getTime().getTime() % TIME_PER_DAY),
                        Order::getItemCount,
                        (mapWithItemAndAmount1, mapWithItemAndAmount2) -> {
                            mapWithItemAndAmount1.forEach((item, amount) -> mapWithItemAndAmount2.merge(item, amount, Integer::sum));
                            return mapWithItemAndAmount2;
                        })
                );
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
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders
                .stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().values()
                                .stream()
                                .mapToInt(i -> i)
                                .sum()
                ))
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        pairOfOrderAndAmount -> pairOfOrderAndAmount.getKey()
                                .getItemCount()
                                .entrySet()
                                .stream()
                                .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue())
                                .sum()
                ));
    }
}
