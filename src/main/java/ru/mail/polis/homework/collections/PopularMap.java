package ru.mail.polis.homework.collections;


import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 *
 * Так же надо сделать итератор (подробности ниже).
 *
 * Важный момент, вам не надо реализовывать мапу, вы должны использовать композицию.
 * Вы можете использовать любые коллекции, которые есть в java.
 *
 * Помните, что по мапе тоже можно итерироваться
 *
 *         for (Map.Entry<K, V> entry : map.entrySet()) {
 *             entry.getKey();
 *             entry.getValue();
 *         }
 *
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> popularityKeysMap = new HashMap<>();
    private final Map<V, Integer> popularityValuesMap = new HashMap<>();

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
        increasePopularity(new AbstractMap.SimpleEntry<>((K) key, null));
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity(new AbstractMap.SimpleEntry<>(null, (V) value));
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        increasePopularity(new AbstractMap.SimpleEntry<>((K) key, value));
        return value;
    }

    @Override
    public V put(K key, V value) {
        increasePopularity(new AbstractMap.SimpleEntry<>(key, value));
        V oldValue = map.put(key, value);
        increasePopularity(new AbstractMap.SimpleEntry<>(null, oldValue));
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        increasePopularity(new AbstractMap.SimpleEntry<>((K) key, value));
        return value;
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
        Entry<K, Integer> maxKeyEntry = popularityKeysMap.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .orElse(null);
        return maxKeyEntry != null ? maxKeyEntry.getKey() : null;
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularityKeysMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        Entry<V, Integer> maxValueEntry = popularityValuesMap.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .orElse(null);
        return maxValueEntry != null ? maxValueEntry.getKey() : null;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularityValuesMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return popularityValuesMap.keySet()
                .stream()
                .sorted(Comparator.comparingInt(this::getValuePopularity))
                .iterator();
    }

    private void increasePopularity(Entry<K, V> mapEntry) {
        if (mapEntry == null) {
            return;
        }

        K key = mapEntry.getKey();
        if (key != null) {
            popularityKeysMap.merge(key, 1, Integer::sum);
        }

        V value = mapEntry.getValue();
        if (value != null) {
            popularityValuesMap.merge(value, 1, Integer::sum);
        }
    }
}
