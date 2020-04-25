package ru.mail.polis.homework.collections;


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

    private final Map<K, Integer> keyPopular;
    private final Map<V, Integer> valuePopular;

    public PopularMap() {

        this.map = new HashMap<>();
        this.keyPopular = new HashMap();
        this.valuePopular = new HashMap();
    }

    public PopularMap(Map<K, V> map) {

        this.map = map;
        this.keyPopular = new HashMap();
        this.valuePopular = new HashMap();
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
        calculatePopular((K) key, keyPopular);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        calculatePopular((V) value, valuePopular);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        calculatePopular((K) key, keyPopular);
        V value = map.get(key);
        calculatePopular(value, valuePopular);

        return value;
    }

    @Override
    public V put(K key, V value) {
        calculatePopular(key, keyPopular);
        calculatePopular(value, valuePopular);
        V valueBefore = map.put(key, value);
        calculatePopular(valueBefore, valuePopular);
        return valueBefore;
    }


    @Override
    public V remove(Object key) {

        V value = map.remove(key);
        calculatePopular((K) key, keyPopular);
        calculatePopular(value, valuePopular);


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

    public <T> void calculatePopular(T parameterPopular, Map<T, Integer> mapPopular) {
        if (parameterPopular != null) {
            int countKey = mapPopular.getOrDefault(parameterPopular, 0);
            mapPopular.put(parameterPopular, countKey + 1);
        }
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return getPopular(keyPopular);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {

        return keyPopular.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getPopular(valuePopular);
    }


    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopular.getOrDefault(value, 0);
    }


    public <T> T getPopular(Map<T, Integer> mapPopular) {
        int max = 0;
        T parameterPopular = null;
        for (Entry<T, Integer> entry : mapPopular.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                parameterPopular = entry.getKey();
            }
        }
        return parameterPopular;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> valueList = new ArrayList(this.valuePopular.keySet());
        valueList.sort(Comparator.comparing(valuePopular::get));
        return valueList.iterator();
    }
}


