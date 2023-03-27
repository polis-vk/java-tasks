package ru.mail.polis.homework.collections;


import ru.mail.polis.homework.collections.structure.ValidatorForParentheses;

import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 *
 * Так же надо сделать итератор (подробности ниже).
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
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private List<Element<K, V>> popularityHistory = new ArrayList<>();

    private Comparator<Element<K, V>> compareByKeys = Comparator.comparing(Element<K, V> :: getKeyPopularity);
    private Comparator<Element<K, V>> compareByValues = Comparator.comparing(Element<K, V> :: getValuePopularity);

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
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
        if( Element.findElementByKey(popularityHistory, key) == null) {
            Element.addElement(popularityHistory, key, null);
        } else {
            Element.findElementByKey(popularityHistory, key).useKey();
        }

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if( Element.findElementByValue(popularityHistory, value) == null) {
            Element.addElement(popularityHistory, null, value);
        } else {
            Element.findElementByValue(popularityHistory, value).useValue();
        }

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if( Element.findElementByKey(popularityHistory, key) == null) {
            Element.addElement(popularityHistory, key, null);
        } else {
            Element.findElementByKey(popularityHistory, key).useKey();
        }

            return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        if( Element.findElementByKey(popularityHistory, key) == null) {
            Element.addElement(popularityHistory, key, value);
        } else {
            Element.findElementByKey(popularityHistory, key).useKey();
        }

        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if( Element.findElementByKey(popularityHistory, key) == null) {
            Element.addElement(popularityHistory, key, null);
        } else {
            Element.findElementByKey(popularityHistory, key).useKey();
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
     */
    public K getPopularKey() {
        popularityHistory.sort(compareByKeys);
        return popularityHistory.get(popularityHistory.size()-1).getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if(Element.findElementByKey(popularityHistory, key) == null) {
            return 0;
        }
        return Element.findElementByKey(popularityHistory, key).getKeyPopularity();
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        popularityHistory.sort(compareByValues);
        return popularityHistory.get(popularityHistory.size()-1).getValue();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if(Element.findElementByValue(popularityHistory, value) == null) {
            return 0;
        }
        return Element.findElementByValue(popularityHistory, value).getValuePopularity();
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        popularityHistory.sort(compareByValues);
        return getPopularValues().listIterator();
    }

    private List<V> getPopularValues() {
        List<V> popularValues = new ArrayList<>();
        for (Element<K, V> element :popularityHistory) {
            popularValues.add(element.getValue());
        }
        return popularValues;
    }

    public List<Element<K, V>> getPopularityHistory() {
        return popularityHistory;
    }

    public Comparator<Element<K, V>> getCompareByKeys() {
        return compareByKeys;
    }

    public Comparator<Element<K, V>> getCompareByValues() {
        return compareByValues;
    }
}
