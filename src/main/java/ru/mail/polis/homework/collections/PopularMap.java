package ru.mail.polis.homework.collections;


import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> mapMostPopularKey = new HashMap<>();
    private final Map<K, Integer> mapMostPopularValue = new HashMap<>();

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
        addNewEntry(mapMostPopularKey, (K) key, 1);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addNewEntry(mapMostPopularValue, (K) value, 1);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addNewEntry(mapMostPopularKey, (K) key, 1);

        V getValue = map.get(key);
        if (getValue != null) {
            addNewEntry(mapMostPopularValue, (K) getValue, 1);
        }

        return getValue;
    }

    @Override
    public V put(K key, V value) {
        addNewEntry(mapMostPopularKey, (K) key, 1);

        V putValue = map.put(key, value);
        if (putValue != null) {
            if (putValue.equals(value)) {
                addNewEntry(mapMostPopularValue, (K) value, 2);
                return putValue;
            }

            addNewEntry(mapMostPopularValue, (K) putValue, 1);
        }
        addNewEntry(mapMostPopularValue, (K) value, 1);

        return putValue;
    }

    @Override
    public V remove(Object key) {
        addNewEntry(mapMostPopularKey, (K) key, 1);

        V removeValue = map.remove(key);
        if (removeValue != null) {
            addNewEntry(mapMostPopularValue, (K) removeValue, 1);
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

    private void addNewEntry(Map<K, Integer> mapPopular, K element, int count) {
        if (mapPopular.containsKey(element)) {
            mapPopular.put(element, mapPopular.get(element) + count);
        } else {
            mapPopular.put(element, count);
        }
    }


    /**
     * Возвращает самый популярный, на данный момент, ключ
     * 1 балл
     */
    public K getPopularKey() {
        return mapMostPopularKey.entrySet().stream()
                .max(Entry.comparingByValue())
                .get().getKey();
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        Integer popularity = mapMostPopularKey.get(key);
        if (popularity != null) {
            return mapMostPopularKey.get(key);
        }

        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return (V) mapMostPopularValue.entrySet().stream()
                .max(Entry.comparingByValue())
                .get().getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        Integer popularity = mapMostPopularValue.get(value);
        if (popularity != null) {
            return mapMostPopularValue.get(value);
        }

        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        return new PopularMapIterator();
    }

    private class PopularMapIterator implements Iterator<V> {

        List<V> listPopular;

        public PopularMapIterator() {
            this.listPopular = new ArrayList(mapMostPopularValue.entrySet().stream()
                    .sorted(Comparator.comparingInt(e -> e.getValue()))
                    .collect(Collectors.toMap(
                            Entry::getKey,
                            Entry::getValue,
                            (a, b) -> {
                                throw new AssertionError();
                            },
                            LinkedHashMap::new
                    )).keySet());
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
