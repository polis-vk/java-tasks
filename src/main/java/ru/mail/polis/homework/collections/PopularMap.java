package ru.mail.polis.homework.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    private class PairKeyValue {
        private K key;
        private V value;

        PairKeyValue(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PairKeyValue pairKeyValue = (PairKeyValue) o;
            return pairKeyValue.key.equals(key) && pairKeyValue.value.equals(value);
        }
    }

    private final Map<K, V> map;
    private final Map<K, Integer> usedKeyMap;
    private final Map<V, Integer> usedValueMap;
    private final List<PairKeyValue> list;

    public PopularMap() {
        this.map = new HashMap<>();
        usedKeyMap = new HashMap<>();
        usedValueMap = new HashMap<>();
        list = new ArrayList<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        usedKeyMap = new HashMap<>();
        usedValueMap = new HashMap<>();
        list = new ArrayList<>();
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
        updateKey((K) key);
        if (map.containsKey(key)) {
            updateValue(map.get(key));
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        updateValue((V) value);
        for (K key : map.keySet()) {
            if (map.get(key).equals(value)) {
                updateKey(key);
            }
        }
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updateKey((K) key);
        if (map.containsKey(key)) {
            updateValue(map.get(key));
            return map.get(key);
        }
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        list.add(new PairKeyValue(key, value));
        updateKey(key);
        updateValue(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        updateKey((K) key);
        List<V> removedValues = new ArrayList<>();
        if (map.containsKey(key)) {
            removeValues(removedValues, (K) key);
            V removedValue = map.remove(key);
            list.remove(new PairKeyValue((K) key, removedValue));
            return removedValue;
        }
        removeValues(removedValues, (K) key);
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
        return usedKeyMap.entrySet().stream().max(Comparator.comparing(Entry::getValue)).get().getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return usedKeyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int max = 0;
        V maxValue = null;
        for (Map.Entry<V, Integer> entry : usedValueMap.entrySet()) {
            if (entry.getValue() >= max) {
                max = entry.getValue();
                maxValue = entry.getKey();
            }
        }
        return maxValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return usedValueMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return usedValueMap
            .entrySet()
            .stream()
            .sorted(Comparator.comparing(Entry::getValue))
            .map(Entry::getKey)
            .collect(Collectors.toList())
            .iterator();
    }

    private void updateKey(K key) {
        if (usedKeyMap.containsKey(key)) {
            usedKeyMap.put(key, usedKeyMap.get(key) + 1);
        } else {
            usedKeyMap.put(key, 1);
        }
    }

    private void updateValue(V value) {
        if (usedValueMap.containsKey(value)) {
            usedValueMap.put(value, usedValueMap.get(value) + 1);
        } else {
            usedValueMap.put(value, 1);
        }
    }

    private void removeValues(List<V> removedValues, K key) {
        for (PairKeyValue pairKeyValue : list) {
            if (pairKeyValue.key.equals(key)) {
                updateValue(pairKeyValue.value);
                removedValues.add(pairKeyValue.value);
            }
        }
        removedValues.forEach(value -> list.remove(new PairKeyValue((K) key, value)));
    }
}
