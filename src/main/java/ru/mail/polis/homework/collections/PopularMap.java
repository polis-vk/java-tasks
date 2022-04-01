package ru.mail.polis.homework.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    private final Map<Object, Integer> popularKeys = new HashMap<>();
    private boolean keyChangeChek = false;
    private K popularKey = null;
    private final Map<Object, Integer> popularValues = new HashMap<>();
    private boolean valueChangeChek = false;
    private V popularValue = null;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        addPopularKeyOrValue(popularKeys, key);
        keyChangeChek = true;
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularKeyOrValue(popularValues, value);
        valueChangeChek = true;
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addPopularKeyOrValue(popularKeys, key);
        keyChangeChek = true;

        V value = map.get(key);
        if (value != null) {
            addPopularKeyOrValue(popularValues, value);
            valueChangeChek = true;
        }

        return value;
    }

    @Override
    public V put(K key, V value) {
        addPopularKeyOrValue(popularKeys, key);
        keyChangeChek = true;

        V oldValue = map.put(key, value);
        if (oldValue != null) {
            addPopularKeyOrValue(popularValues, oldValue);

            if (oldValue.equals(value)) {
                addPopularKeyOrValue(popularValues, value);
                return oldValue;
            }
        }
        addPopularKeyOrValue(popularValues, value);
        valueChangeChek = true;

        return oldValue;
    }

    @Override
    public V remove(Object key) {
        addPopularKeyOrValue(popularKeys, key);
        keyChangeChek = true;

        V removeValue = map.remove(key);
        if (removeValue != null) {
            addPopularKeyOrValue(popularValues, removeValue);
            valueChangeChek = true;
        }

        return removeValue;
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

    private void addPopularKeyOrValue(Map<Object, Integer> mapPopular, Object value) {
        if (mapPopular.putIfAbsent(value, 1) != null) {
            mapPopular.computeIfPresent(value, (k, v) -> v + 1);
        }
    }


    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        if (keyChangeChek) {
            popularKey = (K) popularKeys.entrySet().stream()
                    .max(Entry.comparingByValue())
                    .get().getKey();
        }

        keyChangeChek = false;
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        if (valueChangeChek) {
            popularValue = (V) popularValues.entrySet().stream()
                    .max(Entry.comparingByValue())
                    .get().getKey();
        }

        valueChangeChek = false;
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return new PopularMapIterator();
    }

    private class PopularMapIterator implements Iterator<V> {

        List<V> listPopular = new ArrayList<>();

        public PopularMapIterator() {
            popularValues.entrySet().stream()
                    .sorted(Comparator.comparingInt(e -> e.getValue()))
                    .forEach((entry) -> listPopular.add((V) entry.getKey()));
        }

        @Override
        public boolean hasNext() {
            return !listPopular.isEmpty();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return listPopular.remove(0);
        }
    }


}
