package ru.mail.polis.homework.collections;


import java.util.*;
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

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularity = new HashMap<>();  //ключ - популярность
    private final Map<V, Integer> valuePopularity = new HashMap<>();  //значение - популярность
    private int amountOfPopularKey;
    private K popularKey;

    private V popularValue;
    private int amountOfPopularValue;

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
        incrementKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        incrementValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V returnedResult = map.get(key);
        if (returnedResult != null) {
            incrementValuePopularity(returnedResult);
        }
        incrementKeyPopularity((K) key);
        return returnedResult;
    }

    @Override
    public V put(K key, V value) {
        incrementValuePopularity(value);
        incrementKeyPopularity(key);

        V returnedValue = map.put(key, value);
        if (returnedValue != null) {
            incrementValuePopularity(returnedValue);
        }
        return returnedValue;
    }

    @Override
    public V remove(Object key) {
        V returnedResult = map.remove(key);
        if (returnedResult != null) {
            incrementValuePopularity(returnedResult);
        }
        incrementKeyPopularity((K) key);
        return returnedResult;
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
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularity.getOrDefault(key, 0);
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
        return valuePopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuePopularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new)).keySet().iterator();
    }

    //считает популярность ключей
    private void incrementKeyPopularity(K key) {
        int returnedResult = keyPopularity.merge(key, 1, Integer::sum);
        if (returnedResult > amountOfPopularKey) {
            amountOfPopularKey = returnedResult;
            popularKey = key;
        }
    }

    //считает популярность значений
    private void incrementValuePopularity(V value) {
        int returnedResult = valuePopularity.merge(value, 1, Integer::sum);
        if (returnedResult > amountOfPopularValue) {
            amountOfPopularValue = returnedResult;
            popularValue = value;
        }
    }
}
