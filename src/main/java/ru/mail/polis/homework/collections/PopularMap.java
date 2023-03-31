package ru.mail.polis.homework.collections;


import java.util.Collection;
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
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */

public class PopularMap<K, V> implements Map<K, V> {
    public static void main(String[] args) {
        PopularMap<String, String> popularMap1 = new PopularMap<>();
        String key = "key";
        String value = "value";
        popularMap1.get(key);
        popularMap1.remove(key);
        popularMap1.put(key, value);
        popularMap1.put(key, value);
        popularMap1.get(key);
        popularMap1.remove(key);
        popularMap1.remove(key);
        popularMap1.put(key, value);
        popularMap1.remove(key);
    }
    private final Map<K, V> map;
    private final Map<K, Integer> popularKeys = new HashMap<>();
    private final Map<V, Integer> popularValues = new HashMap<>();
    private <T> void addPopularMap(Map<T, Integer> popularMap, Object key){
        if(popularMap.containsKey(key)){
            popularMap.put((T) key, popularMap.get(key) + 1);
        } else {
            popularMap.put((T) key, 1);
        }
    }

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        addPopularMap(popularKeys, key);
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularMap(popularValues, value);
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addPopularMap(popularKeys, key);
        return this.map.get(key);
    }

    @Override
    public V put(K key, V value) {
        addPopularMap(popularKeys, key);
        addPopularMap(popularValues, value);
        return this.map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        addPopularMap(popularKeys, key);
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("putAll");
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        K popularKey = null;
        int maxAppearances = 0;
        for(K key : popularKeys.keySet()){
            if(popularKeys.get(key) > maxAppearances){
                maxAppearances = popularKeys.get(key);
                popularKey = key;
            }
        }
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKeys.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return null;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValues.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return null;
    }
}
