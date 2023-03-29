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
    private final Map<K, Integer> popularKeys = new HashMap<>();
    private final Map<V, Integer> popularValues = new HashMap<>();
    private V mostPopularValue;
    private K mostPopularKey;

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
        keysPopularityIncrease((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        valuesPopularityIncrease((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        keysPopularityIncrease((K) key);
        valuesPopularityIncrease(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        keysPopularityIncrease((K) key);
        valuesPopularityIncrease((V) value);
        valuesPopularityIncrease(map.get(key));
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        keysPopularityIncrease((K) key);
        valuesPopularityIncrease(map.get(key));
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
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<Entry<V, Integer>> popularity = new ArrayList<>(popularValues.entrySet());
        popularity.sort(Comparator.comparingInt(Entry::getValue));
        List<V> valuesSortedByPopularity = new ArrayList<>();
        for (Entry<V, Integer> value : popularity) {
            valuesSortedByPopularity.add(value.getKey());
        }
        return valuesSortedByPopularity.iterator();
    }

    private void keysPopularityIncrease(K key) {
        if (popularKeys.merge(key, 1, Integer::sum) > popularKeys.getOrDefault(mostPopularKey, 0)) {
            mostPopularKey = key;
        }
    }

    private void valuesPopularityIncrease(V value) {
        if (value == null) {
            return;
        }
        if (popularValues.merge(value, 1, Integer::sum) > popularValues.getOrDefault(mostPopularValue, 0)) {
            mostPopularValue = value;
        }
    }
}
