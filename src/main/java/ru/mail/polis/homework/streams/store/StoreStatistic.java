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
                .filter(i -> i.getTime().compareTo(from) >= 0 && i.getTime().compareTo(to) <= 0)
                .mapToLong(i -> i.getItemCount().get(typeItem) == null ? 0 : i.getItemCount().get(typeItem))
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
                .collect(Collectors.toMap(i -> dayStart(i.getTime().toString()), Order::getItemCount));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
         return orders.stream().flatMap(i -> i.getItemCount().entrySet().stream())
                .max(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow(NullPointerException::new).getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().collect(Collectors.toMap(i -> i, i -> i.getItemCount().values().stream()
                .mapToLong(j -> j).sum())).entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .limit(5).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Timestamp dayStart(String date) {
        return Timestamp.valueOf(date.substring(0,10) + " 00:00:00");
    }
}
