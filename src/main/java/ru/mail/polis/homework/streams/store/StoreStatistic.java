package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
        return orders.stream().filter(order ->
                (order.getTime().after(from)) && (order.getTime().before(to))
        ).mapToLong(order -> order.getItemCount().get(typeItem)).sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(Collectors
                .toMap(order -> truncateTime(order.getTime()),
                        Order::getItemCount, (existing, replacement) -> {
                            Map<Item, Integer> newMap = new HashMap<>(existing);
                            replacement.forEach(
                                    (item, count) -> newMap.merge(item, count, Integer::sum)
                            );
                            return newMap;
                        }));
    }

    private static Timestamp truncateTime(Timestamp timestamp) {
        return Timestamp.valueOf(timestamp.toLocalDateTime().truncatedTo(ChronoUnit.DAYS));
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        Optional<Map<Item, Integer>> totalStats = orders.stream().map(Order::getItemCount)
                .reduce((firstOrder, secondOrder) -> {
                    Map<Item, Integer> newMap = new HashMap<>(firstOrder);
                    secondOrder.forEach(
                            (item, count) -> newMap.merge(item, count, Integer::sum)
                    );
                    return newMap;
                });
        if (totalStats.isPresent()) {
            return null;
        }
        return Collections.max(totalStats.get().entrySet(), new Comparator<Map.Entry<Item, Integer>>() {
            @Override
            public int compare(Map.Entry<Item, Integer> o1, Map.Entry<Item, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        }).getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().map(order -> new AbstractMap.SimpleEntry<Order, Long>(order, getSum(order)))
                .sorted(new Comparator<AbstractMap.SimpleEntry<Order, Long>>() {
                    @Override
                    public int compare(AbstractMap.SimpleEntry<Order, Long> o1, AbstractMap.SimpleEntry<Order, Long> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                }).limit(5).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    private Long getSum(Order order) {
        return order.getItemCount().entrySet().stream().mapToLong((entry) -> entry.getKey().getPrice() * entry.getValue()).sum();
    }
}
