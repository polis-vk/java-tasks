package ru.mail.polis.homework.collections;


import java.util.*;


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

    private Integer maxValuePopularity = 0;
    private Integer maxKeyPopularity = 0;
    private K maxPopularKey;
    private V maxPopularValue;

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
        this.popularValue = new HashMap<>();
        this.popularKey = new HashMap<>();
    }

    private void incrementPopularValue(V val) {
        if (popularValue.containsKey(val)) {
            popularValue.put(val, (popularValue.get(val) + 1));
        } else {
            popularValue.put(val, 1);
        }
        Integer newVal = popularValue.get(val);
        if (newVal > maxValuePopularity) {
            maxValuePopularity = newVal;
            maxPopularValue = val;
        }
    }

    private void incrementPopularKey(K key) {
        if (popularKey.containsKey(key)) {
            popularKey.put(key, (popularKey.get(key) + 1));
        } else {
            popularKey.put(key, 1);
        }
        Integer newKeyPopularity = popularKey.get(key);
        if (newKeyPopularity > maxKeyPopularity) {
            maxKeyPopularity = newKeyPopularity;
            maxPopularKey = key;
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
        incrementPopularKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        incrementPopularValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        incrementPopularKey((K) key);
        if (map.containsKey(key)) {
            incrementPopularValue(map.get(key));
        }
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            incrementPopularValue(map.get(key));
        }
        incrementPopularKey(key);
        incrementPopularValue(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        incrementPopularKey((K) key);
        if (map.containsKey(key)) {
            incrementPopularValue(map.get(key));
        }
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
        return maxPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (popularKey.containsKey(key)) {
            return popularKey.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return maxPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (popularValue.containsKey(value)) {
            return popularValue.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        ArrayList<V> result = new ArrayList<>();
        for (Map.Entry<V, Integer> entry : popularValue.entrySet()) {
            result.add(entry.getKey());
        }
        result.sort(new Comparator<V>() {
            @Override
            public int compare(V o1, V o2) {
                Integer popularityO1 = getValuePopularity(o1);
                Integer popularityO2 = getValuePopularity(o2);
                return popularityO1.compareTo(popularityO2);
            }
        });
        return result.iterator();
    }
}
