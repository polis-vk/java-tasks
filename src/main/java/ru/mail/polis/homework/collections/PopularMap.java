package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.Comparator;
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

    private K rememberKey;
    private V rememberValue;
    private final Map<K, V> map;
    private final Map<K, Integer> popularKey = new HashMap<>();
    private final Map<V, Integer> popularValue = new HashMap<>();

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
        rememberKey = popularityIncrease((K) key, popularKey, rememberKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        rememberValue = popularityIncrease((V) value, popularValue, rememberValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        rememberKey = popularityIncrease((K) key, popularKey, rememberKey);
        rememberValue = popularityIncrease(value, popularValue, rememberValue);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V lastValue = map.put(key, value);
        rememberValue = popularityIncrease(lastValue, popularValue, rememberValue);
        rememberKey = popularityIncrease((K) key, popularKey, rememberKey);
        rememberValue = popularityIncrease(value, popularValue, rememberValue);
        return lastValue;
    }

    @Override
    public V remove(Object key) {

        rememberValue = popularityIncrease(map.get(key), popularValue, rememberValue);
        rememberKey = popularityIncrease((K) key, popularKey, rememberKey);
        V lastValue = map.remove(key);
        return lastValue;
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
        return rememberKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (popularKey.get(key) != null) {
            return popularKey.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return rememberValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (popularValue.get(value) != null) {
            return popularValue.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        Iterator<V> popularityIteraror;
        popularityIteraror = popularValue.keySet().stream()
                .sorted(Comparator.comparingInt(popularValue::get))
                .collect(Collectors.toList())
                .iterator();
        return popularityIteraror;
    }

    public <T> T popularityIncrease(T value, Map<T, Integer> popularMap, T rememberValue) {
        if (value == null) {
            return rememberValue;
        }
        if (!popularMap.containsKey(value)) {
            popularMap.put(value, 1);
        } else {
            popularMap.put(value, popularMap.get(value) + 1);
        }
        if (rememberValue == null) {
            return value;
        }
        if (popularMap.get(rememberValue) >= popularMap.get(value)) {
            return rememberValue;
        }
        return value;
    }
}
