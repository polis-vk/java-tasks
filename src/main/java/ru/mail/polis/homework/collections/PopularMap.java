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
    private HashMap<K, Integer> popularKey = new HashMap<>();
    private HashMap<V, Integer> popularObject = new HashMap<>();

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
        System.out.println(map.toString() + "    map");
        System.out.println("get   " + key.toString());
        KeyValueCheck((K) key);
        Object tempV = map.get(key);
        if (map.containsKey(key)) {
            ObjValueCheck((V) tempV);
        }
        return (V) tempV;
    }

    @Override
    public V put(K key, V value) {
        System.out.println(map.toString() + "    map");
        System.out.println("put   " + key.toString() + "    " + value.toString());
        KeyValueCheck(key);
        ObjValueCheck(value);
        if (map.get(key) == value) {
            ObjValueCheck(value);
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        System.out.println(map.toString() + "    map");
        System.out.println("remove   " + key.toString());
        KeyValueCheck((K) key);
        if (map.get(key) != null) {
            ObjValueCheck(map.get(key));
        }
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Object K : m.keySet()) {
            ObjValueCheck((V) m.get(K));
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
     * Возвращает самый популярный, на данный момент, ключ 1 балл
     */
    public K getPopularKey() {
        int max = 0;
        Object key = new Object();
        for (Object K : popularKey.keySet()) {
            int temp = popularKey.get(K);
            System.out.println("getPopularValue   " + K.toString() + "  " + temp);
            if (temp >= max) {
                max = temp;
                key = K;
            }
        }
        return (K) key;
    }

    /**
     * Возвращает количество использование ключа 1 балл
     */
    public int getKeyPopularity(K key) {
        if (!popularKey.containsKey(key)) {
            return 0;
        }
        return popularKey.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что
     * значени может быть более одного 1 балл
     */
    public V getPopularValue() {
        int max = 0;
        Object value = new Object();
        for (Object V : popularObject.keySet()) {
            int temp = popularObject.get(V);
            System.out.println("getPopularValue   " + V.toString() + "  " + temp);
            if (temp >= max) {
                max = temp;
                value = V;
            }
        }
        return (V) value;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get,
     * put (учитывается 2 раза, если старое значение и новое - одно и тоже), remove
     * (считаем по старому значению). 1 балл
     */
    public int getValuePopularity(V value) {
        if (!popularObject.containsKey(value)) {
            return 0;
        }
        return popularObject.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к
     * самым популярным) 2 балла
     */
    public Iterator<V> popularIterator() {
        popularObject.entrySet().stream()
        .sorted(Map.Entry.<V, Integer>comparingByValue());
        return popularObject.keySet().iterator();
    }

    public void KeyValueCheck(K key) {
        if (popularKey.containsKey(key)) {
            int temp = popularKey.get(key);
            popularKey.put(key, 1 + temp);
        } else {
            popularKey.put(key, 1);
        }
        System.out.println(popularKey.toString() + " key");
    }

    public void ObjValueCheck(V value) {
        if (popularObject.containsKey(value)) {
            int temp = popularObject.get(value);
            popularObject.put(value, 1 + temp);
        } else {
            popularObject.put(value, 1);
        }
        System.out.println(popularObject.toString() + " obj");
    }
}
