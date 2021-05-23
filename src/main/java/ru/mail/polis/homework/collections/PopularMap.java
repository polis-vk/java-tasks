package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


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
    private final Map<K, Integer> keyChart;
    private final Map<V, Integer> valueChart;

    private K topKey;
    private V topValue;

    public PopularMap() {
        this(new HashMap<>());
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyChart = new HashMap<>();
        this.valueChart = new HashMap<>();
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
        topKey = this.updateChart((K) key, keyChart);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        topValue = this.updateChart((V) value, valueChart);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        topKey = this.updateChart((K) key, keyChart);

        V value = map.get(key);
        topValue = this.updateChart(value, valueChart);

        return value;
    }

    @Override
    public V put(K key, V value) {
        topKey = this.updateChart(key, keyChart);
        topValue = this.updateChart(value, valueChart);

        V previousValue = map.put(key, value);
        topValue = this.updateChart(previousValue, valueChart);

        return previousValue;
    }

    @Override
    public V remove(Object key) {
        topKey = this.updateChart((K) key, keyChart);

        V value = map.get(key);
        topValue = this.updateChart(value, valueChart);

        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        keyChart.clear();
        valueChart.clear();
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
        return topKey;
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return keyChart.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return topValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return valueChart.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> list = new ArrayList<>(valueChart.keySet());
        list.sort((V a, V b) -> {
            Integer valueA = valueChart.get(a);
            Integer valueB = valueChart.get(b);
            return valueA.compareTo(valueB);
        });
        return list.iterator();
    }

    private <T> T updateChart(T key, Map<T, Integer> chart) {
        if (key != null) {
            chart.compute(key, (k, v) -> v == null ? 1 : v + 1);
        }

        return this.getPopularItem(chart);
    }

    private <T> T getPopularItem(Map<T, Integer> chart) {
        T key = null;
        int maxValue = 0;

        for (Map.Entry<T, Integer> entry : chart.entrySet()) {
            int value = entry.getValue();
            if (value > maxValue) {
                maxValue = value;
                key = entry.getKey();
            }
        }

        return key;
    }
}
