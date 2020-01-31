package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 *
 * Дополнительное задание описано будет ниже
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private final List<PopularityNode<K>> keyPopularityTable;
    private final List<PopularityNode<V>> valuePopularityTable;

    public PopularMap() {
        this.map = new HashMap<>();
        keyPopularityTable = new LinkedList<>();
        valuePopularityTable = new LinkedList<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keyPopularityTable = new LinkedList<>();
        valuePopularityTable = new LinkedList<>();
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
        incrementKeyPopularity((K)key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        incrementValuePopularity((V)value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        incrementKeyPopularity((K)key);
        if (value != null) {
            incrementValuePopularity(value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        V previous = map.put(key, value);
        if (previous != null) {
            incrementValuePopularity(previous);
        }
        incrementKeyPopularity(key);
        incrementValuePopularity(value);
        return previous;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        incrementKeyPopularity((K)key);
        incrementValuePopularity(value);
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            incrementKeyPopularity(entry.getKey());
            incrementValuePopularity(entry.getValue());
        }
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
        return keyPopularityTable.get(keyPopularityTable.size() - 1).key;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        PopularityNode<K> node = getNodeForKey(key);
        return node != null ? node.popularity : 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return valuePopularityTable.get(valuePopularityTable.size() - 1).key;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        PopularityNode<V> node = getNodeForValue(value);
        return node != null ? node.popularity : 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return new Iterator<V>() {
            Iterator<PopularityNode<V>> iterator = valuePopularityTable.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public V next() {
                return iterator.next().key;
            }
        };
    }

    private PopularityNode<K> getNodeForKey(Object key) {
        for (PopularityNode<K> node : keyPopularityTable) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    private PopularityNode<V> getNodeForValue(Object key) {
        for (PopularityNode<V> node : valuePopularityTable) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    private void incrementKeyPopularity(K key) {
        if (key == null) {
            return;
        }
        PopularityNode<K> node = getNodeForKey(key);
        if (node != null) {
            node.incrementPopularity();
            keyPopularityTable.sort(Comparator.comparingInt(node2 -> node2.popularity));
        } else {
            keyPopularityTable.add(0, new PopularityNode<>(key));
        }
    }

    private void incrementValuePopularity(V value) {
        if (value == null) {
            return;
        }
        PopularityNode<V> node = getNodeForValue(value);
        if (node != null) {
            node.incrementPopularity();
            valuePopularityTable.sort(Comparator.comparingInt(node1 -> node1.popularity));
        } else {
            valuePopularityTable.add(0, new PopularityNode<>(value));
        }
    }

    private class PopularityNode<Key> {
        int popularity;
        Key key;

        PopularityNode(Key key) {
            this.key = key;
            this.popularity = 1;
        }

        void incrementPopularity() {
            popularity++;
        }
    }
}
