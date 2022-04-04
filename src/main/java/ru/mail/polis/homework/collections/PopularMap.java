package ru.mail.polis.homework.collections;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;


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

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularMap = new HashMap<>();
    private final Map<V, Integer> valuePopularMap = new HashMap<>();

    private K popularKey;
    private V popularValue;

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
        increasingPopularityKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasingPopularityValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increasingPopularityKey((K) key);
        V value = map.get(key);
        increasingPopularityValue(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increasingPopularityKey(key);
        increasingPopularityValue(value);
        V prevValue = map.put(key, value);
        increasingPopularityValue(prevValue);
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        increasingPopularityKey((K) key);
        V prevValue = map.remove(key);
        increasingPopularityValue(prevValue);
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
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularMap.getOrDefault(key, 0);
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
        return valuePopularMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(valuePopularMap.keySet());
        values.sort(Comparator.comparingInt(valuePopularMap::get));
        return values.iterator();
    }

    private void increasingPopularityKey(K key) {
        if (key != null) {
            int popularity = keyPopularMap.merge(key, 1, Integer::sum);
            if (!key.equals(popularKey)) {
                if (popularKey == null || keyPopularMap.get(popularKey) < popularity) {
                    popularKey = key;
                }
            }
        }
    }

    private void increasingPopularityValue(V value) {
        if (value != null) {
            int popularity = valuePopularMap.merge(value, 1, Integer::sum);
            if (!value.equals(popularValue)) {
                if (popularValue == null || valuePopularMap.get(popularValue) < popularity) {
                    popularValue = value;
                }
            }
        }
    }
}
