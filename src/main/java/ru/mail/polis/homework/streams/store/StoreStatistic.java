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
                .filter(order -> from.compareTo(order.getTime()) <= 0)
                .filter(order -> to.compareTo(order.getTime()) >= 0)
                .filter(order -> order.getItemCount().containsKey(typeItem))
                .mapToLong(order -> order.getItemCount().get(typeItem))
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
                .collect(Collectors.toMap(order -> normalizeTime(order.getTime()),
                        Order::getItemCount, (exMap, newMap) -> {
                            exMap.forEach((key, value) -> newMap.merge(key, value, Integer::sum));
                            return newMap;
                        }
                ));
    }

    private Timestamp normalizeTime(Timestamp src) {
        String normalized = src.toString().substring(0, 10);
        return Timestamp.valueOf(normalized + " 00:00:00");
    }


    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .map(Order::getItemCount)
                .flatMap(order -> order.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(NullPointerException::new)
                .getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .sorted((o1, o2) -> {
                    if (o1.getItemCount().values().stream()
                            .reduce(0, Integer::sum).equals(o2.getItemCount().values().stream()
                                    .reduce(0, Integer::sum))) {
                        return 0;
                    }
                    return o2.getItemCount().values().stream()
                            .reduce(0, Integer::sum) - o1.getItemCount().values().stream()
                            .reduce(0, Integer::sum);
                })
                .map(order -> Map.entry(order, order.getItemCount().entrySet().stream()
                        .mapToLong(o -> o.getKey().getPrice() * o.getValue()).sum()))
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

