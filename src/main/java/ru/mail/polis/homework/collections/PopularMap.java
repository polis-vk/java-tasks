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
    private final Map<K, Integer> numbersOfUsesKeys;
    private final Map<V, Integer> numbersOfUsesValues;

    public PopularMap() {
        this.map = new HashMap<>();
        numbersOfUsesKeys = new HashMap<>();
        numbersOfUsesValues = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        numbersOfUsesKeys = new HashMap<>();
        numbersOfUsesValues = new HashMap<>();
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
        setNumberOfUsesKeys((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        setNumbersOfUsesValues((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        setNumberOfUsesKeys((K) key);
        setNumbersOfUsesValues((V) value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        setNumberOfUsesKeys(key);
        setNumbersOfUsesValues(value);
        V oldValue = map.put(key, value);
        setNumbersOfUsesValues(oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V oldValue = map.remove(key);
        setNumberOfUsesKeys((K) key);
        setNumbersOfUsesValues(oldValue);
        return oldValue;
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
        List<Integer> valuesOfPopularity = new ArrayList<Integer>(numbersOfUsesKeys.values());
        Collections.sort(valuesOfPopularity, Collections.reverseOrder());
        for (Map.Entry<K, Integer> entry : numbersOfUsesKeys.entrySet()) {
            if (entry.getValue().equals(valuesOfPopularity.get(0))) {
                return entry.getKey();
            }
        }
        return null;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (numbersOfUsesKeys.get(key) != null) {
            return numbersOfUsesKeys.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        List<Integer> valuesOfPopularity = new ArrayList<Integer>(numbersOfUsesValues.values());
        Collections.sort(valuesOfPopularity, Collections.reverseOrder());
        for (Map.Entry<V, Integer> entry : numbersOfUsesValues.entrySet()) {
            if (entry.getValue().equals(valuesOfPopularity.get(0))) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (numbersOfUsesValues.get(value) != null) {
            return numbersOfUsesValues.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<Integer> valuesOfPopularity = new ArrayList<Integer>(numbersOfUsesValues.values());
        Collections.sort(valuesOfPopularity);
        List<V> sortedValuesOfMap = new ArrayList<V>();
        for (Integer element : valuesOfPopularity) {
            for (Entry<V, Integer> entry : numbersOfUsesValues.entrySet()) {
                if (entry.getValue().equals(element)) {
                    sortedValuesOfMap.add(entry.getKey());
                }
            }
        }
        return sortedValuesOfMap.iterator();
    }

    private void setNumberOfUsesKeys(K key) {
        if (numbersOfUsesKeys.get(key) != null) {
            numbersOfUsesKeys.put(key, numbersOfUsesKeys.get(key) + 1);
            return;
        }
        numbersOfUsesKeys.put(key, 1);
    }

    private void setNumbersOfUsesValues(V key) {
        if (key != null) {
            if (numbersOfUsesValues.get(key) != null) {
                numbersOfUsesValues.put(key, numbersOfUsesValues.get(key) + 1);
                return;
            }
            numbersOfUsesValues.put(key, 1);
        }
    }
}
