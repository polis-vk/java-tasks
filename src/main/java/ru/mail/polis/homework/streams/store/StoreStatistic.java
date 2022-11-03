package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
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
    /*
     * Использую вариант, когда граничные случаи времени входят в диапазон.
     */
    public long proceedsByItems(List<Order> orders, Item typeItem, Timestamp from, Timestamp to) {
        return orders.stream().filter(StoreStatistic::filterOrder).
                filter(order -> inTimeRange(order.getTime(), from, to))
                .map(order -> order.getItemCount().get(typeItem))
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue).sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */

    /*
     * Для одних и тех же товаров суммируем их количество.
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> Timestamp.valueOf(order.getTime().toString().substring(0, 10).concat(" 00:00:00")),
                        Order::getItemCount,
                        (map, nextMap) -> {
                            map.forEach((key, value) -> nextMap.merge(key, value, Integer::sum));
                            return nextMap;
                        }
                ));
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
                        Integer::sum))
                .entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow(NoSuchElementException::new).getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    /*
     * Сначала сортируем заказы по количеству товаров. Потом из отсортированного потока создаем поток из пяти релевант-
     * ных заказов, где перемножаем кол-во и цену за шт. -> суммируем.
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().values().stream().mapToInt(value -> value).sum()))
                .entrySet().stream().sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())
                .limit(5).map(Map.Entry::getKey)
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().entrySet().stream()
                                .mapToLong(mapEntry -> mapEntry.getKey().getPrice() * mapEntry.getValue()).sum()));
    }

    static public boolean filterOrder(Order order) {
        return order != null &&
                order.getTime() != null &&
                order.getItemCount() != null;
    }

    public static boolean inTimeRange(Timestamp currentTime, Timestamp from, Timestamp to) {
        return (currentTime.before(to) || currentTime.equals(to)) &&
                (currentTime.after(from) || currentTime.equals(from));
    }
}

