package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
2 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> popularKey;
    private final Map<V, Integer> popularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularKey = new HashMap<>();
        this.popularValue = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularKey = new HashMap<>();
        this.popularValue = new HashMap<>();
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
        increasePopularityMap((K) key, popularKey);
        return map.containsKey(key);
    }

    private <T> void increasePopularityMap(T key, Map<T, Integer> popularMap) {
        popularMap.put(key, popularMap.getOrDefault(key, 0) + 1);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularityMap((V) value, popularValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        updateAllPopularity((K) key, value);
        return value;
    }

    private <T> void updateAllPopularity(K key, V value) {
        increasePopularityMap(key, popularKey);
        increasePopularityMap(value, popularValue);
    }

    @Override
    public V put(K key, V value) {
        updateAllPopularity(key, value);
        V valueBefore = map.put(key, value);
        if (valueBefore != null) {
            increasePopularityMap(valueBefore, popularValue);
        }
        return valueBefore;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        increasePopularityMap((K) key, popularKey);
        if (value != null) {
            increasePopularityMap(value, popularValue);
        }
        return map.remove(value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        popularKey.clear();
        popularValue.clear();
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
     * 1 балл
     */
    public K getPopularKey() {
        return getPopularity(popularKey);
    }

    private <T> T getPopularity(Map<T, Integer> popularMap) {
        int maxPopularity = 0;
        T value = null;
        for (Map.Entry<T, Integer> entry : popularMap.entrySet()) {
            if (entry.getValue() >= maxPopularity) {
                maxPopularity = entry.getValue();
                value = entry.getKey();
            }
        }
        return value;
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return popularKey.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return getPopularity(popularValue);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return popularValue.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> list = new ArrayList<>();
        for (Entry<V, Integer> entry : popularValue.entrySet()) {
            list.add((V)entry.getKey());
        }
        list.sort(Comparator.comparing(popularValue::get));
        return list.iterator();
    }
}
