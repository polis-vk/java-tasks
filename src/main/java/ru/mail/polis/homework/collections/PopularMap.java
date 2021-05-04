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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private final Map<K, Integer> keysPopularity;
    private final Map<V, Integer> valuesPopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
    }

    /**
     * Не учитываем null значения
     */

    private void addKeyPopularity(K key) {
        if (key != null) {
            keysPopularity.put(key, keysPopularity.getOrDefault(key, 0) + 1);
        }
    }

    private void addValuePopularity(V value) {
        if (value != null) {
            valuesPopularity.put(value, valuesPopularity.getOrDefault(value, 0) + 1);
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
        addKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        addKeyPopularity((K) key);
        addValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        addKeyPopularity(key);
        addValuePopularity(value);
        V oldValue = map.put(key, value);
        addValuePopularity(oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        addKeyPopularity((K) key);
        V valueRemoved = map.remove(key);
        addValuePopularity(valueRemoved);
        return valueRemoved;
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
        Integer maxPopularity = 0;
        K maxPopularKey = null;
        for (Entry<K, Integer> entry : keysPopularity.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                maxPopularKey = entry.getKey();
                maxPopularity = entry.getValue();
            }
        }
        return maxPopularKey;
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
        Integer maxPopularity = 0;
        V maxPopularValue = null;
        for (Entry<V, Integer> entry : valuesPopularity.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                maxPopularValue = entry.getKey();
                maxPopularity = entry.getValue();
            }
        }
        return maxPopularValue;
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
        List<Entry<V, Integer>> entries = new ArrayList<>(valuesPopularity.entrySet());
        entries.sort(Entry.comparingByValue());
        List<V> result = new ArrayList<>(entries.size());
        for (Entry<V, Integer> entry : entries) {
            result.add(entry.getKey());
        }
        return result.iterator();
    }
}
