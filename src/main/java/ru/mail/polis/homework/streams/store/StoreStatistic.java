package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5-ть баллов
 */
public class StoreStatistic {

    private static final long MilliSecondsInDay = 86_400_000L;

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
                .filter(order -> (from.after(order.getTime()) && to.before(order.getTime())))
                .mapToLong(order -> order.getItemCount().getOrDefault(typeItem, 0))
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public static Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                                (order -> new Timestamp(order.getTime().toLocalDateTime().getDayOfMonth() * MilliSecondsInDay)),
                                Collectors.flatMapping(order -> order.getItemCount().entrySet().stream(),
                                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                        )
                );
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public static Item mostPopularItem(List<Order> orders) {
        return orders.stream().flatMap(order -> order.getItemCount().entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)
                ))
                .entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public static Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparingInt(order -> order.getItemCount().size()))
                .limit(5)
                .collect(Collectors.groupingBy(
                                order -> order,
                                Collectors.flatMapping(order -> order.getItemCount().entrySet().stream(),
                                        Collectors.summingLong(entry -> entry.getValue() * entry.getKey().getPrice())
                                )
                        )
                );
    }
}

