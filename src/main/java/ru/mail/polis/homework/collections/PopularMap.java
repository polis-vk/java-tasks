package ru.mail.polis.homework.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных
 * методов. 4 дополнительных метода должны возвращать самый популярный ключ и
 * его популярность. (аналогично для самого популярного значения) Популярность -
 * это количество раз, который этот ключ/значение учавствовал/ло в других
 * методах мапы, такие как containsKey, get, put, remove (в качестве параметра и
 * возвращаемого значения). Считаем, что null я вам не передаю ни в качестве
 * ключа, ни в качестве значения
 *
 * Так же надо сделать итератор (подробности ниже).
 *
 * Важный момент, вам не надо реализовывать мапу, вы должны использовать
 * композицию. Вы можете использовать любые коллекции, которые есть в java.
 *
 * Помните, что по мапе тоже можно итерироваться
 *
 * for (Map.Entry<K, V> entry : map.entrySet()) { entry.getKey();
 * entry.getValue(); }
 *
 * Всего 9 баллов (2 за общие методы, 6 за специальные)
 * 
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
@SuppressWarnings("unchecked")
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final HashMap<K, Integer> popularKey = new HashMap<>();
    private final HashMap<V, Integer> popularObject = new HashMap<>();
    private K maxKey;
    private V maxObj;
    private V tempV;
    private Integer popK = 0;
    private Integer popV = 0;
    private Integer counter;

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
        KeyValueCheck((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        ObjValueCheck((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        KeyValueCheck((K) key);
        V tempV = map.get(key);
        if (tempV != null) {
            ObjValueCheck(tempV);
        }
        return tempV;
    }

    @Override
    public V put(K key, V value) {
        tempV = map.get(key);
        if (tempV != null && tempV != value) {
            ObjValueCheck(tempV);
        }
        KeyValueCheck(key);
        ObjValueCheck(value);
        if (tempV == value) {
            ObjValueCheck(value);
            return value;
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        KeyValueCheck((K) key);
        tempV = map.get(key);
        if (tempV != null) {
            ObjValueCheck(tempV);
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

    public K getPopularKey() {
        return maxKey;
    }

    public V getPopularValue() {
        return maxObj;
    }

    public int getKeyPopularity(K key) {
        counter = popularKey.get(key);
        if (counter == null) {
            return 0;
        }
        return counter;
    }

    public int getValuePopularity(V value) {
        counter = popularObject.get(value);
        if (counter == null) {
            return 0;
        }
        return counter;
    }

    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(popularObject.keySet());
        values.sort(Comparator.comparing(popularObject::get));
        return values.iterator();
    }

    public void KeyValueCheck(K key) {
        counter = popularKey.get(key);
        if (counter != null) {
            popularKey.put(key, 1 + counter);
        } else {
            popularKey.put(key, 1);
            counter = 1;
        }
        if (counter > popK) {
            popK = counter;
            maxKey = key;
        }
    }

    public void ObjValueCheck(V value) {
        counter = popularObject.get(value);
        if (counter != null) {
            popularObject.put(value, 1 + counter);
        } else {
            popularObject.put(value, 1);
            counter = 1;
        }
        if (counter > popV) {
            popV = counter;
            maxObj = value;
        }
    }
}
