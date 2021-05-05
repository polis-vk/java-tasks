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

    private final Map<K, Integer> keyPopularity;

    private final Map<V, Integer> valuePopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularity = new HashMap<K, Integer>();
        this.valuePopularity = new HashMap<V, Integer>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularity = new HashMap<K, Integer>();
        this.valuePopularity = new HashMap<V, Integer>();
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
        upgradeKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        upgradeValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        upgradeKeyPopularity((K) key);
        if(map.containsKey(key))
        {
           upgradeValuePopularity(map.get(key));
        }
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        upgradeKeyPopularity(key);
        upgradeValuePopularity(value);
        V val = map.put(key, value);
        if(val != null) {
            upgradeValuePopularity(val);
        }
        return val;
    }

    @Override
    public V remove(Object key) {
        upgradeKeyPopularity((K) key);
        if(map.containsKey((K) key)){
            upgradeValuePopularity(map.get(key));
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
        int maxValue = -1;
        K key = null;
        for (Map.Entry<K, Integer> entry : keyPopularity.entrySet()) {
            if (maxValue < entry.getValue())
            {
                key = entry.getKey();
                maxValue = entry.getValue();
            }
        }
        return key;
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        if(keyPopularity.containsKey(key)) {
            return keyPopularity.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        int maxValue = -1;
        V value = null;
        for (Map.Entry<V, Integer> entry : valuePopularity.entrySet()) {
            if (maxValue < entry.getValue())
            {
                value = entry.getKey();
                maxValue = entry.getValue();
            }
        }
        return value;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        if (valuePopularity.containsKey(value)){
            return valuePopularity.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(valuePopularity.keySet());
        values.sort(Comparator.comparing(valuePopularity::get));
        return values.iterator();
    }

    private void upgradeKeyPopularity(K key)
    {
        if (keyPopularity.containsKey(key)) {
            keyPopularity.replace(key, keyPopularity.get(key) + 1);
        }
        else {
            keyPopularity.put(key, 1);
        }
    }

    private void upgradeValuePopularity(V value)
    {
        if (valuePopularity.containsKey(value)) {
            valuePopularity.replace(value, valuePopularity.get(value) + 1);
        } else {
            valuePopularity.put(value, 1);
        }
    }
}
