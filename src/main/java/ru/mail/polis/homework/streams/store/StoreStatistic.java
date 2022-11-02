package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.*;
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
        return orders.stream().filter(order -> order.getTime().compareTo(from) >= 0)
                .filter(order -> order.getTime().compareTo(to) <= 0)
                .flatMap(order -> order.getItemCount().entrySet().stream())
                .filter(itemIntegerEntry -> itemIntegerEntry.getKey().getName().equals(typeItem.getName()))
                .map(Map.Entry::getValue).mapToInt(Integer::intValue).sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(
                Collectors.toMap(order -> getNormalTime(order.getTime()), Order::getItemCount, (exMap, newMap) -> {
            exMap.forEach((key, value) -> newMap.merge(key, value, Integer::sum));
            return newMap;
        }));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream().map(Order::getItemCount)
                .flatMap(order -> order.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().values().stream()
                                .mapToLong(integer -> integer)
                                .sum()))
                .entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount().entrySet().stream()
                                .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue()).sum()
                ));
    }

    private Timestamp getNormalTime(Timestamp timestamp) {
        return Timestamp.valueOf(timestamp.toString().split(" ")[0] + " 00:00:00");
    }
}
