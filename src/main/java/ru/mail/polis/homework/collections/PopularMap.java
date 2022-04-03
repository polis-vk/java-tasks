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
 * <p>
 * Так же надо сделать итератор (подробности ниже).
 * <p>
 * Важный момент, вам не надо реализовывать мапу, вы должны использовать композицию.
 * Вы можете использовать любые коллекции, которые есть в java.
 * <p>
 * Помните, что по мапе тоже можно итерироваться
 * <p>
 * for (Map.Entry<K, V> entry : map.entrySet()) {
 * entry.getKey();
 * entry.getValue();
 * }
 * <p>
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularity = new HashMap<>();
    private final Map<V, Integer> valuePopularity = new HashMap<>();
    private K maxKey;
    private V maxValue;


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
        addPopularity((K) key, keyPopularity);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularity((V) value, valuePopularity);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addPopularity((K) key, keyPopularity);
        V value = map.get(key);
        addPopularity(value, valuePopularity);
        return value;
    }

    @Override
    public V put(K key, V value) {
        addPopularity(key, keyPopularity);
        addPopularity(value, valuePopularity);
        addPopularity(map.get(key), valuePopularity);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        addPopularity((K) key, keyPopularity);
        addPopularity(map.get(key), valuePopularity);
        return map.remove(key);
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
        return maxKey;
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
        return maxValue;
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
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuePopularity.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()).iterator();
    }

    private <T> void addPopularity(T value, Map<T, Integer> popularity) {
        if (value != null) {
            popularity.compute(value, (k, v) -> (v == null) ? 1 : v + 1);
        }
        if (!popularity.isEmpty()) {
            if (popularity.equals(keyPopularity)) {
                maxKey = keyPopularity.entrySet().stream()
                        .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                        .limit(1)
                        .reduce((b, a) -> a).get().getKey();

            } else {
                maxValue = valuePopularity.entrySet().stream()
                        .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                        .limit(1)
                        .reduce((b, a) -> a).get().getKey();
            }
        }
    }
}
