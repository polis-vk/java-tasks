package ru.mail.polis.homework.collections;


import java.util.*;


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
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private final Map<K, Integer> popularKeys = new HashMap<>();
    private final Map<V, Integer> popularValues = new HashMap<>();

    private K mostPopularKey;
    private V mostPopularValue;
    private Set<Entry<K, V>> entries;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.entries = map.entrySet();
        for (Entry<K, V> entry: entries) {
            this.popularKeys.put(entry.getKey(), 0);
            this.popularValues.put(entry.getValue(), 0);
        }
    }

    private void gainKeyPopularity(K key) {
        if (this.popularKeys.containsKey(key)) {
            int popularity = this.popularKeys.get(key) + 1;
            this.popularKeys.put(key, popularity);
        } else {
            this.popularKeys.put(key, 1);
        }

        if (mostPopularKey != null) {
            if (popularKeys.get(key) > popularKeys.get(mostPopularKey)) {
                mostPopularKey = key;
            }
        } else {
            mostPopularKey = key;
        }
    }

    private void gainValuePopularity(V value) {
        if (this.popularValues.containsKey(value)) {
            int popularity = this.popularValues.get(value) + 1;
            this.popularValues.put(value, popularity);
        } else {
            this.popularValues.put(value, 1);
        }

        if (mostPopularValue != null) {
            if (popularValues.get(value) > popularValues.get(mostPopularValue)) {
                mostPopularValue = value;
            }
        } else {
            mostPopularValue = value;
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
        gainKeyPopularity((K) key);
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        gainValuePopularity((V) value);
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        gainKeyPopularity((K) key);
        V currVal = this.map.get(key);
        gainValuePopularity(currVal);
        return currVal;
    }

    @Override
    public V put(K key, V value) {
        gainKeyPopularity(key);
        gainValuePopularity(value);
        V prevVal = this.map.put(key, value);
        if (prevVal != null) {
            gainValuePopularity(prevVal);
        }
        return prevVal;
    }

    @Override
    public V remove(Object key) {
        gainKeyPopularity((K) key);
        V prevVal = this.map.remove(key);
        if (prevVal != null) {
            gainValuePopularity(prevVal);
        }
        return prevVal;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
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
        return this.entries;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return this.mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return this.popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return this.mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return this.popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        V[] keysArray = (V[]) popularValues.keySet().toArray();
        Arrays.sort(keysArray, Comparator.comparing(popularValues::get));

        return Arrays.stream(keysArray).iterator();
    }
}
