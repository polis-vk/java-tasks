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
    private Map<K, V> map;
    private Map<K, Integer> keyPopularity;
    private Map<V, Integer> valuePopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularity = new LinkedHashMap<>();
        this.valuePopularity = new LinkedHashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;

        this.keyPopularity = new LinkedHashMap<>();
        this.valuePopularity = new LinkedHashMap<>();

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
        boolean flag = map.containsKey(key);
        increaseKeyPopularity((K) key);
        return flag;

    }

    @Override
    public boolean containsValue(Object value) {
        boolean flag = false;
        flag = map.containsValue(value);
        increaseValuePopularity((V) value);
        return flag;
    }

    @Override
    public V get(Object key) {
        V result = map.get(key);
        increaseKeyPopularity((K) key);
        if (result != null) {
            valuePopularity.put(result, valuePopularity.get(result) + 1);
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        V result = map.put(key, value);
        increaseKeyPopularity(key);
        increaseValuePopularity(value);
        if (result != null) {
            valuePopularity.put(result, valuePopularity.get(result) + 1);
        }

        return result;
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity((K) key);
        V result = map.remove(key);
        if (result != null) {
            valuePopularity.put(result, valuePopularity.get(result) + 1);
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        K key;
        V value;
        map.putAll(m);
        for (Map.Entry<? extends K, ? extends V> x : m.entrySet()) {
            key = x.getKey();
            value = x.getValue();
            increaseKeyPopularity(key);
            increaseValuePopularity(value);
        }
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
        int max = Integer.MIN_VALUE;
        K popularKey = null;
        for (Map.Entry<K, Integer> x : keyPopularity.entrySet()) {
            if (max < x.getValue()) {
                max = x.getValue();
                popularKey = x.getKey();
            }
        }
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (keyPopularity.containsKey(key)) {
            return keyPopularity.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int max = Integer.MIN_VALUE;
        V popularValue = null;
        for (Map.Entry<V, Integer> x : valuePopularity.entrySet()) {
            if (max < x.getValue()) {
                max = x.getValue();
                popularValue = x.getKey();
            }
        }

        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (valuePopularity.containsKey(value)) {
            return valuePopularity.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(valuePopularity.keySet());
        values.sort((first, second) -> valuePopularity.get(first) - valuePopularity.get(second));
        return values.iterator();
    }

    private void increaseKeyPopularity(K key) {
        if (keyPopularity.containsKey(key)) {
            keyPopularity.put(key, keyPopularity.get(key) + 1);
        } else {
            keyPopularity.put(key, 1);
        }
    }

    private void increaseValuePopularity(V value) {
        if (valuePopularity.containsKey(value)) {
            valuePopularity.put(value, valuePopularity.get(value) + 1);
        } else {
            valuePopularity.put(value, 1);
        }
    }

}
