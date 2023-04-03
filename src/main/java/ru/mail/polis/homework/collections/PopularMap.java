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
    private final Map<K, Integer> keyPopMap = new HashMap<>();
    private final Map<V, Integer> valuePopMap = new HashMap<>();
    private K mostPopKey;
    private V mostPopValue;
    private int maxQuantityPopKey;
    private int maxQuantityPopValue;


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
        increasePopKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        if (value != null) {
            increasePopValue(value);
        }
        increasePopKey((K) key);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V prevValue = map.put(key, value);
        if (prevValue != null) {
            increasePopValue(prevValue);
        }
        increasePopValue(value);
        increasePopKey(key);
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        if (value != null) {
            increasePopValue(value);
        }
        increasePopKey((K) key);
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
        return mostPopKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return mostPopValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuePopMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Entry::getKey).iterator();
    }

    private void increasePopValue(V value) {
        int valuePop = valuePopMap.getOrDefault(value, 0) + 1;
        if (valuePop > maxQuantityPopValue) {
            maxQuantityPopValue = valuePop;
            mostPopValue = value;
        }
        valuePopMap.put(value, valuePop);
    }

    private void increasePopKey(K key) {
        int keyPop = keyPopMap.getOrDefault(key, 0) + 1;
        if (keyPop > maxQuantityPopKey) {
            maxQuantityPopKey = keyPop;
            mostPopKey = key;
        }
        keyPopMap.put(key, keyPop);
    }
}
