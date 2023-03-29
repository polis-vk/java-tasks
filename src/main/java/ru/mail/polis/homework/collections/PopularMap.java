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
    private Map<K, Integer> keys; //мапа с ключами и их популярностью
    private Map<K, Integer> keysSorted; //мапа с ключами, отсортированная по их популярности
    private Map<V, Integer> values; //мапа со значениями и их популярностью
    private Map<V, Integer> valuesSorted; //мапа со значениями, отсортированная по их популярности


    public PopularMap() {
        this.map = new HashMap<>();
        keys = new HashMap<>();
        values = new HashMap<>();

        sortedMapsInit();
    }
    private void sortedMapsInit() { //инициализация компараторов и мап для отсортированных ключей/значений по популярности
        ValueComparator keysComparator = new ValueComparator((Map<Object, Integer>) keys);
        keysSorted = new TreeMap<>(keysComparator);

        ValueComparator valuesComparator = new ValueComparator((Map<Object, Integer>) values);
        valuesSorted = new TreeMap<>(valuesComparator);
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;

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
        increaseKeyPopularity(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increaseValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        increaseKeyPopularity(key);
        increaseValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increaseKeyPopularity(key);
        if(map.containsKey(key) ){                  //популярность значения, которе лежало в мапе на момент вызова метода увеличивается
            increaseValuePopularity(map.get(key));
        }
        increaseValuePopularity(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity(key);
        increaseValuePopularity(map.get(key));

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
        return getSortedKeys().keySet().iterator().next(); //возвращается первый ключ из отсортированного (по убыванию популярности) списка ключей
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if(keys.isEmpty() || !keys.containsKey(key)) {
            return 0;
        }
        return keys.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
            return getSortedValues().keySet().iterator().next();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if(keys.isEmpty() || !values.containsKey(value)) {
            return 0;
        }
        return values.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return values.keySet().iterator();
    }

    private void increaseKeyPopularity(Object key) {
        if(keys != null) {
            if (keys.containsKey(key)) {
                keys.put((K) key, keys.get(key) + 1);
            } else {
                keys.put((K) key, 1);
            }
        }
    }

    private void increaseValuePopularity(V value) {
        if(values != null) {
            if (values.containsKey(value)) {
                values.put(value, values.get(value) + 1);
            } else if(value != null) {
                values.put(value, 1);
            }
        }
    }

    private Map<K, Integer> getSortedKeys() {
        if(keys.isEmpty()) {
            return null;
        }
        keysSorted.putAll(keys);
        return keysSorted;
    }

    private Map<V, Integer> getSortedValues() {
        if(values.isEmpty()) {
            return null;
        }
        valuesSorted.putAll(values);
        return valuesSorted;
    }
}
