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

    private final Map<K, Integer> keysPopularity;
    private final Map<V, Integer> valuesPopularity;

    private K popularKey = null;
    private int popularKeyCount = 0;
    private V popularValue = null;
    private int popularValueCount = 0;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
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
        keysPopularity.put((K) key, keysPopularity.getOrDefault((K) key, 0) + 1);
        definePopularKey();
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        valuesPopularity.put((V) value, valuesPopularity.getOrDefault((V) value, 0) + 1);
        definePopularValue();
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        keysPopularity.put((K) key, keysPopularity.getOrDefault((K) key, 0) + 1);
        V value = map.get(key);
        if (value != null) {
            valuesPopularity.put(value, valuesPopularity.getOrDefault(value, 0) + 1);
        }
        definePopularKey();
        definePopularValue();
        return value;
    }

    @Override
    public V put(K key, V value) {
        keysPopularity.put(key, keysPopularity.getOrDefault(key, 0) + 1);
        valuesPopularity.put(value, valuesPopularity.getOrDefault(value, 0) + 1);
        V temp = map.put(key, value);
        if (temp != null) {
            valuesPopularity.put(temp, valuesPopularity.getOrDefault(temp, 0) + 1);
        }
        definePopularKey();
        definePopularValue();
        return temp;
    }

    @Override
    public V remove(Object key) {
        keysPopularity.put((K) key, keysPopularity.getOrDefault((K) key, 0) + 1);
        V value = map.remove(key);
        if (value != null) {
            valuesPopularity.put(value, valuesPopularity.getOrDefault(value, 0) + 1);
        }
        definePopularKey();
        definePopularValue();
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

    /**
     * Возвращает самый популярный, на данный момент, ключ
     * 1 балл
     */
    public K getPopularKey() {
        return popularKey;
    }

    private void definePopularKey() {
        keysPopularity.forEach((key, count) -> {
            if (count > popularKeyCount) {
                popularKeyCount = count;
                popularKey = key;
            }
        });
//        for (Entry<K, Integer> entry : keysPopularity.entrySet()) {
//            if(entry.getValue() > popularKeyCount){
//                popularKeyCount = entry.getValue();
//                popularKey = entry.getKey();
//            }
//        }
    }

    private void definePopularValue() {
        valuesPopularity.forEach((value, count) -> {
            if (count > popularValueCount) {
                popularValueCount = count;
                popularValue = value;
            }
        });
    }

    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return keysPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> listOfValues = new ArrayList<>(valuesPopularity.keySet());
        listOfValues.sort(Comparator.comparing(valuesPopularity::get));
        return listOfValues.iterator();
    }
}
