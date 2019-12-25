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
    private final Map<K, Integer> popularityKey;
    private final Map<V, Integer> popularityValue;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularityKey = new HashMap<>();
        this.popularityValue = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularityKey = new HashMap<>();
        this.popularityValue = new HashMap<>();
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
        popularityKey.put((K) key, popularityKey.getOrDefault(key, 0) + 1);

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        popularityValue.put((V) value, popularityValue.getOrDefault(value, 0) + 1);

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        final V tmp = map.get(key);

        popularityKey.put((K) key, popularityKey.getOrDefault(key, 0) + 1);
        if (tmp != null) {
            popularityValue.put(tmp, popularityValue.getOrDefault(tmp, 0) + 1);
        }

        return tmp;
    }

    @Override
    public V put(K key, V value) {
        final V oldValue = map.get(key);

        if (oldValue != null) {
            popularityValue.put((V) oldValue, popularityValue.getOrDefault(oldValue, 0) + 1);
        }

        popularityKey.put((K) key, popularityKey.getOrDefault(key, 0) + 1);
        popularityValue.put((V) value, popularityValue.getOrDefault(value, 0) + 1);

        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        final V tmp = map.remove(key);

        popularityKey.put((K) key, popularityKey.getOrDefault(key, 0) + 1);
        if (tmp != null) {
            popularityValue.put(tmp, popularityValue.getOrDefault(tmp, 0) + 1);
        }

        return tmp;
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
        return popularityKey
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
        return popularityKey.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularityValue
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
        return popularityValue.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return popularityValue
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .iterator();
    }
}
