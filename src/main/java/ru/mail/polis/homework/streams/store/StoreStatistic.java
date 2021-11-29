package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return orders.stream().
                filter(x -> x.getTime().compareTo(from) >= 0 && x.getTime().compareTo(to) < 0 && x.getItemCount().containsKey(typeItem)).
                mapToLong(x -> x.getItemCount().get(typeItem))
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
        Map<Timestamp, Map<Item, Integer>> map = new HashMap<>();
        orders.stream().sorted(Comparator.comparing(Order::getTime));
        return null;
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        orders.stream();
        return null;
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return null;
    }
}
