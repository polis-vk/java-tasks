package ru.mail.polis.homework.collections;


import java.util.*;


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
    private final Map<K, Integer> keyPopularity = new HashMap<>();
    private final Map<V, Integer> valuePopularity = new HashMap<>();

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
        increasePopularity((K) key, keyPopularity);

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity((V) value, valuePopularity);

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);

        increasePopularity((K) key, keyPopularity);
        increasePopularity(value, valuePopularity);

        return value;
    }

    @Override
    public V put(K key, V value) {
        increasePopularity(key, keyPopularity);
        increasePopularity(value, valuePopularity);

        V oldValue = map.put(key, value);
        increasePopularity(oldValue, valuePopularity);

        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);

        increasePopularity((K) key, keyPopularity);
        increasePopularity(value, valuePopularity);

        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        keyPopularity.clear();
        valuePopularity.clear();
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
        return getMostPopularityEntry(keyPopularity);
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getMostPopularityEntry(valuePopularity);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valuePopularity.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getValue))
                .map(Entry::getKey)
                .iterator();
    }

    private <T> void increasePopularity(T key, Map<T, Integer> map) {
        if (key == null) return;
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    private <T> T getMostPopularityEntry(Map<T, Integer> map) {
        return map.entrySet().stream()
                .max(Comparator.comparing(Entry<T, Integer>::getValue))
                .get()
                .getKey();
    }
}
