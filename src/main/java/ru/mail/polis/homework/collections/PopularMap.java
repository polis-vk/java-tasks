package ru.mail.polis.homework.collections;


import java.util.*;


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
    private final Map<K, Integer> keyPopularity;
    K popularKey;
    private final Map<V, Integer> valuePopularity;
    V popularValue;


    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularity = new HashMap<>();
        this.valuePopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularity = new HashMap<>();
        this.valuePopularity = new HashMap<>();
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
        increaseKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increaseValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increaseKeyPopularity((K) key);
        V currentValue = map.get(key);
        if (currentValue != null) {
            increaseValuePopularity(currentValue);
        }
        return currentValue;
    }

    @Override
    public V put(K key, V value) {
        increaseKeyPopularity(key);
        increaseValuePopularity(value);
        V prevValue = map.put(key, value);
        if (prevValue != null) {
            increaseValuePopularity(prevValue);
        }
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity((K) key);
        if (map.get(key) != null) {
            increaseValuePopularity(map.get(key));
        }
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry :
                m.entrySet()) {
            increaseValuePopularity(entry.getValue());
            increaseKeyPopularity(entry.getKey());
        }
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
        if (keyPopularity.containsKey(key)) {
            return keyPopularity.get(key).intValue();
        }
        return 0;
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
        if (valuePopularity.containsKey(value)) {
            return valuePopularity.get(value).intValue();
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(valuePopularity.keySet());
        values.sort(this::compare);
        return values.iterator();
    }

    public int compare(V firstValue, V secondValue) {
        return valuePopularity.get(firstValue).intValue() - valuePopularity.get(secondValue).intValue();
    }

    private void increaseKeyPopularity(K key) {
        if (keyPopularity.containsKey(key)) {
            keyPopularity.put(key, keyPopularity.get(key) + 1);
        } else {
            if (keyPopularity.size() == 0) {
                popularKey = key;
            }
            keyPopularity.put(key, 1);
        }
        if (keyPopularity.get(key).intValue() > keyPopularity.get(popularKey).intValue()) {
            popularKey = key;
        }
    }

    private void increaseValuePopularity(V value) {
        if (valuePopularity.containsKey(value)) {
            valuePopularity.put(value, valuePopularity.get(value) + 1);
        } else {
            if (valuePopularity.size() == 0) {
                popularValue = value;
            }
            valuePopularity.put(value, 1);
        }
        if (valuePopularity.get(value).intValue() > valuePopularity.get(popularValue).intValue()) {
            popularValue = value;
        }
    }
}
