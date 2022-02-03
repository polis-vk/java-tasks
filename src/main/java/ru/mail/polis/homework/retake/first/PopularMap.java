package ru.mail.polis.homework.retake.first;


import java.util.Collection;
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
 * Всего 10 баллов
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularityMap;
    private final Map<V, Integer> valuePopularityMap;
    private List<V> sortedValues;

    {
        this.keyPopularityMap = new HashMap<>();
        this.valuePopularityMap = new HashMap<>();

    }

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
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
        boolean result = this.map.containsKey(key);
        this.keyPopularityMap.merge((K) key, 1, Integer::sum);
        return result;
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V result = this.map.get(key);
        this.keyPopularityMap.merge((K) key, 1, Integer::sum);
        if (result != null) {
            this.valuePopularityMap.merge(result, 1, Integer::sum);
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        V result = this.map.put(key, value);
        this.keyPopularityMap.merge(key, 1, Integer::sum);
        if (value != null) {
            this.valuePopularityMap.merge(value, 1, Integer::sum);
        }
        if (result != null) {
            this.valuePopularityMap.merge(result, 1, Integer::sum);
        }
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = this.map.remove(key);
        this.keyPopularityMap.merge((K) key, 1, Integer::sum);
        if (result != null) {
            this.valuePopularityMap.merge(result, 1, Integer::sum);
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
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
     */
    public K getPopularKey() {
        int max = 0;
        K popularKey = null;
        for (Map.Entry<K, Integer> entry : this.keyPopularityMap.entrySet()) {
            if (entry.getValue() > max) {
                popularKey = entry.getKey();
                max = entry.getValue();
            }
        }
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return this.keyPopularityMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int max = 0;
        V popularValue = null;
        for (Map.Entry<V, Integer> entry : this.valuePopularityMap.entrySet()) {
            if (entry.getValue() > max) {
                popularValue = entry.getKey();
                max = entry.getValue();
            }
        }
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return this.valuePopularityMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return new PopularIterator();
    }

    private class PopularIterator implements Iterator<V> {

        private int current;

        PopularIterator() {
            this.current = 0;
            sortedValues = valuePopularityMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .map(Entry::getKey)
                    .collect(Collectors.toList());
        }
        @Override
        public boolean hasNext() {
            return current < sortedValues.size();
        }

        @Override
        public V next() {
            return sortedValues.get(current++);
        }
    }
}
