package ru.mail.polis.homework.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
    public boolean changed = true;

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
        return map.containsValue((V) value);
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
        if (map.get(key) != null && map.get(key) != value) {
            ObjValueCheck(map.get(key));
        }
        KeyValueCheck(key);
        ObjValueCheck(value);
        if (map.get(key) == value) {
            ObjValueCheck(value);
            return value;
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        KeyValueCheck((K) key);
        if (map.get(key) != null) {
            ObjValueCheck(map.get(key));
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
        if (changed) {
            int maxKeyCount = 0;
            for (K K : popularKey.keySet()) {
                int temp = popularKey.get(K);
                if (temp >= maxKeyCount) {
                    maxKeyCount = temp;
                    maxKey = K;
                }
            }
            changed = false;
        }
        return maxKey;
    }

    public V getPopularValue() {
        if (changed) {
            int maxObjCount = 0;
            for (V V : popularObject.keySet()) {
                int temp = popularObject.get(V);
                if (temp >= maxObjCount) {
                    maxObjCount = temp;
                    maxObj = V;
                }
            }
            changed = false;
        }
        return maxObj;
    }

    public int getKeyPopularity(K key) {
        Integer tempValue = popularKey.get(key);
        if (tempValue == null) {
            return 0;
        }
        return popularKey.get(key);
    }

    public int getValuePopularity(V value) {
        Integer tempValue = popularObject.get(value);
        if (tempValue == null) {
            return 0;
        }
        return tempValue;
    }

    public Iterator<V> popularIterator() {
        HashMap<V, Integer> tempKey = new HashMap<>();
        popularObject.entrySet().stream().sorted(Map.Entry.<V, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> tempKey.put(x.getKey(), x.getValue()));
        return tempKey.keySet().iterator();
    }

    public void KeyValueCheck(K key) {
        if (popularKey.containsKey(key)) {
            popularKey.put(key, 1 + popularKey.get(key));
        } else {
            popularKey.put(key, 1);
        }
        changed = true;
    }

    public void ObjValueCheck(V value) {
        if (popularObject.containsKey(value)) {
            popularObject.put(value, 1 + popularObject.get(value));
        } else {
            popularObject.put(value, 1);
        }
        changed = true;
    }
}
