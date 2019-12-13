package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 *
 * Дополнительное задание описано будет ниже
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> popularKey;
    private final Map<V, Integer> popularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularKey = new HashMap<>();
        this.popularValue = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularKey = new HashMap<>();
        this.popularValue = new HashMap<>();
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
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addPopularUseForKey((K) key);
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            addPopularUseForValue(map.get(key));
        }
        addPopularUseForKey(key);
        addPopularUseForValue(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        addPopularUseForKey((K) key);
        V rm = map.remove(key);
        if (popularValue.containsKey(rm) && Objects.nonNull(rm)) addPopularUseForValue(rm);
        return rm;
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

    private void addPopularUseForKey(K key) {
        if (popularKey.containsValue(key)) {
            Integer popular = popularKey.get(key);
            popularKey.put(key, popular);
        } else {
            popularKey.put(key, 1);
        }
    }

    private void addPopularUseForValue(V value) {
        if (popularValue.containsValue(value)) {
            Integer popular = popularValue.get(value);
            popularValue.put(value, popular);
        } else {
            popularValue.put(value, 0); // value 1
        }
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        K key = popularKey
                .entrySet()
                .stream()
                .max((v1, v2) -> {
                    if (v1.getValue().equals(v2.getValue())) return 0;
                    else if (v1.getValue() > v2.getValue()) return 1;
                    else return -1;
                })
                .get()
                .getKey();
        return key;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (popularKey.containsKey(key)) {
            return popularKey.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        V value = popularValue
                .entrySet()
                .stream()
                .max((v1, v2) -> {
                    if (v1.getValue().equals(v2.getValue())) return 0;
                    else if (v1.getValue() > v2.getValue()) return 1;
                    else return -1;
                })
                .get()
                .getKey();
        return value;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (popularValue.containsKey(value)) {
            return popularValue.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        Iterator<V> iterator = popularValue.entrySet()
                .stream()
                .sorted((v1, v2) -> {
                    if (v1.getValue().equals(v2.getValue())) return 0;
                    else if (v1.getValue() > v2.getValue()) return 1;
                    else return -1;
                })
                .map(Entry::getKey)
                .iterator();
        return iterator;
    }
}
