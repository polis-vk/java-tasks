package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
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
        return orders
                .stream()
                .filter((order -> order.getTime().compareTo(from) > 0 && order.getTime().compareTo(to) < 0))
                .map((order -> order.getItemCount().get(typeItem)))
                .count();
    }

    /**
     * Вернуть статистику по каждому дню сколько какого товара было продано
     *
     * @param orders - список заказов
     * @return - map, где ключ - начало дня (00:00:00 000 UTC) для которого собрана статистика
     * значение - map товар/кол-во
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders.stream().collect(Collectors
                .toMap(Order::getTime, Order::getItemCount, (o1, o2) -> {
                    o2.forEach((item, count) -> {
                        o1.computeIfPresent(item, ((item1, integer) -> integer + count));
                        o1.putIfAbsent(item, count);
                    });
                    return o1;
                }));
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders
                .stream()
                .map(order -> order.getItemCount().entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                .entrySet()
                .stream()
                .reduce((firstPair, secondPair) -> firstPair.getValue().compareTo(secondPair.getValue()) > 0 ? firstPair : secondPair)
                .get().getKey();


    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders
                .stream()
                .collect(Collectors.toMap(order -> order, Order::getItemCount))
                .entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    int first = o2.getValue().values().stream().mapToInt(i -> i).sum();
                    int second = o1.getValue().values().stream().mapToInt(i -> i).sum();
                    return Integer.compare(first, second);
                })
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, (entry) -> entry.getValue().entrySet().stream()
                                .mapToLong(entry1 -> entry1.getKey().getPrice() * entry1.getValue()).sum()));


    }

    public static void main(String[] args) {
        //Map<Timestamp, Map<Item, Integer>> test = new HashMap<>();
        Timestamp timestamp1 = Timestamp.valueOf("2021-12-01 13:56:49.9363995");
        //Timestamp timestamp2 = Timestamp.valueOf("2021-12-01 13:57:34.1447382");
        Timestamp timestamp3 = Timestamp.valueOf("2021-12-01 14:00:11.655733");
        Item item1 = new Item("earlier", 1);
        Item item2 = new Item("bitLater", 2);
        Item item3 = new Item("later", 3);
        Item item4 = new Item("longLater", 4);
        Item item5 = new Item("afterAll", 5);
        //Timestamp timestamp = Timestamp.from(Instant.now());
        Map<Item, Integer> hm1 = new HashMap<>();
        hm1.put(item1, 1);
        hm1.put(item2, 0);
        hm1.put(item3, 2);
        hm1.put(item4, 2);
        Order order1 = new Order(timestamp1, hm1);
        System.out.printf("or1, hash:%s\n", order1);
        Map<Item, Integer> hm2 = new HashMap<>();
        hm2.put(item1, 2);
        hm2.put(item2, 1);
        hm2.put(item3, 1);
        hm2.put(item5, 4);
        Order order2 = new Order(timestamp1, hm2);
        System.out.printf("or2, hash:%s\n", order2);
        Map<Item, Integer> hm3 = new HashMap<>();
        hm3.put(item1, 3);
        hm3.put(item2, 6);
        hm3.put(item3, 5);
        Order order3 = new Order(timestamp3, hm3);
        System.out.printf("or3, hash:%s\n", order3);
        Map<Item, Integer> hm4 = new HashMap<>();
        hm4.put(item3, 3);
        hm4.put(item4, 2);
        hm4.put(item1, 5);
        Order order4 = new Order(timestamp1, hm4);
        System.out.printf("or4, hash:%s\n", order4);
        StoreStatistic st = new StoreStatistic();
        System.out.println(st.sum5biggerOrders(List.of(order1, order2, order3, order4)));
    }
}
