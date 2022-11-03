package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        return orders.stream().filter(o -> (o.getTime().equals(from) || o.getTime().after(from)) && (o.getTime().equals(to) || o.getTime().before(to)))
                .mapToLong(o -> o.getItemCount().getOrDefault(typeItem, 0))
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
        return null;
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream().flatMap(map -> map.getItemCount().entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.toMap(k -> k, v -> v.getItemCount().entrySet().stream().mapToLong(o -> o.getValue() * o.getKey().getPrice()).sum()))
                .entrySet().stream()
                .sorted((a, b) -> getAmount(b) - getAmount(a))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private int getAmount(Map.Entry< Order, Long > map) {
        return map.getKey().getItemCount().values().stream().mapToInt(v -> v).sum();
    }
}
