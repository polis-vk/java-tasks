package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.*;


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
    private final Map<K, Integer> popularKeyList = new HashMap<>();
    private final Map<K, Integer> popularValueList = new HashMap<>();

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
        popularKeyList.put((K) key, popularKeyList.getOrDefault(key, 0) + 1);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        popularValueList.put((K) value, popularValueList.getOrDefault(value, 0) + 1);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        popularKeyList.put((K) key, popularKeyList.getOrDefault(key, 0) + 1);
        V value = map.get(key);
        popularValueList.put((K) value, popularValueList.getOrDefault(value, 0) + 1);
        return value;
    }

    @Override
    public V put(K key, V value) {
        popularKeyList.put(key, popularKeyList.getOrDefault(key, 0) + 1);
        V oldValue = map.get(key);
        popularValueList.put((K) value, popularValueList.getOrDefault(value, 0) + 1);
        popularValueList.put((K) oldValue, popularValueList.getOrDefault(oldValue, 0) + 1);
        map.put(key, value);
        return map.get(key);
    }

    @Override
    public V remove(Object key) {
        popularKeyList.put((K) key, popularKeyList.getOrDefault(key, 0) + 1);
        V value = map.remove(key);
        popularValueList.put((K) value, popularValueList.getOrDefault(value, 0) + 1);
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
        return popularKeyList
                .entrySet()
                .stream()
                .filter(x -> x.getKey() != null)
                .max(comparingByValue())
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!popularKeyList.containsKey(key)) {
            return 0;
        }
        return popularKeyList.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return (V) popularValueList
                .entrySet()
                .stream()
                .filter(x -> x.getKey() != null)
                .max(comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!popularValueList.containsKey(value)) {
            return 0;
        }
        return popularValueList.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return new Iterator<V>() {
            final List<V> currentMap = (List<V>) popularValueList
                    .entrySet()
                    .stream()
                    .map(Entry::getKey)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < currentMap.size();
            }

            @Override
            public V next() {
                if (hasNext()) {
                    return currentMap.get(index++);
                }
                return null;
            }
        };
    }
}
