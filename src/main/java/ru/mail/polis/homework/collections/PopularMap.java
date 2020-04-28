package ru.mail.polis.homework.collections;


import ru.mail.polis.homework.collections.mail.MailService;

import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 * <p>
 * Дополнительное задание описано будет ниже
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularity = new HashMap<>();
    private final Map<V, Integer> valuePopularity = new HashMap<>();

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    private <T> T getMostPopular(Map<T, Integer> map) {
        int maxValue = Integer.MIN_VALUE;
        T result = null;

        for (T t : map.keySet()) {
            Integer tmp = map.get(t);
            if(tmp > maxValue) {
                maxValue = tmp;
                result = t;
            }
        }
        return result;
    }

    private <T> void increasePopularity(T param, Map<T, Integer> map) {
        if (param != null) {
            map.compute(param, (k, v) -> v == null ? 1 : v + 1);
        }
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
        increasePopularity((K) key, keyPopularity);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity((V) value, valuePopularity);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V mapValue = map.get(key);

        increasePopularity((K) key, keyPopularity);
        increasePopularity((V) mapValue, valuePopularity);
        return mapValue;
    }

    @Override
    public V put(K key, V value) {
        increasePopularity(key, keyPopularity);
        increasePopularity(value, valuePopularity);

        increasePopularity(map.get(key), valuePopularity);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        increasePopularity((K) key, keyPopularity);

        V removedValue = map.remove(key);
        increasePopularity((V) removedValue, valuePopularity);
        return removedValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        keyPopularity.clear();
        valuePopularity.clear();
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
        return getMostPopular(keyPopularity);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getMostPopular(valuePopularity);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<V>(valuePopularity.keySet());
        values.sort(Comparator.comparing(valuePopularity::get));
        return values.iterator();
    }
}
