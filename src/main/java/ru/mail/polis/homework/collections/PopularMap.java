package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


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
    private final Map<K, Integer> keysPopularityMap;
    private final Map<V, Integer> valuesPopularityMap;
    private K mostPopularKey;
    private V mostPopularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keysPopularityMap = new HashMap<>();
        this.valuesPopularityMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keysPopularityMap = new HashMap<>();
        this.valuesPopularityMap = new HashMap<>();
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
        incrementKeyPopularity((K) key);
        return map.containsKey((K) key);
    }

    @Override
    public boolean containsValue(Object value) {
        incrementValuePopularity((V) value);
        return map.containsValue((V) value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        incrementKeyPopularity((K) key);
        incrementValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        incrementKeyPopularity(key);
        incrementValuePopularity(value);
        V newValue = map.put(key, value);
        incrementValuePopularity(newValue);
        return newValue;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        incrementKeyPopularity((K) key);
        incrementValuePopularity(value);
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
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keysPopularityMap.getOrDefault(key, 0);
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
        return valuesPopularityMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuesPopularityMap.keySet()
                .stream()
                .sorted(Comparator.comparingInt(this::getValuePopularity))
                .iterator();
    }

    private void incrementKeyPopularity(K key) {
        int popularity = keysPopularityMap.merge(key, 1, Integer::sum);
        if (mostPopularKey == null || popularity > keysPopularityMap.get(mostPopularKey)) {
            mostPopularKey = key;
        }
    }

    private void incrementValuePopularity(V value) {
        if (value == null) {
            return;
        }
        int popularity = valuesPopularityMap.merge(value, 1, Integer::sum);
        if (mostPopularValue == null || popularity > valuesPopularityMap.get(mostPopularValue)) {
            mostPopularValue = value;
        }
    }

}
