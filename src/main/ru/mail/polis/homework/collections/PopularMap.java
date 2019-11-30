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

    private Map<K, V> map;
    private Map<K, Integer> popularKeyMap = new HashMap<K, Integer>();
    private Map<V, Integer> popularValueMap = new HashMap<V, Integer>();

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

       addPopularityKey((K)key);
        return map.containsKey((K)key);
    }

    @Override
    public boolean containsValue(Object value){
        addPopularityValue((V)value);
        return  map.containsValue((V)value);
    }

    @Override
    public V get(Object key) {
        V value = map.get((K)key);
        addPopularityKey((K)key);
        if (value != null){
            addPopularityValue(value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
       addPopularity(key, value);

       V oldValue = map.get(key);
       if (oldValue != null) {
           addPopularityValue(oldValue);
       }
        map.put(key, value);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove((K)key);
        addPopularityKey((K)key);
        if (value != null){
            addPopularityValue(value);
        }
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
        if (popularKeyMap.isEmpty()){
            return null;
        }
        return popularKeyMap.entrySet()
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
        if (popularValueMap.isEmpty()){
            return null;
        }
        return popularValueMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(
                                (Function<Entry<V, Integer>, Integer>) Entry::getValue).reversed()
                )
                .findFirst()
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValueMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return popularValueMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(
                        Entry::getValue)
                )
                .map(Entry::getKey)
                .iterator();
    }

    private void addPopularityKey(K key){
        popularKeyMap.put(key, popularKeyMap.getOrDefault(key, 0)+1);
    }

    private void addPopularityValue(V value){
        popularValueMap.put(value, popularValueMap.getOrDefault(value, 0)+1);
    }

    private void addPopularity(K key, V value){
        popularKeyMap.put(key, popularKeyMap.getOrDefault(key, 0)+1);
        popularValueMap.put(value, popularValueMap.getOrDefault(value, 0)+1);
    }

    @Override
    public String toString() {
        return "PopularMap{\n" +
                "map=" + map.toString() +
                ",\n popularKeyMap=" + popularKeyMap.toString() +
                ",\n popularValueMap=" + popularValueMap.toString() +
                "\n}";
    }
}
