package ru.mail.polis.homework.collections;


import java.util.*;


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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private final Map<K, Integer> keysPopularity;
    private final Map<V, Integer> valuesPopularity;

    private K popularKey;
    private V popularValue;

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
        addPopularityKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularityValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        addPopularityKey((K) key);
        addPopularityValue(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        addPopularityKey(key);
        addPopularityValue(value);
        V oldValue = map.put(key, value);
        addPopularityValue(oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        addPopularityKey((K) key);
        V valueRemoved = map.remove(key);
        addPopularityValue(valueRemoved);
        return valueRemoved;
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
        return popularKey;
    }

    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return keysPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> valuesPopularityList = new ArrayList<>(valuesPopularity.keySet());
        valuesPopularityList.sort(Comparator.comparing(valuesPopularity::get));
        return valuesPopularityList.iterator();
    }

    private void addPopularityKey(K parameter) {
        popularKey = addPopularity(parameter, popularKey, keysPopularity);
    }

    private void addPopularityValue(V parameter) {
        popularValue = addPopularity(parameter, popularValue, valuesPopularity);
    }

    private <Z> Z addPopularity(Z parameter, Z mostPopular, Map<Z, Integer> popularMap) {
        if (parameter != null) {
            int parameterPopularity = popularMap.compute(parameter, (k, v) -> (v == null) ? 1 : v + 1);
            if (mostPopular == null || popularMap.get(mostPopular) < parameterPopularity) {
                return parameter;
            }
        }
        return mostPopular;
    }
}
