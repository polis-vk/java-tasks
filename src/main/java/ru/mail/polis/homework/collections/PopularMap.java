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

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularityMap = new HashMap<>();
    private final Map<V, Integer> valuePopularityMap = new HashMap<>();
    private K popularKey = null;
    private int maxKeyPopularity = 0;
    private V popularValue = null;
    private int maxValuePopularity = 0;

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
        increasePopularity((K) key, keyPopularityMap, true);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity((V) value, valuePopularityMap, false);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increasePopularity((K) key, keyPopularityMap, true);
        V value = map.get(key);
        increasePopularity(value, valuePopularityMap, false);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increasePopularity(key, keyPopularityMap, true);
        increasePopularity(value, valuePopularityMap, false);
        V oldValue = map.put(key, value);
        increasePopularity(oldValue, valuePopularityMap, false);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        increasePopularity((K) key, keyPopularityMap, true);
        V oldValue = map.remove(key);
        increasePopularity(oldValue, valuePopularityMap, false);
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
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularityMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularityMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuePopularityMap.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .iterator();
    }

    private <T> void increasePopularity(T popularityMapKey, Map<T, Integer> popularityMap, boolean isKey) {
        if (popularityMapKey == null) {
            return;
        }
        popularityMap.compute(popularityMapKey, (key, popularity) -> {
            if (popularity == null) {
                popularity = 0;
            }
            popularity++;
            if (isKey) {
                updatePopularKey((K) popularityMapKey, popularity);
            } else {
                updatePopularValue((V) popularityMapKey, popularity);
            }
            return popularity;
        });
    }

    private void updatePopularKey(K key, Integer popularity) {
        if (popularity > maxKeyPopularity) {
            maxKeyPopularity = popularity;
            popularKey = key;
        }
    }

    private void updatePopularValue(V value, Integer popularity) {
        if (popularity > maxValuePopularity) {
            maxValuePopularity = popularity;
            popularValue = value;
        }
    }
}
