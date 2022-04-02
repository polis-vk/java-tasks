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
    private final Map<K, Integer> popularKey = new HashMap<>();
    private final Map<V, Integer> popularValue = new HashMap<>();

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
        popularityIncrease((K) key, popularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        popularityIncrease(value, popularValue);
        popularityIncrease((K) key, popularKey);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V lastValue = map.put(key, value);
        popularityIncrease(lastValue, popularValue);
        popularityIncrease(value, popularValue);
        popularityIncrease((K) key, popularKey);
        return lastValue;
    }

    @Override
    public V remove(Object key) {
        V lastValue = map.remove(key);
        popularityIncrease(lastValue, popularValue);
        popularityIncrease((K) key, popularKey);
        return lastValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            map.clear();
        }
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
        return popularKey.entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey)
                .orElse(null);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (popularKey.get(key) == null) {
            return 0;
        }
        return popularKey.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue.entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey)
                .orElse(null);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (popularValue.get(value) == null) {
            return 0;
        }
        return popularValue.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        ArrayList<V> values = new ArrayList<>(popularValue.keySet());
        values.sort(Comparator.comparingInt(popularValue::get));
        return values.iterator();
    }

    public <T> void popularityIncrease(T value, Map<T, Integer> popularMap) {
        if (value == null) {
            return;
        }
        if (!popularMap.containsKey(value)) {
            popularMap.put(value, 1);
            return;
        }
        popularMap.put(value, popularMap.get(value) + 1);
    }
}
