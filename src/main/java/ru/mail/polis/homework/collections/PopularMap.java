package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 * <p>
 * Дополнительное задание описано будет ниже
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<Object, Integer> mapKeyPopularity;
    private final Map<Object, Integer> mapValuePopularity;


    public PopularMap() {
        this.map = new HashMap<>();
        this.mapKeyPopularity = new HashMap<Object, Integer>();
        this.mapValuePopularity = new HashMap<Object, Integer>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.mapKeyPopularity = new HashMap<Object, Integer>();
        this.mapValuePopularity = new HashMap<Object, Integer>();
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
        updatePopularity(key, mapKeyPopularity);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValuePopularity(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updatePopularity(key, mapKeyPopularity);
        V result = map.get(key);
        updateValuePopularity(result);
        return result;
    }

    @Override
    public V put(K key, V value) {
        updatePopularity(key, mapKeyPopularity);
        if (mapValuePopularity.containsKey(value)) {
            if (map.get(key) != null && map.get(key) == value) {
                mapValuePopularity.put(value, mapValuePopularity.get(value) + 2);
            } else {
                mapValuePopularity.put(value, mapValuePopularity.get(value) + 1);
            }
        } else {
            mapValuePopularity.put(value, 1);
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        updatePopularity(key, mapKeyPopularity);
        V result = map.remove(key);
        updateValuePopularity(result);
        return result;
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
        Object popularKey = null;
        for (Entry<Object, Integer> entry : mapKeyPopularity.entrySet()) {
            if (popularKey == null || mapKeyPopularity.get(popularKey) < entry.getValue()) {
                popularKey = entry.getKey();
            }
        }
        return (K) popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        Integer popularity = mapKeyPopularity.get(key);
        return popularity == null ? 0 : popularity;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        Object popularKey = null;
        for (Entry<Object, Integer> entry : mapValuePopularity.entrySet()) {
            if (popularKey == null || mapValuePopularity.get(popularKey) < entry.getValue()) {
                popularKey = entry.getKey();
            }
        }
        return (V) popularKey;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        Integer popularity = mapValuePopularity.get(value);
        return popularity == null ? 0 : popularity;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>();
        for (Object value : mapValuePopularity.keySet()) {
            values.add((V) value);
        }
        values.sort((Comparator.comparing(mapValuePopularity::get)));
        return values.iterator();
    }

    private void updatePopularity(Object element, Map<Object, Integer> map) {
        Integer popularity = map.get(element);
        popularity = popularity == null ? 0 : popularity;
        map.put(element, popularity + 1);
    }

    private void updateValuePopularity(Object value) {
        if (mapValuePopularity.containsKey(value)) {
            updatePopularity(value, mapValuePopularity);
        }
    }
}
