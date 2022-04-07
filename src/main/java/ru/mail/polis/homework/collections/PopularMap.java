package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Comparator;

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
    private final Map<K, Integer> keyPopularMap;
    private final Map<V, Integer> valuePopularMap;
    private K mostPopularKey;
    private V mostPopularValue;

    public PopularMap() {
        this(new HashMap<>());
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularMap = new HashMap<>();
        this.valuePopularMap = new HashMap<>();
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
        mostPopularKey = increasePopularity((K) key, keyPopularMap, mostPopularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        mostPopularValue = increasePopularity((V) value, valuePopularMap, mostPopularValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        mostPopularKey = increasePopularity((K) key, keyPopularMap, mostPopularKey);
        V value = map.get(key);
        mostPopularValue = increasePopularity(value, valuePopularMap, mostPopularValue);
        return value;
    }

    @Override
    public V put(K key, V value) {
        mostPopularKey = increasePopularity(key, keyPopularMap, mostPopularKey);
        V oldValue = map.put(key, value);
        mostPopularValue = increasePopularity(oldValue, valuePopularMap, mostPopularValue);
        mostPopularValue = increasePopularity(value, valuePopularMap, mostPopularValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        mostPopularKey = increasePopularity((K) key, keyPopularMap, mostPopularKey);
        V value = map.remove(key);
        mostPopularValue = increasePopularity(value, valuePopularMap, mostPopularValue);
        return value;
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

    private <T> T increasePopularity(T element, Map<T, Integer> popularMap, T popularElement) {
        if (element == null) {
            return popularElement;
        }
        int popularity = popularMap.merge(element, 1, Integer::sum);
        if (element.equals(popularElement) || popularElement == null || popularMap.get(popularElement) < popularity) {
            return element;
        }
        return popularElement;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     * 1 балл
     */
    public K getPopularKey() {
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return keyPopularMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return valuePopularMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        return valuePopularMap
                .entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .iterator();
    }
}
