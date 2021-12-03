package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс для работы со статистикой по заказам магазина.
 * Оценка 5 баллов
 */
public class StoreStatistic {

    public static final Collector<Order, ?, Map<Item, Integer>> SUMMING_COUNTS_BY_ITEM = flatMapping(
            StoreStatistic::itemCountsStream,
            Collectors.groupingBy(
                    Map.Entry::getKey,
                    Collectors.summingInt(Map.Entry::getValue)));

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
                .filter(isPurchasedBetween(from, to))
                .flatMap(StoreStatistic::itemCountsStream)
                .filter(e -> e.getKey().equals(typeItem))
                .mapToInt(Map.Entry::getValue)
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
        return orders.stream()
                .collect(Collectors.groupingBy(StoreStatistic::toDayStart, SUMMING_COUNTS_BY_ITEM));
    }

    /**
     * Вернуть самый популярный товар
     *
     * @param orders - список заказов
     * @return - товар
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders.stream()
                .collect(SUMMING_COUNTS_BY_ITEM)
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Вернуть сумму для 5 самых больших по кол-ву товаров заказу.
     *
     * @param orders - список заказов
     * @return map - заказ / общая сумма заказа
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        final int topCount = 5;
        Map<Order, Long> result = new HashMap<>();
        result.entrySet().addAll(orders.stream()
                .map(o -> entry(o, itemCountsStream(o).mapToLong(Map.Entry::getValue).sum()))
                .collect(Collectors.toCollection(
                        () -> new TopNPriorityQueue<>(topCount, Map.Entry.comparingByValue()))));
        return result;
    }

    private static Timestamp toDayStart(Order order) {
        return Timestamp.valueOf(order.getTime().toLocalDateTime().with(LocalTime.MIN));
    }

    private static Stream<? extends Map.Entry<Item, Integer>> itemCountsStream(Order order) {
        return order.getItemCount().entrySet().stream();
    }

    private static Predicate<Order> isPurchasedBetween(Timestamp from, Timestamp to) {
        return order -> order.getTime().after(from) && order.getTime().before(to);
    }

    /**
     * Backport of {@link Collectors#flatMapping} which is since Java 9
     */
    private static <T, U, A, R>
    Collector<T, ?, R> flatMapping(Function<? super T, ? extends Stream<? extends U>> mapper,
                                   Collector<? super U, A, R> downstream) {
        BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
        return Collector.of(downstream.supplier(),
                (r, t) -> {
                    try (Stream<? extends U> result = mapper.apply(t)) {
                        if (result != null)
                            result.sequential().forEach(u -> downstreamAccumulator.accept(r, u));
                    }
                },
                downstream.combiner(), downstream.finisher(),
                downstream.characteristics().toArray(new Collector.Characteristics[0]));
    }

    /**
     * Backport of {@link Map#entry} which is since Java 9
     */
    private static <K, V> Map.Entry<K, V> entry(K k, V v) {
        // KeyValueHolder checks for nulls
        return new KeyValueHolder<>(k, v);
    }

    /**
     * Backport of {@link java.util.KeyValueHolder} which is since Java 9
     */
    private static final class KeyValueHolder<K, V> implements Map.Entry<K, V> {
        final K key;
        final V value;

        KeyValueHolder(K key, V value) {
            this.key = Objects.requireNonNull(key);
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("not supported");
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            return key.equals(e.getKey()) && value.equals(e.getValue());
        }

        @Override
        public int hashCode() {
            return key.hashCode() ^ value.hashCode();
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * PriorityQueue, but contains <b>first</b> {@code topCount} largest elements of all added
     *
     * @param <E> the type of elements held in this queue
     */
    private static class TopNPriorityQueue<E> extends PriorityQueue<E> {
        final int topCount;

        TopNPriorityQueue(int topCount, Comparator<? super E> comparator) {
            super(topCount, comparator);
            this.topCount = topCount;
        }

        @Override
        public boolean add(E e) {
            if (size() >= topCount && comparator().compare(peek(), e) >= 0) {
                return false;
            }
            if (size() == topCount) {
                remove();
            }
            return super.add(e);
        }
    }
}
