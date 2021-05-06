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
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        this.updateChart((K) key, this.keyChart);
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        this.updateChart((V) value, this.valueChart);
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        this.updateChart((K) key, this.keyChart);

        V value = this.map.get(key);
        this.updateChart(value, this.valueChart);

        return value;
    }

    @Override
    public V put(K key, V value) {
        this.updateChart(key, this.keyChart);
        this.updateChart(value, this.valueChart);

        V previousValue = this.map.put(key, value);
        this.updateChart(previousValue, this.valueChart);

        return previousValue;
    }

    @Override
    public V remove(Object key) {
        this.updateChart((K) key, this.keyChart);

        V value = this.map.get(key);
        this.updateChart(value, this.valueChart);

        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
        this.keyChart.clear();
        this.valueChart.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     * 1 балл
     */
    public K getPopularKey() {
        return this.getPopularItem(this.keyChart);
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return this.keyChart.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return this.getPopularItem(this.valueChart);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return this.valueChart.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> list = new ArrayList<>(this.valueChart.keySet());
        list.sort((V a, V b) -> {
            Integer valueA = this.valueChart.get(a);
            Integer valueB = this.valueChart.get(b);
            return valueA.compareTo(valueB);
        });
        return list.iterator();
    }

    private <T> void updateChart(T key, Map<T, Integer> chart) {
        if (key == null) return;

        Integer popularity = chart.getOrDefault(key, 0) + 1;
        chart.put(key, popularity);
    }

    private <T> T getPopularItem(Map<T, Integer> chart) {
        T key = null;
        Integer maxValue = 0;

        for (Map.Entry<T, Integer> entry : chart.entrySet()) {
            Integer value = entry.getValue();
            if (value > maxValue) {
                maxValue = value;
                key = entry.getKey();
            }
        }

        return key;
    }
}
