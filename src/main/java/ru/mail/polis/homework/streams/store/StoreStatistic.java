package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
        return orders.stream().filter(order -> 
                order.getItemCount().containsKey(typeItem) 
                && order.getTime().after(from) 
                && order.getTime().before(to)
        ).count();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было проданно
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(Collectors.toMap(
                order -> {
                    Timestamp t = order.getTime();
                    t.setNanos(0);
                    t.setSeconds(0);
                    t.setMinutes(0);
                    t.setHours(0);
                    return t;
                },
                Order::getItemCount,
                (map1, map2) -> {map1.putAll(map2); return map1;}                
        ));
    }

    /**
     * Вернуть самый популярный товар
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        // "однострочник" нечитаем, поэтому будет так
        Map<Item, Long> map = orders.stream().collect(
                ItemLongMapConsumer::new,
                ItemLongMapConsumer::accept,
                ItemLongMapConsumer::combine
        ).getMap();
        // вместо map.entrySet().stream() чтоб уйти от Optional там, где это возможно
        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    
    
    static class ItemLongMapConsumer implements Consumer<Order> {
        private final Map<Item, Long> map = new HashMap<>();

        @Override
        public void accept(Order t) {
            t.getItemCount().forEach((item, count) -> map.put(item, count.longValue()));
        }

        public void combine(ItemLongMapConsumer other) {
            other.map.forEach((item, count) -> map.merge(item, count, Long::sum));
        }

        public Map<Item, Long> getMap() {
            return map;
        }

    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        Map<Order, Long> map = orders.stream().collect(
                OrderLongMapConsumer::new,
                OrderLongMapConsumer::accept,
                OrderLongMapConsumer::combine
        ).getMap();
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    static class OrderLongMapConsumer implements Consumer<Order> {
        private final Map<Order, Long> map = new HashMap<>();

        @Override
        public void accept(Order t) {
            map.put(t, t.getItemCount().values().stream().mapToLong(Integer::longValue).sum());
        }

        public void combine(OrderLongMapConsumer other) {
            other.map.forEach((item, count) -> map.merge(item, count, Long::sum));
        }

        public Map<Order, Long> getMap() {
            return map;
        }

    }
    
    
}
