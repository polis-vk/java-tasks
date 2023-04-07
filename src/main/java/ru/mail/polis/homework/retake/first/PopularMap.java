package ru.mail.polis.homework.retake.first;


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
 * Всего 10 баллов
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keysPopularity;
    private final Map<V, Integer> valuesPopularity;

    private K mostPopularKey;
    private int mostPopularityAmongKeys = 0;
    private V mostPopularValue;
    private int mostPopularityAmongValues = 0;


    public PopularMap() {
        this.map = new HashMap<>();
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
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
        V result = map.get(key);
        if (result != null) {
            increaseValuePopularity(result);
        }
        increaseKeyPopularity((K) key);
        return result;
    }

    @Override
    public V put(K key, V value) {
        V result = map.put(key, value);
        if (result != null) {
            increaseValuePopularity(result);
        }
        increaseKeyPopularity(key);
        increaseValuePopularity(value);
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = map.remove(key);
        if (result != null) {
            increaseValuePopularity(result);
        }
        increaseKeyPopularity((K) key);
        return result;
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
        return mostPopularKey;
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keysPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valuesPopularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .iterator();
    }

    private void increaseKeyPopularity(K key) {
        int newKeyPopularity = keysPopularity.getOrDefault(key, 0) + 1;
        keysPopularity.put(key, newKeyPopularity);
        if (newKeyPopularity > mostPopularityAmongKeys) {
            mostPopularityAmongKeys = newKeyPopularity;
            mostPopularKey = key;
        }
    }

    private void increaseValuePopularity(V value) {
        int newValuePopularity = valuesPopularity.getOrDefault(value, 0) + 1;
        valuesPopularity.put(value, newValuePopularity);
        if (newValuePopularity > mostPopularityAmongValues) {
            mostPopularityAmongValues = newValuePopularity;
            mostPopularValue = value;
        }
    }
}
