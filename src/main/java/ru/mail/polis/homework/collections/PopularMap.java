package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 * <p>
 * Так же надо сделать итератор (подробности ниже).
 * <p>
 * Важный момент, вам не надо реализовывать мапу, вы должны использовать композицию.
 * Вы можете использовать любые коллекции, которые есть в java.
 * <p>
 * Помните, что по мапе тоже можно итерироваться
 * <p>
 * for (Map.Entry<K, V> entry : map.entrySet()) {
 * entry.getKey();
 * entry.getValue();
 * }
 * <p>
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, Integer> popularKey;
    private final Map<V, Integer> popularValue;
    private final Map<K, V> map;

    private K mostPopularKey;
    private V mostPopularValue;

    public PopularMap() {
        this(new HashMap<>());
    }

    public PopularMap(Map<K, V> map) {
        this.popularKey = new HashMap<>();
        this.popularValue = new HashMap<>();
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        mostPopularKey = popularity((K) key, popularKey, mostPopularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        mostPopularValue = popularity((V) value, popularValue, mostPopularValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        mostPopularKey = popularity((K) key, popularKey, mostPopularKey);
        V value = map.get(key);
        mostPopularValue = popularity(value, popularValue, mostPopularValue);
        return value;
    }

    @Override
    public V put(K key, V value) {
        mostPopularKey = popularity(key, popularKey, mostPopularKey);
        mostPopularValue = popularity(value, popularValue, mostPopularValue);
        V oldValue = map.put(key, value);
        mostPopularValue = popularity(oldValue, popularValue, mostPopularValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        mostPopularKey = popularity((K) key, popularKey, mostPopularKey);
        mostPopularValue = popularity(map.get(key), popularValue, mostPopularValue);
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKey.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValue.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<V> {

        List<V> popularList = new ArrayList<>();

        public CustomIterator() {
            popularList = popularValue.entrySet().stream()
                    .sorted(Comparator.comparingInt(value -> value.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean hasNext() {
            return !popularList.isEmpty();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return popularList.remove(0);
        }
    }

    private <T> T popularity(T element, Map<T, Integer> mapPopularity, T popularElement) {
        if (element == null) {
            return popularElement;
        }
        mapPopularity.compute(element, (key, value) -> value == null ? 1 : value + 1);
        if (popularElement == null) {
            return element;
        }
        return mapPopularity.get(element) > mapPopularity.get(popularElement) ? element : popularElement;
    }
}
