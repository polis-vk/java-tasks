package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 *
 * Дополнительное задание описано будет ниже
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularity;
    private final Map<V, Integer> valuePopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularity = new HashMap<>();
        this.valuePopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularity = new HashMap<>();
        this.valuePopularity = new HashMap<>();
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
        plusPopularityKey((K)key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        plusPopularityValue((V)value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        plusPopularityKey((K)key);
        plusPopularityValue((V)value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V retValue = map.put(key, value);
        if (retValue != null) {plusPopularityValue(value);}
        plusPopularityKey(key);
        plusPopularityValue(value);
        return retValue;
    }

    @Override
    public V remove(Object key) {
        V delValue = map.remove(key);
        if (delValue != null) {plusPopularityValue(delValue);}
        plusPopularityKey((K)key);
        return delValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) { map.putAll(m); }

    @Override
    public void clear() { map.clear();}

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
        return keyPopularity
                .entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) { return keyPopularity.get(key); }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return valuePopularity
                .entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularity.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valuePopularity
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }

    private void plusPopularityKey(K key){
        if (keyPopularity.containsKey(key)) {
            keyPopularity.put(key,keyPopularity.get(key) + 1);
        } else {
            keyPopularity.put(key,1);
        }
    }

    private void plusPopularityValue(V value){
        if (valuePopularity.containsKey(value)) {
            valuePopularity.put(value,valuePopularity.get(value) + 1);
        } else {
            valuePopularity.put(value,1);
        }
    }
}
