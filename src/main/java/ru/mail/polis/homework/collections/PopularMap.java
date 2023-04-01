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
    private Map<K, Integer> keysPopularity;
    private Map<K, Integer> keysSorted;
    private Map<V, Integer> valuesPopularity;
    private Map<V, Integer> valuesSorted;


    public PopularMap() {
        this.map = new HashMap<>();
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();

        sortedMapsInit();
    }

    private void sortedMapsInit() { //инициализация компараторов и мап для отсортированных ключей/значений по популярности
        ValueComparator keysComparator = new ValueComparator((Map<Object, Integer>) keysPopularity);
        keysSorted = new TreeMap<>(keysComparator);

        ValueComparator valuesComparator = new ValueComparator((Map<Object, Integer>) valuesPopularity);
        valuesSorted = new TreeMap<>(valuesComparator);
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();

        sortedMapsInit();
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
        keyIncrement(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        valueIncrement((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        keyIncrement(key);
        valueIncrement(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        keyIncrement(key);
        if (map.containsKey(key)) {                  //популярность значения, которе лежало в мапе на момент вызова метода увеличивается
            valueIncrement(map.get(key));
        }
        valueIncrement(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        keyIncrement(key);
        valueIncrement(map.get(key));

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

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return getSortedKeys().lastKey();
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (keysPopularity.isEmpty() || !keysPopularity.containsKey(key)) {
            return 0;
        }
        return keysPopularity.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getSortedValues().lastKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {

        return getSortedValues().keySet().iterator();
    }

    private void keyIncrement(Object key) {
        keysPopularity.merge((K) key, 1, Integer::sum);
    }

    private void valueIncrement(V value) {
        valuesPopularity.merge((V) value, 1, Integer::sum);
    }

    private TreeMap<K, Integer> getSortedKeys() {
        if (keysSorted.isEmpty()) {
            keysSorted.putAll(keysPopularity);
        }
        return (TreeMap<K, Integer>) keysSorted;
    }


    private TreeMap<V, Integer> getSortedValues() {
        if (valuesSorted.isEmpty()) {
            valuesSorted.putAll(valuesPopularity);
        }
        return (TreeMap<V, Integer>) valuesSorted;
    }
}
