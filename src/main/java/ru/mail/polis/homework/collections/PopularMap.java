package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
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
    private final Map<K, Integer> keyMap;
    private final Map<V, Integer> valueMap;
    
    public PopularMap() {
        this(new HashMap<>());
    }
    
    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyMap = new HashMap<>();
        this.valueMap = new HashMap<>();
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
        updateMapPopularity((K) key, keyMap);
        return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        updateMapPopularity((V) value, valueMap);
        return map.containsValue(value);
    }
    
    @Override
    public V get(Object key) {
        updateMapPopularity((K) key, keyMap);
        V value = map.get(key);
        updateMapPopularity(value, valueMap);
        return value;
    }
    
    @Override
    public V put(K key, V value) {
        updateMapPopularity(value, valueMap);
        updateMapPopularity(key, keyMap);
        V oldValue = map.put(key, value);
        updateMapPopularity(oldValue, valueMap);
        return oldValue;
    }
    
    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        updateMapPopularity((K) key, keyMap);
        updateMapPopularity(value, valueMap);
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
        return Collections.max(keyMap.entrySet(), Comparator.comparingInt(Entry::getValue)).getKey();
    }
    
    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyMap.getOrDefault(key, 0);
    }
    
    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return Collections.max(valueMap.entrySet(), Comparator.comparingInt(Entry::getValue)).getKey();
    }
    
    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valueMap.getOrDefault(value, 0);
    }
    
    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> list = new ArrayList<>(valueMap.keySet());
        list.sort(Comparator.comparingInt(valueMap::get));
        return list.iterator();
    }
    
    private <T> void updateMapPopularity(T key, Map<T, Integer> map) {
        if (key != null) {
            map.merge(key, 1, Integer::sum);
        }
    }
}
