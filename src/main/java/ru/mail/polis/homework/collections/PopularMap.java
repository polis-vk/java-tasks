package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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
    private final Map<K, Integer> popularKeys = new HashMap<>();
    private K popularKey = null;
    private final Map<V, Integer> popularValues = new HashMap<>();
    private V popularValue = null;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        addPopularAndComparing(popularKeys, (K) key, popularKey, true);

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularAndComparing(popularValues, (V) value, popularValue, false);

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addPopularAndComparing(popularKeys, (K) key, popularKey, true);

        V value = map.get(key);
        addPopularAndComparing(popularValues, value, popularValue, false);

        return value;
    }

    @Override
    public V put(K key, V value) {
        addPopularAndComparing(popularKeys, key, popularKey, true);

        V oldValue = map.put(key, value);
        addPopularAndComparing(popularValues, value, popularValue, false);
        addPopularAndComparing(popularValues, oldValue, popularValue, false);

        return oldValue;
    }

    @Override
    public V remove(Object key) {
        addPopularAndComparing(popularKeys, (K) key, popularKey, true);

        V removeValue = map.remove(key);
        addPopularAndComparing(popularValues, removeValue, popularValue, false);

        return removeValue;
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

    private <T> void addPopularAndComparing(Map<T, Integer> mapPopular, T value, T comparingValue, boolean isKey) {
        if (value == null) {
            return;
        }

        mapPopular.put(value, mapPopular.getOrDefault(value, 0) + 1);

        if (mapPopular.getOrDefault(comparingValue, 0) < mapPopular.get(value)) {
            if (isKey) {
                popularKey = (K) value;
            } else {
                popularValue = (V) value;
            }
        }
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return popularValues.keySet().stream()
                .sorted(Comparator.comparingInt(popularValues::get))
                .collect(Collectors.toList())
                .iterator();
    }
}
