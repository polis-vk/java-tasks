package ru.mail.polis.homework.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private Map<K, Integer> popularKeyMap;
    private Map<V, Integer> popularValueMap;


    public PopularMap() {
        this(new HashMap<>());
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        popularKeyMap = new HashMap<>();
        popularValueMap = new HashMap<>();
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
        increasePopularity(popularKeyMap, (K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity(popularValueMap, (V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increasePopularity(popularKeyMap, (K) key);
        V value = (V) map.get(key);
        increasePopularity(popularValueMap, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increasePopularity(popularKeyMap, (K) key);
        increasePopularity(popularValueMap, (V) value);
        V putValue = map.put(key, value);
        increasePopularity(popularValueMap, (V) putValue);
        return putValue;
    }

    @Override
    public V remove(Object key) {
        increasePopularity(popularKeyMap, (K) key);
        V removeValue = map.remove(key);
        increasePopularity(popularValueMap, removeValue);
        return removeValue;
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
     * 1 балл
     */
    public K getPopularKey() {
        return popularKeyMap.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return popularKeyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return popularValueMap.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        if (popularValueMap.containsKey(value)) {
            return popularValueMap.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        Set<V> valuesSet = popularValueMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toSet());
        return valuesSet.iterator();
    }

    private <T> void increasePopularity(Map<T, Integer> map, T elm) {
        if (elm != null) {
            map.merge(elm, 1, Integer::sum);
        }
    }
}
