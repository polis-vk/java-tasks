package ru.mail.polis.homework.streams.store;

import java.security.Key;
import java.sql.Timestamp;
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
        return orders.stream()
                .filter(order -> order.getTime().compareTo(to) <= 0 && order.getTime().compareTo(from) >= 0)
                .map(order -> {
                    if (order.getItemCount().get(typeItem) == null) {
                        return 0;
                    }
                    return order.getItemCount().get(typeItem);
                })
                .reduce(Integer::sum).get();
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
                .collect(Collectors.groupingBy(Order::getTime))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        order -> order.getValue().stream()
                                .flatMap(order1 -> order1.getItemCount().entrySet().stream())
                                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
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
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
                .entrySet()
                .stream()
                .max((o1, o2) -> o1.getValue() - o2.getValue()).get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .sorted((o1, o2) ->
                        o1.getItemCount().entrySet().stream().mapToInt(value -> value.getValue()).sum() -
                                o2.getItemCount().entrySet().stream().mapToInt(value -> value.getValue()).sum()
                ).limit(5)
                .collect(Collectors.toMap(
                        first -> first,
                        second -> second.getItemCount()
                                .entrySet()
                                .stream()
                                .mapToLong(value -> (int) (value.getValue() * value.getKey().getPrice())).sum()
                ));
    }
}
