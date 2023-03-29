package ru.mail.polis.homework.collections;


import java.util.*;


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
    private final Map<K, Integer> popularKey;
    private final Map<V, Integer> popularValue;
    K popularK;
    V popularV;

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
        addPopularityKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularityValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addPopularityKey((K) key);
        V value = map.get(key);
        addPopularityValue(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        addPopularityKey(key);
        addPopularityValue(value);
        V lastValue = map.put(key, value);
        if (lastValue != null) {
            addPopularityValue(lastValue);
        }
        return lastValue;
    }

    @Override
    public V remove(Object key) {
        addPopularityKey((K) key);
        V value = map.remove(key);
        addPopularityValue(value);
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
        return popularK;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKey.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularV;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValue.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<V> list = new ArrayList<>();

        for (Map.Entry<V, Integer> entry : popularValue.entrySet()) {
            list.add(entry.getKey());
        }

        return list.iterator();
    }

    private K addPopularityKey(K key) {
        if (key == null) {
            return popularK;
        }
        popularKey.put(key, popularKey.getOrDefault(key, 0) + 1);
        if (popularK == null) {
            popularK = key;
        }
        if (popularKey.get(popularK) < popularKey.get(key)) {
            popularK = key;
        }
        return popularK;
    }

    private V addPopularityValue(V values) {
        if (values == null) {
            return popularV;
        }
        popularValue.put(values, popularValue.getOrDefault(values, 0) + 1);
        if (popularV == null) {
            popularV = values;
        }
        if (popularValue.get(popularV) < popularValue.get(values)) {
            popularV = values;
        }
        return popularV;
    }
}
