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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> popularKeyMap;
    private final Map<V, Integer> popularValueMap;

    private K mostPopularKey;
    private V mostPopularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        popularKeyMap = new HashMap<>();
        popularValueMap = new HashMap<>();

        mostPopularKey = null;
        mostPopularValue = null;
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        popularKeyMap = new HashMap<>();
        popularValueMap = new HashMap<>();

        mostPopularKey = null;
        mostPopularValue = null;
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
        addPopularity(popularKeyMap, (K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addPopularity(popularValueMap, (V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        addPopularity(popularKeyMap, (K) key);
        addPopularity(popularValueMap, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V previouslyValue = map.put(key, value);
        addPopularity(popularKeyMap, key);
        addPopularity(popularValueMap, value);
        addPopularity(popularValueMap, previouslyValue);
        return previouslyValue;
    }

    @Override
    public V remove(Object key) {
        V removedValue = map.remove(key);
        addPopularity(popularKeyMap, (K) key);
        addPopularity(popularValueMap, removedValue);
        return removedValue;
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
        return mostPopularKey = updatingTheMostPopular(popularKeyMap, mostPopularKey);
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return popularKeyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return mostPopularValue = updatingTheMostPopular(popularValueMap, mostPopularValue);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return popularValueMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> sortedValueList = new ArrayList<>(popularValueMap.keySet());
        sortedValueList.sort(Comparator.comparing(popularValueMap::get));
        return sortedValueList.iterator();
    }

    private <T> void addPopularity(Map<T, Integer> map, T key) {
        if (key != null) {
            map.compute(key, (k, v) -> v == null ? 1 : v + 1);
            mostPopularValue = null;
            mostPopularKey = null;
        }
    }

    private <T> T updatingTheMostPopular(Map<T, Integer> map, T popularKey) {
        return popularKey == null ? Collections.max(map.entrySet(), Entry.comparingByValue()).getKey() : popularKey;
    }
}
