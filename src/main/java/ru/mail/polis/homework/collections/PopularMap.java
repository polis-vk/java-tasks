package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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

    private final Map<K, V> map;
    private final Map<K, Integer> keysWithPopularity = new HashMap<>();
    private final Map<V, Integer> valuesWithPopularity = new HashMap<>();

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    private static <T> void increasePopularity(T key, Map<T, Integer> mapPopularity) {
        if (key != null) {
            mapPopularity.merge(key, 1, Integer::sum);
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
        increasePopularity((K) key, keysWithPopularity);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity((V) value, valuesWithPopularity);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        increasePopularity((K) key, keysWithPopularity);
        increasePopularity(value, valuesWithPopularity);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V mapValue = map.get(key);
        if (mapValue != null) {
            increasePopularity(mapValue, valuesWithPopularity);
        }
        increasePopularity(value, valuesWithPopularity);
        increasePopularity(key, keysWithPopularity);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        increasePopularity((K) key, keysWithPopularity);
        V oldValue = map.remove(key);
        increasePopularity(oldValue, valuesWithPopularity);
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
        return getTheMostPopular(keysWithPopularity);
    }

    private static <T> T getTheMostPopular(Map<T, Integer> map) {
        return map.entrySet()
                .stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        keysWithPopularity.putIfAbsent(key, 0);
        return keysWithPopularity.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getTheMostPopular(valuesWithPopularity);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        valuesWithPopularity.putIfAbsent(value, 0);
        return valuesWithPopularity.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        Set<V> valuesSet = this.valuesWithPopularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toSet());
        return valuesSet.iterator();
    }
}
