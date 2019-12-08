package ru.mail.polis.homework.collections;


import java.util.*;
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
    private final TreeMap<K,Integer> popularKeyMap=new TreeMap<>();
    private final TreeMap<V,Integer> popularValueMap=new TreeMap<>();

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        for (K k:map.keySet()) {
            popularKeyMap.put(k,1);
        }
        for(V v:map.values())
        {
            popularValueMap.put(v,1);
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
        if(popularKeyMap.containsKey(key))
            popularKeyMap.put((K) key,popularKeyMap.get(key)+1);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if(popularValueMap.containsKey(value))
            popularValueMap.put((V) value,popularValueMap.get(value)+1);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if(popularKeyMap.containsKey(key))
            popularKeyMap.put((K) key,popularKeyMap.get(key)+1);
        if(popularValueMap.containsKey(map.get(key)))
            popularValueMap.put(map.get(key),popularValueMap.get(map.get(key))+1);
        return map.getOrDefault(key, null);
    }

    @Override
    public V put(K key, V value) {
        if(popularKeyMap.containsKey(key))
            popularKeyMap.put( key,popularKeyMap.get(key)+1);
        else popularKeyMap.put(key,1);
        if(popularValueMap.containsKey(value))
            popularValueMap.put(value,popularValueMap.get(value)+1);
        else popularValueMap.put(value,1);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if(popularKeyMap.containsKey(key))
            popularKeyMap.put((K) key,popularKeyMap.get(key)+1);
        if(popularValueMap.containsKey(map.get(key)))
            popularValueMap.put(map.get(key),popularValueMap.get(map.get(key))+1);
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        //throw new UnsupportedOperationException("putAll");
        for (K k:m.keySet()) {
            if(popularKeyMap.containsKey(k))
                popularKeyMap.put(k,popularKeyMap.get(k)+1);
            else popularKeyMap.put(k,1);
        }
        for(V v:m.values())
            if(popularValueMap.containsKey(v))
                popularValueMap.put(v,popularValueMap.get(v)+1);
            else popularValueMap.put(v,1);
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
        return popularKeyMap.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValueMap.entrySet()
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
        return popularValueMap.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return popularValueMap.entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }
}
