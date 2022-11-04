package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
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
                .filter(order -> order.getTime().compareTo(from) >= 0 && order.getTime().compareTo(to) <= 0)
                .map(order -> order.getItemCount().entrySet().stream()
                        .filter(entry -> entry.getKey().equals(typeItem))
                        .map(entry -> (long) entry.getValue())
                        .reduce(0L, Long::sum))
                .reduce(0L, Long::sum);
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(order -> Timestamp.valueOf(order.getTime().toLocalDateTime().truncatedTo(ChronoUnit.DAYS)))).entrySet().stream()
                .map(orderEntry -> new Pair<>(orderEntry.getKey(), orderEntry.getValue().stream()
                        .map(Order::getItemCount)
                        .flatMap(itemCount -> itemCount.entrySet().stream())
                        .collect(Collectors.groupingBy(Map.Entry::getKey,
                                Collectors.summingInt(Map.Entry::getValue)))))
                .collect(Collectors.toMap(Pair::getA, Pair::getB));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .map(Order::getItemCount)
                .flatMap(itemCount -> itemCount.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue))).entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream().sorted((a, b) -> (int) (orderItemCountSum(b) - orderItemCountSum(a)))
                .limit(5)
                .collect(Collectors.toMap(order -> order, order -> order.getItemCount().entrySet().stream()
                        .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue()).sum()));
    }

    private Long orderItemCountSum(Order order) {
        return order.getItemCount().values().stream().mapToLong(x -> (long) x).sum();
    }

    private static class Pair<T1, T2> {
        private final T1 a;
        private final T2 b;

        public Pair(T1 a, T2 b) {
            this.a = a;
            this.b = b;
        }

        public T1 getA() {
            return a;
        }

        public T2 getB() {
            return b;
        }
    }
}
