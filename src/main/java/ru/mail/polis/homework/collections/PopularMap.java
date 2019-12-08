package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.function.Function;

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
    private final Map<V, Integer> popularValMap = new HashMap<>();
    private final Map<K, Integer> popularKeyMap = new HashMap<>();

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
        updateKeyCounter((K)key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValCounter((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updateKeyCounter((K) key);
        V curVal = map.get(key);
        if (curVal != null){
            popularValMap.put(curVal, popularValMap.get(curVal) + 1);
        }
        return curVal;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)){
            updateValCounter(map.get(key));
        }
        updateKeyCounter(key);
        updateValCounter(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        updateKeyCounter((K) key);
        V curVal = map.remove(key);
        if (curVal != null){
            updateValCounter(curVal);
        }
        return curVal;
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
        if (popularKeyMap.isEmpty()){
            return null;
        }
        return popularKeyMap
                .entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKeyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        if (popularValMap.isEmpty()) {
            return null;
        }
        return popularValMap
                .entrySet()
                .stream()
                .sorted(Comparator.comparing((Function<Entry<V, Integer>, Integer>) Entry::getValue).reversed())
                .findFirst()
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return popularValMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(Entry::getValue))
                .map(Entry::getKey)
                .iterator();
    }
    private void updateValCounter(V value){
        popularValMap.put(value, popularValMap.getOrDefault(value, 0) + 1);
    }
    private void updateKeyCounter(K key){
        popularKeyMap.put(key, popularKeyMap.getOrDefault(key, 0) + 1);
    }
}
