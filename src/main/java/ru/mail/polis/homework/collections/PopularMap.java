package ru.mail.polis.homework.collections;


import java.util.*;


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


    private static class ObservedElement<T> implements Comparable {
        T value;
        Integer count;

        ObservedElement(T value) {
            this.value = value;
            this.count = value == null ? 0 : 1;
        }

        @Override
        public int compareTo(Object o) {
            return count.compareTo(((ObservedElement) o).count);
        }
    }

    private final Map<K, V> map;

    private Map<K, Integer> keyReferenceCount;
    private ObservedElement<K> maxKeyReferenceCount;

    private Map<V, ObservedElement<V>> valueReferenceCount;
    private NavigableSet<ObservedElement<V>> valueReferenceCountSet;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyReferenceCount = new HashMap<>();
        maxKeyReferenceCount = new ObservedElement<>(null);

        this.valueReferenceCount = new HashMap<>();
        this.valueReferenceCountSet = new TreeSet<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyReferenceCount = new HashMap<>();
        maxKeyReferenceCount = new ObservedElement<>(null);

        this.valueReferenceCount = new HashMap<>();
        this.valueReferenceCountSet = new TreeSet<>();
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
        updateKeyPopularity(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValuePopularity(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updateKeyPopularity(key);
        V value = map.get(key);
        if (value != null) {
            updateValuePopularity(value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        updateKeyPopularity(key);
        V oldValue = map.put(key, value);
        if (oldValue != null) {
            updateValuePopularity(oldValue);
        }
        updateValuePopularity(value);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        updateKeyPopularity(key);

        V value = map.remove(key);
        if (value != null) {
            updateValuePopularity(value);
        }
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
        return maxKeyReferenceCount.value;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyReferenceCount.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return valueReferenceCountSet.last().value;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        final ObservedElement result = valueReferenceCount.get(value);
        if (result != null) {
            return result.count;
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valueReferenceCountSet
                .stream()
                .map((vObservedElement -> vObservedElement.value))
                .iterator();
    }

    private void updateKeyPopularity(Object key) {
        keyReferenceCount.compute((K) key, (k, element) -> {
            if (element != null) {
                element++;
            } else {
                element = 1;
            }
            if (maxKeyReferenceCount.count < element) {
                maxKeyReferenceCount.count = element;
                maxKeyReferenceCount.value = k;
            }
            return element;
        });
    }

    private void updateValuePopularity(Object value) {
        valueReferenceCount.compute((V) value, (v, element) -> {
            if (element != null) {
                valueReferenceCountSet.remove(element);
                element.count++;
            } else {
                element = new ObservedElement<>(v);
            }
            valueReferenceCountSet.add(element);
            return element;
        });
    }
}
