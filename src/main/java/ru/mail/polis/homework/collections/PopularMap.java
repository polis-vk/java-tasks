package ru.mail.polis.homework.collections;


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

    private Map<K, V> map;
    private final Map<K, Integer> popularKeys;
    private final Map<V, Integer> popularValues;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularKeys = new HashMap<>();
        this.popularValues = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this();
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
        increaseKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increaseValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        increaseKeyPopularity((K) key);
        increaseValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V prevValue = map.put(key, value);
        increaseKeyPopularity((K) key);
        increaseValuePopularity(prevValue);
        increaseValuePopularity(value);
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity((K) key);
        increaseValuePopularity(map.get(key));
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
        K popularKey = null;
        int popularity = 0;
        for (K key : popularKeys.keySet()) {
            if (getKeyPopularity(key) > popularity) {
                popularity = getKeyPopularity(key);
                popularKey = key;
            }
        }
        return popularKey;
    }

    private void increaseValuePopularity(V value) {
        if (value == null) {
            return;
        }
        popularValues.put((V) value, popularValues.getOrDefault(value, 0) + 1);
    }

    private void increaseKeyPopularity(K key) {
        popularKeys.put((K) key, popularKeys.getOrDefault(key, 0) + 1);
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
        V popularValue = null;
        int popularity = 0;
        for (Map.Entry<V, Integer> entry : popularValues.entrySet()) {
            if (entry.getValue() > popularity) {
                popularValue = entry.getKey();
            }
        }
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
        return popularValues.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .iterator();
    }
}
