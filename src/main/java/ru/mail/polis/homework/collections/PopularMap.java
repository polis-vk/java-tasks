package ru.mail.polis.homework.collections;


import java.util.Collection;
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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularityMap;
    private final Map<V, Integer> valuePopularityMap;

    public PopularMap() {
        this.map = new HashMap<>();
        keyPopularityMap = new HashMap<>();
        valuePopularityMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keyPopularityMap = new HashMap<>();
        valuePopularityMap = new HashMap<>();
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
        incKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        incValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        incKeyPopularity((K) key);
        V value = map.get(key);
        incValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        incKeyPopularity(key);
        incValuePopularity(value);
        V oldValue = map.put(key, value);
        incValuePopularity(oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        incKeyPopularity((K) key);
        V removedValue = map.remove(key);
        incValuePopularity(removedValue);
        return removedValue;
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
     * 1 балл
     */
    public K getPopularKey() {
        return keyPopularityMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        Integer keyPopularity = keyPopularityMap.get(key);
        return keyPopularity == null ? 0 : keyPopularity;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return valuePopularityMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        Integer valuePopularity = valuePopularityMap.get(value);
        return valuePopularity == null ? 0 : valuePopularity;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        return valuePopularityMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Entry::getKey)
                .iterator();
    }

    private void incKeyPopularity(K key) {
        if (key == null) {
            return;
        }

        if (keyPopularityMap.containsKey(key)) {
            keyPopularityMap.put(key, keyPopularityMap.get(key) + 1);
        } else {
            keyPopularityMap.put(key, 1);
        }
    }

    private void incValuePopularity(V value) {
        if (value == null) {
            return;
        }

        if (valuePopularityMap.containsKey(value)) {
            valuePopularityMap.put(value, valuePopularityMap.get(value) + 1);
        } else {
            valuePopularityMap.put(value, 1);
        }
    }
}
