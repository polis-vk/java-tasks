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
 * Всего 9 баллов (2 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private Map<K, Integer> keysPopularity;
    private Map<V, Integer> valuesPopularity;
    private K mostPopularKey;
    private V mostPopularValue;


    public PopularMap() {
        this.map = new HashMap<>();
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();
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
        RefreshPopularity(keysPopularity, key, true);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        RefreshPopularity(valuesPopularity, value, false);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        RefreshPopularity(keysPopularity, key, true);
        V previousValue = map.get(key);
        if (previousValue != null) {
            RefreshPopularity(valuesPopularity, previousValue, false);
        }
        return previousValue;
    }

    @Override
    public V put(K key, V value) {
        RefreshPopularity(keysPopularity, key, true);
        RefreshPopularity(valuesPopularity, value, false);
        V previousValue = map.put(key, value);
        if (previousValue != null) {
            RefreshPopularity(valuesPopularity, previousValue, false);
        }
        return previousValue;
    }

    @Override
    public V remove(Object key) {
        RefreshPopularity(keysPopularity, key, true);
        V deletedValue = map.remove(key);
        if (deletedValue != null) {
            RefreshPopularity(valuesPopularity, deletedValue, false);
        }
        return deletedValue;
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
     * 1 балл
     */
    public K getPopularKey() {
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return keysPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> sortedValuesPopularity = new ArrayList(valuesPopularity.keySet());
        sortedValuesPopularity.sort(Comparator.comparing(valuesPopularity::get));
        return sortedValuesPopularity.iterator();
    }

    public <T> void RefreshPopularity(Map<T, Integer> popularityMap, Object key, boolean flag) {
        Integer newPopularity = popularityMap.compute((T) key, (k, v) -> (v == null) ? 1 : v + 1);
        if (flag) {
            if (mostPopularKey == null || newPopularity > popularityMap.get(mostPopularKey)) {
                mostPopularKey = (K) key;
            }
        } else {
            if (mostPopularValue == null || newPopularity > popularityMap.get(mostPopularValue)) {
                mostPopularValue = (V) key;
            }
        }
    }
}
