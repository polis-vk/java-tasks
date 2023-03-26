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

    private Map<Object, Integer> popularKey = new HashMap<Object, Integer>();
    private Map<Object, Integer> popularValue = new HashMap<Object, Integer>();
    private Map<Object, V> oldMap = new HashMap<>();

    private void refreshPopularKey(Object key) {

        if (popularKey.isEmpty()) {
            popularKey.put(key, 1);
            return;
        }
        if (!popularKey.containsKey(key)) {
            popularKey.put(key, 1);
        } else {
            popularKey.put(key, popularKey.get(key) + 1);
        }
    }

    private void refreshPopularValue(Object value) {
        if (popularValue.isEmpty()) {
            popularValue.put(value, 1);
            return;
        }
        if (!popularValue.containsKey(value)) {
            popularValue.put(value, 1);
        } else {
            popularValue.put(value, popularValue.get(value) + 1);
        }

    }

    private final Map<K, V> map;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
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
        refreshPopularKey(key);
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        refreshPopularValue(value);
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        refreshPopularKey(key);
        if (this.map.containsKey(key)) {
            refreshPopularValue(this.map.get(key));
            return this.map.get(key);
        } else {
            if (oldMap.containsKey(key)) {
                refreshPopularValue(oldMap.get(key));
            }
        }
        return this.map.get(key);
    }

    @Override
    public V put(K key, V value) {
        refreshPopularKey(key);
        refreshPopularValue(value);
        return this.map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        refreshPopularKey(key);
        if (this.map.containsKey(key)) {
            refreshPopularValue(this.map.get(key));
            oldMap.put(key, this.map.get(key));
            return this.map.remove(key);
        } else {
            refreshPopularValue(oldMap.get(key));
        }
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
//        объединение словарей
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
        int maxValue = -1;
        Object maxKey = null;
        int value = 0;
        Object key;
        for (Map.Entry<Object, Integer> item : popularKey.entrySet()) {
            key = item.getKey();
            value = popularKey.get(key);
            if (value > maxValue) {
                maxValue = value;
                maxKey = key;
            }
        }
        return (K) maxKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!popularKey.containsKey(key)) {
            return 0;
        }
        return popularKey.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int maxValue = -1;
        Object maxKey = null;
        int value = 0;
        Object key;
        for (Map.Entry<Object, Integer> item : popularValue.entrySet()) {
            key = item.getKey();
            value = popularValue.get(key);
            if (value > maxValue) {
                maxValue = value;
                maxKey = key;
            }
        }
        return (V) maxKey;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!popularValue.containsKey(value)) {
            return 0;
        }
        return popularValue.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return (Iterator<V>) popularValue.keySet().iterator();
    }
}
