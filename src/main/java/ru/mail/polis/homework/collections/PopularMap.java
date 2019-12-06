package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
    private final Map<K, Integer> popularKeyMap;
    private final Map<V, Integer> popularValueMap;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularKeyMap = new HashMap<>();
        this.popularValueMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularKeyMap = new HashMap<>();
        this.popularValueMap = new HashMap<>();
    }

    //Utility methods
    private void setKeyPopularity(K key) {
        Integer popularity = this.popularKeyMap.get(key);

        if (popularity == null) {
            this.popularKeyMap.put(key, 1);
        }
        else {
            this.popularKeyMap.put(key, popularity + 1);
        }
    }

    private void setValuePopularity(V value) {
        Integer popularity = this.popularValueMap.get(value);

        if (popularity == null) {
            this.popularValueMap.put(value, 1);
        }
        else {
            this.popularValueMap.put(value, popularity + 1);
        }
    }



    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        setKeyPopularity((K) key);
        V value = this.map.get(key);
        setValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        setValuePopularity(value);
        setKeyPopularity(key);
        V oldValue = this.map.get(key);

        if (oldValue != null && oldValue.equals(value)) {
            setValuePopularity(value);
            return value;
        }

        return this.map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        System.out.println("key: " + key);
        setKeyPopularity((K) key);
        V value = this.map.remove(key);
        System.out.println("value: " + value);
        if (value != null) {
            setValuePopularity(value);
            return this.map.remove(key);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
              setKeyPopularity(entry.getKey());
              setValuePopularity(entry.getValue());
        }

        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return popularKeyMap
                .keySet()
                .stream()
                .max((key_1, key_2) -> {
                    Integer value_1 = popularKeyMap.get(key_1);
                    Integer value_2 = popularKeyMap.get(key_2);
                    return value_1.compareTo(value_2);
                })
                .get();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        Integer popularity = popularKeyMap.get(key);
        return popularity == null ? 0 : popularity;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValueMap
                .keySet()
                .stream()
                .max((key_1, key_2) -> {
                    Integer value_1 = popularValueMap.get(key_1);
                    Integer value_2 = popularValueMap.get(key_2);
                    return value_1.compareTo(value_2);
                })
                .get();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        Integer popularity = popularValueMap.get(value);
        return popularity == null ? 0 : popularity;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        // TODO implement the iterator
        return popularValueMap
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }
}
