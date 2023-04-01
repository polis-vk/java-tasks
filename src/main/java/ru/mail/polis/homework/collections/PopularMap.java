package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


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
    private final Map<K, Integer> mapPopularKey = new HashMap<>();
    private final Map<V, Integer> mapPopularValue = new HashMap<>();
    private K popularKey;
    private V popularValue;

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
        popularKey = raiseObjectPopularity((K) key, mapPopularKey, popularKey);
        return (map.containsKey(key));
    }

    @Override
    public boolean containsValue(Object value) {
        popularValue = raiseObjectPopularity((V) value, mapPopularValue, popularValue);
        return (map.containsValue(value));
    }

    @Override
    public V get(Object key) {
        popularKey = raiseObjectPopularity((K) key, mapPopularKey, popularKey);
        V gottenValue = map.get(key);
        popularValue = raiseObjectPopularity((V) gottenValue, mapPopularValue, popularValue);
        return gottenValue;
    }

    @Override
    public V put(K key, V value) {
        popularKey = raiseObjectPopularity((K) key, mapPopularKey, popularKey);
        popularValue = raiseObjectPopularity((V) value, mapPopularValue, popularValue);
        V newValue = map.put((K) key, (V) value);
        popularValue = raiseObjectPopularity(newValue, mapPopularValue, popularValue);
        return newValue;
    }

    @Override
    public V remove(Object key) {
        popularKey = raiseObjectPopularity((K) key, mapPopularKey, popularKey);
        V removedValue = map.remove(key);
        popularValue = raiseObjectPopularity((V) removedValue, mapPopularValue, popularValue);
        return removedValue;
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

    private <T> T raiseObjectPopularity(T element, Map<T, Integer> mapPopularity, T popularElement) {
        if (element == null) {
            return popularElement;
        }
        mapPopularity.compute(element, (key, value) -> value == null ? 1 : value + 1);
        if (popularElement == null) {
            return element;
        }
        return mapPopularity.get(element) > mapPopularity.get(popularElement) ? element : popularElement;
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
        return mapPopularKey.getOrDefault(key, 0);
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
        return mapPopularValue.getOrDefault(value,0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return mapPopularValue
                .entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .iterator();
    }
}
