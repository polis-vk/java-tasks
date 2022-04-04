package ru.mail.polis.homework.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    private final Map<K, Integer> popularMapKeys = new HashMap<>();
    private final Map<V, Integer> popularMapValues = new HashMap<>();

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
        raisePopular(popularMapKeys, (K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        raisePopular(popularMapValues, (V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        raisePopular(popularMapKeys, (K) key);
        V value = map.get(key);
        raisePopular( popularMapValues, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        raisePopular(popularMapKeys, key);
        V oldValue = map.put(key, value);
        if (oldValue != null) {
            raisePopular(popularMapValues, oldValue);
        }
        raisePopular(popularMapValues, value);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        raisePopular(popularMapKeys, (K) key);
        V valueToDelete = map.remove(key);
        if(valueToDelete != null) {
            raisePopular(popularMapValues, valueToDelete);
        }
        return valueToDelete;
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
        K  mostPopular = null;
        int maxPopularity = 0;
        for (Entry<K, Integer> entry : popularMapKeys.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                maxPopularity = entry.getValue();
                mostPopular = entry.getKey();
            }
        }
        return mostPopular;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        Integer popularity = popularMapKeys.get(key);
        return popularity != null ? popularity : 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        V mostPopular = null;
        int maxPopularity = 0;
        for (Entry<V, Integer> entry : popularMapValues.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                maxPopularity = entry.getValue();
                mostPopular = entry.getKey();
            }
        }
        return mostPopular;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (popularMapValues.containsKey(value)) {
            return popularMapValues.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<Entry<V, Integer>> values = new ArrayList<>(popularMapValues.entrySet());
        List<V> result = new ArrayList<>();
        for (Entry<V, Integer> value : values) {
            result.add(value.getKey());
        }
        return result.iterator();
    }


    private <T> void raisePopular(Map<T, Integer> popularMap, T key) {
        if (popularMap.containsKey(key)) {
            popularMap.put(key, popularMap.get(key) + 1);
        } else {
            popularMap.put(key, 1);
        }
    }

}
