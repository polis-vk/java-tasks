package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
     * @param orders - список заказов
     * @param typeItem - вид товара
     * @param from - с какого момента вести подсчет
     * @param to - по какой момент вести подсчет
     * @return - кол-во проданного товара
     */
    public long proceedsByItems(List<Order> orders, Item typeItem, Timestamp from, Timestamp to) {
        return orders.stream()
                .filter(order -> order.getTime().equals(from) ||
                        order.getTime().after(from) && order.getTime().before(to) || order.getTime().equals(to))
                .map(order -> order.getItemCount().get(typeItem))
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue)
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
                .collect(Collectors.toMap(
                        order -> Timestamp.from(order.getTime().toInstant().truncatedTo(ChronoUnit.DAYS)),
                        Order::getItemCount,
                        (itemIntegerMap1, itemIntegerMap2) -> {
                            itemIntegerMap2.entrySet().forEach(itemIntegerEntry ->
                                    itemIntegerMap1.merge(itemIntegerEntry.getKey(), itemIntegerEntry.getValue(), Integer::sum));
                            return itemIntegerMap1;
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
                .map(order -> order.getItemCount().entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().values().stream()
                                .mapToLong(Integer::longValue)
                                .sum()
                )).entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().entrySet().stream()
                                .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue())
                                .sum()
                ));
    }
}
