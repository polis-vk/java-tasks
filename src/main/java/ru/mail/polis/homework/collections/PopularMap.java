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
    private Map<K, Integer> numbersOfUsesKeys;
    private Map<V, Integer> numbersOfUsesValues;

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

    private void setNumberOfUsesKeys(K key) {
        if (checkingElForAbsenceWithKey(key)) {
            for (Map.Entry<K, Integer> entry : numbersOfUsesKeys.entrySet()) {
                if (key.equals(entry.getKey())) {
                    numbersOfUsesKeys.put(key, entry.getValue() + 1);
                }
            }
            return;
        }
        numbersOfUsesKeys.put(key, 1);
    }

    private void setNumbersOfUsesValues(V key) {
        if (key != null) {
            if (checkingElForAbsenceWithValue(key)) {
                for (Map.Entry<V, Integer> entry : numbersOfUsesValues.entrySet()) {
                    if (key.equals(entry.getKey())) {
                        numbersOfUsesValues.put(key, entry.getValue() + 1);
                    }
                }
                return;
            }
            numbersOfUsesValues.put(key, 1);
        }
    }

    private boolean checkingElForAbsenceWithKey(K key) {
        for (Map.Entry<K, Integer> entry : numbersOfUsesKeys.entrySet()) {
            if (key.equals(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkingElForAbsenceWithValue(V key) {
        for (Map.Entry<V, Integer> entry : numbersOfUsesValues.entrySet()) {
            if (key.equals(entry.getKey())) {
                return true;
            }
        }
        return false;
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
        setNumbersOfUsesValues(map.get(key));
        return map.put(key, value);
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
        int maxNumberOfUses = 0;
        K mostPopKey = null;
        for (Map.Entry<K, Integer> entry : numbersOfUsesKeys.entrySet()) {
            if (maxNumberOfUses < entry.getValue()) {
                maxNumberOfUses = entry.getValue();
                mostPopKey = entry.getKey();
            }
        }
        return mostPopKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        for (Map.Entry<K, Integer> entry : numbersOfUsesKeys.entrySet()) {
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int maxNumberOfUses = 0;
        V mostPopValue = null;
        for (Map.Entry<V, Integer> entry : numbersOfUsesValues.entrySet()) {
            if (maxNumberOfUses < entry.getValue()) {
                maxNumberOfUses = entry.getValue();
                mostPopValue = entry.getKey();
            }
        }
        return mostPopValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        for (Map.Entry<V, Integer> entry : numbersOfUsesValues.entrySet()) {
            if (value.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return null;
    }
}
