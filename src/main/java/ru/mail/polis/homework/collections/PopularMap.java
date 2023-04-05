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
    private final HashMap<K, Integer> keys;
    private final HashMap<V, Integer> values;

    private final Container<K> popularKey;
    private final Container<V> popularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keys = new HashMap<>();
        this.values = new HashMap<>();
        this.popularValue = new Container<>();
        this.popularKey = new Container<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keys = new HashMap<>();
        this.values = new HashMap<>();
        this.popularValue = new Container<>();
        this.popularKey = new Container<>();
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
        addObject(key, (Map<Object, Integer>) keys, (Container<Object>) popularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addObject(value, (Map<Object, Integer>) values, (Container<Object>) popularValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addObject(key, (Map<Object, Integer>) keys, (Container<Object>) popularKey);
        V res = map.get(key);
        addObject(res, (Map<Object, Integer>) values, (Container<Object>) popularValue);
        return res;
    }

    @Override
    public V put(K key, V value) {
        addObject(key, (Map<Object, Integer>) keys, (Container<Object>) popularKey);
        addObject(value, (Map<Object, Integer>) values, (Container<Object>) popularValue);
        V res = map.put(key, value);
        if (res != null) {
            addObject(res, (Map<Object, Integer>) values, (Container<Object>) popularValue);
        }
        return res;
    }

    @Override
    public V remove(Object key) {
        addObject(key, (Map<Object, Integer>) keys, (Container<Object>) popularKey);
        V res = map.remove(key);
        if (res != null) {
            addObject(res, (Map<Object, Integer>) values, (Container<Object>) popularValue);
        }
        return res;
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
        return popularKey.get();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (keys.get(key) == null) {
            return 0;
        }
        return keys.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue.get();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (values.get(value) == null) {
            return 0;
        }
        return values.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return values.keySet()
                .stream()
                .sorted(Comparator.comparingInt(this::getValuePopularity))
                .iterator();
    }

    private void addObject(Object obj, Map<Object, Integer> map, Container<Object> popularObj) {
        map.put(obj, map.containsKey(obj) ? map.get(obj) + 1 : 1);
        if (popularObj.get() == null || map.get(obj) >= map.get(popularObj.get())) {
            popularObj.set(obj);
        }
    }
}



