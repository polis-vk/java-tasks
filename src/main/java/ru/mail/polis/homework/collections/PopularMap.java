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
    private final Map<K, V> map;
    private final Map<K, Integer> popularKeys = new HashMap<>();
    private final Map<V, Integer> popularValues = new HashMap<>();

    private <T> void addPopularMap(Map<T, Integer> popularMap, Object key) {
        if (key != null) {
            if (popularMap.containsKey(key)) {
                popularMap.put((T) key, popularMap.get(key) + 1);
            } else {
                popularMap.put((T) key, 1);
            }
        }
    }

    private <T> T getPopularElement(Map<T, Integer> popularElements) {
        T popularElement = null;
        int maxAppearances = 0;
        for (T key : popularElements.keySet()) {
            if (popularElements.get(key) >= maxAppearances) {
                maxAppearances = popularElements.get(key);
                popularElement = key;
            }
        }
        return popularElement;
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
        V value = this.map.get(key);
        addPopularMap(popularKeys, key);
        addPopularMap(popularValues, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        addPopularMap(popularKeys, key);
        addPopularMap(popularValues, value);
        return this.map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V value = this.map.remove(key);
        addPopularMap(popularKeys, key);
        addPopularMap(popularValues, value);
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
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
        return getPopularElement(popularKeys);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!popularKeys.containsKey(key)) {
            return 0;
        }
        return popularKeys.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        addPopularMap(popularValues, getPopularElement(popularValues));
        return getPopularElement(popularValues);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!popularValues.containsKey(value)) {
            return 0;
        }
        return popularValues.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<Entry<V, Integer>> sortedEntries = new ArrayList<>(popularValues.entrySet());
        sortedEntries.sort(new Comparator<Entry<V, Integer>>() {
            @Override
            public int compare(Entry<V, Integer> first, Entry<V, Integer> second) {
                return first.getValue() - second.getValue();
            }
        });
        List<V> sortedValues = new ArrayList<>();
        for (Entry<V, Integer> entry : sortedEntries) {
            sortedValues.add(entry.getKey());
        }
        return sortedValues.iterator();
    }
}
