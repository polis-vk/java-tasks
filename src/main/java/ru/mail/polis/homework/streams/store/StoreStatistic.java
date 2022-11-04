package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.Calendar;
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
                .filter(order -> (order.getItemCount().get(typeItem) != null
                        && (order.getTime().equals(from)
                        || order.getTime().equals(to)
                        || order.getTime().after(from) && order.getTime().before(to))))
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
                .peek(order -> getDateFromTimestamp(order.getTime()))
                .collect(Collectors.toMap(
                        order -> getDateFromTimestamp(order.getTime()),
                        Order::getItemCount,
                        (o1, o2) -> {
                            o2.forEach((key, value) -> o1.merge(key, value, Integer::sum));
                            return o1;
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
                .flatMap(order -> order.getItemCount().entrySet().stream())
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
                .sorted(Comparator.comparingLong(order -> -order.getItemCount()
                        .entrySet().stream()
                        .mapToLong(Map.Entry::getValue)
                        .sum()))
                .limit(5)
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getItemCount()
                                .entrySet().stream()
                                .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue())
                                .sum()
                ));
    }

    private Timestamp getDateFromTimestamp(Timestamp from) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(from.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }


}
