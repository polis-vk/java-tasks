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

    private final Map<K, Integer> popularKey = new HashMap<>();
    private final Map<V, Integer> popularValue = new HashMap<>();
    private final Map<K, V> map;

    private <T> void popularity(T value, Map<T, Integer> popular) {
        if (value != null) {
            popular.putIfAbsent(value, 0);
            popular.put(value, popular.get(value) + 1);
        }
    }

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
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
        popularity((K) key, popularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        popularity(value, popularValue);
        popularity((K) key, popularKey);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V prevValue = map.put(key, value);
        popularity(key, popularKey);
        popularity(value, popularValue);
        popularity(prevValue, popularValue);
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        V prevValue = map.remove(key);
        popularity((K) key, popularKey);
        popularity(prevValue, popularValue);
        return prevValue;
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
        return popularKey.entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        V result = (V) popularKey.get(key);
        if (result != null) {
            return (int) result;
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue.entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey)
                .orElse(null);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        V result = (V) popularValue.get(value);
        if (result != null) {
            return (int) result;
        }
        return 0;
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
}
