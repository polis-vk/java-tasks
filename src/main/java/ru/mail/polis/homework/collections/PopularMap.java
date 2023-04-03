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
    private final Map<K, Integer> countKeyPopularity = new HashMap<>();
    private final Map<V, Integer> countValuePopularity = new HashMap<>();
    private K popularKey;
    private V popularValue;

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
        putPopularity((K) key, countKeyPopularity);
        getMostPopularKey((K) key);

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        putPopularity((V) value, countValuePopularity);
        getMostPopularValue((V) value);

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        putPopularity((K) key, countKeyPopularity);
        getMostPopularKey((K) key);

        V value = map.get(key);
        putPopularity(value, countValuePopularity);
        getMostPopularValue(value);

        return value;
    }

    @Override
    public V put(K key, V value) {
        putPopularity(key, countKeyPopularity);
        getMostPopularKey(key);

        putPopularity(value, countValuePopularity);
        getMostPopularValue(value);

        V newValue = map.put(key, value);
        putPopularity(newValue, countValuePopularity);
        getMostPopularValue(newValue);

        return newValue;
    }

    @Override
    public V remove(Object key) {
        putPopularity((K) key, countKeyPopularity);
        getMostPopularKey((K) key);

        V value = map.remove(key);
        putPopularity(value, countValuePopularity);
        getMostPopularValue(value);

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
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return countKeyPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return countValuePopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return countValuePopularity.entrySet().stream()
                .sorted(Comparator.comparingInt(Entry::getValue))
                .map(Map.Entry::getKey)
                .iterator();
    }

    private <T> void putPopularity(T key, Map<T, Integer> countPopularity) {
        if (key == null) {
            return;
        }
        if (countPopularity.containsKey(key)) {
            countPopularity.put(key, countPopularity.get(key) + 1);
        } else {
            countPopularity.put(key, 1);
        }
    }

    private void getMostPopularKey(K key) {
        if (getKeyPopularity(key) > getKeyPopularity(popularKey)) {
            popularKey = key;
        }
    }

    private void getMostPopularValue(V value) {
        if (getValuePopularity(value) > getValuePopularity(popularValue)) {
            popularValue = value;
        }
    }

}
