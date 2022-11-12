package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Collections;
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
        return orders.stream().filter(
                order -> tsBetween(order.getTime(), from, to)
        ).reduce(
                0,
                (first, second) -> first + second.getItemCount().getOrDefault(typeItem, 0),
                Integer::sum
        );
    }

    private boolean tsBetween(Timestamp ts, Timestamp tsAft, Timestamp tsBef) {
        return (ts.equals(tsAft) || ts.after(tsAft)) && (ts.equals(tsBef) || ts.before(tsBef));
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(
                Collectors.groupingBy(
                        order -> Timestamp.valueOf(order.getTime().toLocalDateTime().toLocalDate().atStartOfDay()),
                        Collectors.toSet()
                )
        ).entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        orderListEntry -> orderListEntry.getValue().stream().reduce(
                                (Map<Item, Integer>) new HashMap<Item, Integer>(),
                                (first, second) -> mergeMaps(first, second.getItemCount()),
                                this::mergeMaps
                        )
                )
        );
    }

    public <K> Map<K, Integer> mergeMaps(Map<K, Integer> mergeInto, Map<K, Integer> toMerge) {
        for (K key : toMerge.keySet()) {
            mergeInto.put(key, mergeInto.getOrDefault(key, 0) + toMerge.get(key));
        }
        return mergeInto;
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return Collections.max(
                orders.stream().reduce(
                        (Map<Item, Integer>) new HashMap<Item, Integer>(),
                        (first, second) -> mergeMaps(first, second.getItemCount()),
                        this::mergeMaps
                ).entrySet(),
                Map.Entry.comparingByValue()
        ).getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().sorted(
                (first, second) -> second.getItemCount().values().stream().mapToInt(i -> i).sum() -
                        first.getItemCount().values().stream().mapToInt(i -> i).sum()
        ).limit(5).collect(
                Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().entrySet().stream()
                                .mapToLong(
                                        itemNumEntry -> itemNumEntry.getValue() * itemNumEntry.getKey().getPrice()
                                ).sum()
                )
        );
    }
}
