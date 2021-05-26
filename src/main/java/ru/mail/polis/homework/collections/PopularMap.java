package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;


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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {
    private final Map<K, Integer> keysPopularityMap = new HashMap<>();
    private final Map<V, Integer> valuesPopularityMap = new HashMap<>();
    private K popularKey;
    private V popularValue;
    boolean popularKeyChanged;
    boolean popularValueChanged;

    private final Map<K, V> map;

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
        updateKeysMap((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValuesMap((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        updateKeysMap((K) key);
        updateValuesMap(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = map.put(key, value);
        if (oldValue != null) {
            updateValuesMap(oldValue);
        }
        updateKeysMap(key);
        updateValuesMap(value);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V oldValue = map.remove(key);
        updateKeysMap((K) key);
        if (oldValue != null) {
            updateValuesMap(oldValue);
        }
        return oldValue;
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
        if (popularKeyChanged) {
            popularKey = keysPopularityMap.entrySet().stream().max(Entry.comparingByValue()).get().getKey();
            popularKeyChanged = false;
        }
        return popularKey;
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
        if (popularValueChanged) {
            popularValue = valuesPopularityMap.entrySet().stream().max(Entry.comparingByValue()).get().getKey();
            popularValueChanged = false;
        }
        return popularValue;
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
     */
    public Iterator<V> popularIterator() {
        return valuesPopularityMap.keySet().stream()
                .sorted(Comparator.comparingInt(valuesPopularityMap::get))
                .collect(Collectors.toList()).iterator();
    }

    private void updateKeysMap(K k) {
        keysPopularityMap.compute(k, (key, value) -> value == null ? 1 : value + 1);
        popularKeyChanged = true;
    }

    private void updateValuesMap(V v) {
        valuesPopularityMap.compute(v, (key, value) -> value == null ? 1 : value + 1);
        popularValueChanged = true;
    }


}