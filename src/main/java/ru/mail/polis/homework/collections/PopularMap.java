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
    private final Map<K, Integer> popularKeyMap = new HashMap<>();
    private final Map<V, Integer> popularValueMap = new HashMap<>();


    private K mostPopularKey;
    private V mostPopularValue;
    private int quantityPopularKey = Integer.MIN_VALUE;
    private int quantityPopularValue = Integer.MIN_VALUE;


    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    private void keyCounting(K key) {
        int popularityNewKey = popularKeyMap.merge(key, 1, (prev, one) -> prev + one);
        if (popularityNewKey > quantityPopularKey) {
            mostPopularKey = key;
            quantityPopularKey = popularityNewKey;
        }
    }

    private void valueCounting(V value) {
        int popularityNewValue = popularValueMap.merge(value, 1, (prev, one) -> prev + one);
        if (popularityNewValue > quantityPopularValue) {
            mostPopularValue = value;
            quantityPopularValue = popularityNewValue;
        }
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
        keyCounting((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        valueCounting((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V currentValue = map.get(key);
        if (currentValue != null) {
            valueCounting(currentValue);
        }
        keyCounting((K) key);
        return currentValue;
    }

    @Override
    public V put(K key, V value) {
        V currentValue = map.put(key, value);
        if (currentValue != null) {
            valueCounting(currentValue);
        }
        valueCounting(value);
        keyCounting(key);
        return currentValue;
    }

    @Override
    public V remove(Object key) {
        V currentValue = map.remove(key);
        if (currentValue != null) {
            valueCounting(currentValue);
        }
        keyCounting((K) key);
        return currentValue;
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
        return popularKeyMap.getOrDefault(key, 0);
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
        return popularValueMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return popularValueMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).iterator();
    }
}
