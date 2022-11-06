package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

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
                .filter(order -> order.getTime().getTime() <= to.getTime() && order.getTime().getTime() >= from.getTime())
                .map(Order::getItemCount)
                .filter(itemCount -> itemCount.containsKey(typeItem))
                .mapToLong(map -> map.get(typeItem))
                .sum();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было продано
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream()
                .collect(toMap(order -> Timestamp.valueOf(order.getTime().toLocalDateTime().toLocalDate().atStartOfDay()),
                        Order::getItemCount,
                        (v1, v2) -> Stream.of(v1, v2)
                                .map(Map::entrySet)
                                .flatMap(Collection::stream)
                                .collect(toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        Integer::sum))
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
                .map(Order::getItemCount)
                .flatMap(m -> m.entrySet().stream())
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum))
                .entrySet()
                .stream().min((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                .orElseThrow().getKey();
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказов.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders.stream()
                .sorted((o1, o2) -> {
                    int sum1 = o1.getItemCount().values().stream()
                            .mapToInt(integer -> integer)
                            .sum();
                    int sum2 = o2.getItemCount().values().stream()
                            .mapToInt(integer -> integer)
                            .sum();
                    return Integer.compare(sum2, sum1);
                })
                .limit(5)
                .collect(toMap(
                        Function.identity(),
                        value -> value.getItemCount().entrySet().stream()
                                .mapToLong(o -> o.getKey().getPrice() * o.getValue())
                                .sum()));
    }
}
