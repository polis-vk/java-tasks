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
 * Всего 9 баллов (2 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private Map<K, Integer> keysPopularity;
    private Map<V, Integer> valuesPopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();
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
        if (map.containsKey(key)) {
            keysPopularity.put((K) key, keysPopularity.get(key) + 1);
            return true;
        }
        keysPopularity.put((K) key, 1);
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if (map.containsValue(value)) {
            valuesPopularity.put((V) value, valuesPopularity.get(value) + 1);
            return true;
        }
        valuesPopularity.put((V) value, 1);
        return false;
    }

    @Override
    public V get(Object key) {
        UpgradeKeyPopularity((K) key);
        if (map.containsKey(key)) {
            UpgradeValuePopularity(map.get(key));
        }
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        UpgradeKeyPopularity(key);
        if (map.containsKey(key)) {
            UpgradeValuePopularity(map.get(key));
            UpgradeValuePopularity(value);
        } else {
            UpgradeValuePopularity(value);
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        UpgradeKeyPopularity((K) key);
        if (map.containsKey(key)) {
            UpgradeValuePopularity(map.get(key));
        }
        return map.remove(key);
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
        K popularKey = null;
        int maxPopularity = 0;
        for (Entry<K, Integer> entry : keysPopularity.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                popularKey = entry.getKey();
                maxPopularity = entry.getValue();
            }
        }
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        if (!keysPopularity.containsKey(key)) {
            return 0;
        }

        return keysPopularity.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        V popularValue = null;
        int maxPopularity = 0;
        for (Entry<V, Integer> entry : valuesPopularity.entrySet()) {
            if (entry.getValue() >= maxPopularity) {
                popularValue = entry.getKey();
                maxPopularity = entry.getValue();
            }
        }
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        if (!valuesPopularity.containsKey(value)) {
            return 0;
        }

        return valuesPopularity.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> sortedValuesPopularity = new ArrayList(valuesPopularity.keySet());
        Collections.sort(sortedValuesPopularity, Comparator.comparing(valuesPopularity::get));
        return sortedValuesPopularity.iterator();
    }

    public void UpgradeKeyPopularity(K key) {
        if (keysPopularity.containsKey(key)) {
            keysPopularity.put(key, keysPopularity.get(key) + 1);
        } else {
            keysPopularity.put(key, 1);
        }
    }

    public void UpgradeValuePopularity(V key) {
        if (valuesPopularity.containsKey(key)) {
            valuesPopularity.put(key, valuesPopularity.get(key) + 1);
        } else {
            valuesPopularity.put(key, 1);
        }
    }
}
