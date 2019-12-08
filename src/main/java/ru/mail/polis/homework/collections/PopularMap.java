package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 *  * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 *  * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 *  * containsKey, get, put, remove.
 *  * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
    private Map<Object, Integer> popularKey = new HashMap<>();
    private Map<Object, Integer> popularValue = new HashMap<>();

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

    private void incrementKey(Object key){
        int count = popularKey.getOrDefault(key, 0);
        popularKey.put(key, count + 1);
    }

    private void incrementValue(Object value){
        int count = popularValue.getOrDefault(value, 0);
        popularValue.put(value, count + 1);
    }

    private V checkNullValue(Object key){
        V value = map.get(key);
        if (value != null) {
            incrementValue(value);
        }
        return value;
    }

    @Override
    public boolean containsKey(Object key) {
        incrementKey(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        incrementValue(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        incrementKey(key);
        return checkNullValue(key);
    }

    @Override
    public V put(K key, V value) {
        incrementKey(key);
        int count = popularValue.getOrDefault(value, 0);
        if (value.equals(map.get(key))){
            popularValue.put(value, count + 2);
        }
        else {
            popularValue.put(value, count + 1);
            checkNullValue(key);
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        incrementKey(key);
        checkNullValue(key);
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() { map.clear(); }

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
        Optional<Entry<Object, Integer>> maxEntry = popularKey.entrySet()
                .stream()
                .max(Entry.comparingByValue());
        return (K) maxEntry.get().getKey();
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!popularKey.containsKey(key)){
            return 0;
        }
        return popularKey.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        Optional<Entry<Object, Integer>> maxEntry = popularValue.entrySet()
                .stream()
                .max(Entry.comparingByValue());
        return (V) maxEntry.get().getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!popularValue.containsKey(value)){
            return 0;
        }
        return popularValue.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        Iterator<V> valuesIterator = (Iterator<V>) popularValue.keySet().iterator();
        return valuesIterator;
    }
}
