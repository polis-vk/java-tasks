package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
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
        return orders.stream()
                .filter(order -> order.getTime().before(to) && order.getTime().after(from))
                .map(Order::getItemCount)
                .flatMap(itemIntegerMap -> itemIntegerMap.entrySet().stream())
                .filter(itemIntegerEntry -> itemIntegerEntry.getKey().equals(typeItem))
                .mapToLong(Map.Entry::getValue)
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
                .collect(Collectors.toMap(order ->
                                getBeginningOfDay(order.getTime()),
                        Order::getItemCount
                ));
    }

    private Timestamp getBeginningOfDay(Timestamp timestamp) {
        LocalDateTime ld = LocalDate.from(timestamp.toLocalDateTime()).atStartOfDay();
        ZonedDateTime zdt = ZonedDateTime.of(ld, ZoneId.systemDefault());
        long millis = zdt.toInstant().toEpochMilli();
        return new Timestamp(millis);
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream().
                map(Order::getItemCount).
                flatMap(itemIntegerMap -> itemIntegerMap.entrySet().stream()).
                collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting())).
                entrySet().stream()
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
        return orders.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        order -> order.getItemCount().values().stream().mapToLong(i -> i).sum())).
                entrySet().stream().
                sorted(Map.Entry.comparingByValue()).
                limit(5).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
