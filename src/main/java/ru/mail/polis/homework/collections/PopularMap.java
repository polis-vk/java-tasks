package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 * <p>
 * Дополнительное задание описано будет ниже
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private class ValueAndPopullarityPair {
        private V value;
        private int keyPopularity;
        private int valuePopularity;

        public ValueAndPopullarityPair() {
            this.keyPopularity = 0;
            this.valuePopularity = 0;
        }

        public ValueAndPopullarityPair(V value) {
            this.keyPopularity = 0;
            this.valuePopularity = 0;
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public int getKeyPopularity() {
            return keyPopularity;
        }

        public void increaseKeyPopularity() {
            this.keyPopularity++;
        }

        public int getValuePopularity() {
            return valuePopularity;
        }

        public void increaseValuePopularity() {
            this.valuePopularity++;
        }

        public void resetValuePopularity() {
            this.valuePopularity = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ValueAndPopullarityPair pair = (ValueAndPopullarityPair) o;
            return keyPopularity == pair.keyPopularity &&
                    valuePopularity == pair.valuePopularity &&
                    Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, keyPopularity, valuePopularity);
        }

    }

    private final Map<K, ValueAndPopullarityPair> map;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = new HashMap<>();
        map.entrySet()
                .stream()
                .forEach(kvEntry -> {
                    this.map.put(kvEntry.getKey(), new ValueAndPopullarityPair(kvEntry.getValue()));
                });
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        ValueAndPopullarityPair pair = map.get(key);
        if (pair.getValue() != null) {
            pair.increaseKeyPopularity();
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map.Entry<K, ValueAndPopullarityPair> entry : map.entrySet()) {
            if (entry.getValue().getValue() != null && entry.getValue().getValue().equals(value)) {
                entry.getValue().increaseValuePopularity();
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        ValueAndPopullarityPair pair = map.get(key);
        if (pair != null && pair.getValue() != null) {
            pair.increaseKeyPopularity();
            pair.increaseValuePopularity();
        } else {
            put((K) key, null);
        }
        return (pair == null) ? null : pair.getValue();
    }

    @Override
    public V put(K key, V value) {
        ValueAndPopullarityPair pair = map.get(key);
        if (pair != null) {
            pair.increaseKeyPopularity();
            if (pair.getValue() != null && pair.getValue().equals(value)) {
                pair.increaseValuePopularity();
            } else {
                pair.resetValuePopularity();
            }
            pair.setValue(value);
            return pair.getValue();
        } else {
            pair = new ValueAndPopullarityPair(value);
            pair.increaseKeyPopularity();
            pair.increaseValuePopularity();
            map.put(key, pair);
            return pair.getValue();
        }
    }

    @Override
    public V remove(Object key) {
        ValueAndPopullarityPair pair = map.get(key);
        if (pair != null && pair.getValue() != null) {
            V value = pair.getValue();
            pair.increaseKeyPopularity();
            pair.increaseValuePopularity();
            pair.setValue(null);
            return value;
        } else {
            put((K) key, null);
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.entrySet().stream().forEach(entry -> {
            ValueAndPopullarityPair pair = map.get(entry.getKey());
            if (pair.getValue() != null) {
                if(pair.getValue().equals(entry.getValue())) {
                    pair.increaseValuePopularity();
                } else {
                    pair.resetValuePopularity();
                }
                pair.increaseKeyPopularity();
                pair.setValue(entry.getValue());
            } else {
                pair = new ValueAndPopullarityPair(entry.getValue());
                pair.increaseKeyPopularity();
                pair.increaseValuePopularity();
                map.put(entry.getKey(), pair);
            }
        });
    }

    @Override
    public void clear() {
        map.entrySet().stream().forEach(entry -> {
            entry.getValue().increaseKeyPopularity();
            entry.setValue(null);
        });
    }

    @Override
    public Set<K> keySet() {
        return map
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getValue() != null)
                .map(Entry::getKey
                )
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return map
                .values()
                .stream()
                .filter(value -> value.getValue() != null)
                .map(value -> value.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Map<K, V> resMap = new HashMap<>();
        for (Map.Entry<K, ValueAndPopullarityPair> entry : map.entrySet()) {
            resMap.put(entry.getKey(), entry.getValue().getValue());
        }
        ;
        return resMap.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        int max = -1;
        K key = null;
        for (Map.Entry<K, ValueAndPopullarityPair> pair : map.entrySet()) {
            if(pair.getValue().getKeyPopularity() > max)  {
                max = pair.getValue().getKeyPopularity();
                key = pair.getKey();
            }
        }
        return key;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return map.get(key) == null ? 0 : map.get(key).getKeyPopularity();
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int max = -1;
        V value = null;
        for (Map.Entry<K, ValueAndPopullarityPair> pair : map.entrySet()) {
            if(pair.getValue().getValuePopularity() > max)  {
                max = pair.getValue().getValuePopularity();
                value = pair.getValue().getValue();
            }
        }
        return value;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        int res = 0;
        for (ValueAndPopullarityPair pair : map.values()) {
            if (value.equals(pair.getValue())) {
                res += pair.getValuePopularity();
            }
        }
        return res;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return null;
    }
}
