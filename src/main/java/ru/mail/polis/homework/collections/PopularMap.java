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

    private static class PopularityAndValue<T> {
        private T value;
        private int popularity;
    }

    private final Map<K, V> map;
    private final Map<K, Integer> popularKeyMap;
    private final Map<V, Integer> popularValueMap;
    private final PopularityAndValue<K> keyAndMaxPopularity;
    private final PopularityAndValue<V> valueAndMaxPopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularKeyMap = new HashMap<>();
        this.popularValueMap = new HashMap<>();
        this.keyAndMaxPopularity = new PopularityAndValue<>();
        this.valueAndMaxPopularity = new PopularityAndValue<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularKeyMap = new HashMap<>();
        this.popularValueMap = new HashMap<>();
        this.keyAndMaxPopularity = new PopularityAndValue<>();
        this.valueAndMaxPopularity = new PopularityAndValue<>();
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
        addPopularity(popularKeyMap, key, keyAndMaxPopularity);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularity(popularValueMap, value, valueAndMaxPopularity);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addPopularity(popularKeyMap, key, keyAndMaxPopularity);
        V value = map.get(key);
        addPopularity(popularValueMap, value, valueAndMaxPopularity);
        return value;
    }

    @Override
    public V put(K key, V value) {
        addPopularity(popularKeyMap, key, keyAndMaxPopularity);
        addPopularity(popularValueMap, value, valueAndMaxPopularity);
        V oldValue = map.put(key, value);
        addPopularity(popularValueMap, oldValue, valueAndMaxPopularity);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        addPopularity(popularKeyMap, key, keyAndMaxPopularity);
        V oldValue = map.remove(key);
        addPopularity(popularValueMap, oldValue, valueAndMaxPopularity);
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

    private <T> T getPopular(Map<T, Integer> popularMap) {
        if (popularMap.isEmpty()) {
            return null;
        }
        return popularMap
                .entrySet().stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return keyAndMaxPopularity.value;
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
        return valueAndMaxPopularity.value;
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
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return popularValueMap
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }

    @SuppressWarnings("unchecked")
    private <T> void addPopularity(Map<T, Integer> popularMap, Object value, PopularityAndValue<T> valueAndPopularity) {
        if (value == null) {
            return;
        }
        popularMap.compute((T) value, (k, v) -> popularMap.containsKey(k) ? popularMap.get(k) + 1 : 1);
        int popularityThisKey = popularMap.get(value);
        if (popularityThisKey > valueAndPopularity.popularity) {
            valueAndPopularity.value = (T) value;
            valueAndPopularity.popularity = popularityThisKey;
        }
    }
}
