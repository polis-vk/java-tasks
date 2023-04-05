package ru.mail.polis.homework.collections;

import com.sun.org.apache.xpath.internal.compiler.OpMap;

import java.util.*;
import java.util.stream.Stream;


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

    private final Map<Object, Integer> popularKey = new HashMap<Object, Integer>();
    private final Map<Object, Integer> popularValue = new HashMap<Object, Integer>();
    private final Map<Object, V> oldMap = new HashMap<>();

    private final Map<K, V> map;

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
        refreshPopular((K) key, popularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        refreshPopular((K) value, popularValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        refreshPopular((K) key, popularKey);
        V value = map.get(key);
        if (value != null) {
            refreshPopular((K) value, popularValue);
        } else {
            V oldValue = oldMap.get(key);
            if (oldValue != null) {
                refreshPopular((K) oldValue, popularValue);
            }
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        refreshPopular(key, popularKey);
        refreshPopular((K) value, popularValue);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        refreshPopular((K) key, popularKey);
        V value = map.get(key);
        if (value != null) {
            refreshPopular((K) value, popularValue);
            oldMap.put(key, value);
        } else {
            refreshPopular((K) oldMap.get(key), popularValue);
        }
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
//        объединение словарей
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
        return popular(popularKey);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        K countKey = (K) popularKey.get(key);
        if (countKey == null) {
            return 0;
        }
        return (int) countKey;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return (V) popular(popularValue);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        K countValue = (K) popularValue.get(value);
        if (countValue == null) {
            return 0;
        }
        return (int) countValue;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        Map<Object, Integer> sortedMap = new LinkedHashMap<>();
        popularValue.entrySet()
                .stream()
                .sorted(Map.Entry.<Object, Integer>comparingByValue())
                .forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        return (Iterator<V>) sortedMap.keySet().iterator();
    }

    private void refreshPopular(K key, Map<Object, Integer> popularDict) {
        K value = (K) popularDict.get(key);
        if (value == null) {
            popularDict.put(key, 1);
        } else {
            popularDict.put(key, (int) value + 1);
        }
    }

    private K popular(Map<Object, Integer> popularMap) {
        return (K) Collections.max(popularMap.entrySet(), Comparator.comparingInt(Entry::getValue)).getKey();
    }
}
