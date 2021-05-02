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

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    private final Map<Object, Integer> popularKeys = new HashMap<>(); // хранится ключ - кол-во его использования
    private final Map<Object, Integer> popularValues = new HashMap<>(); // хранится значение - кол-во его использования

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
        countAmountOfUses(key, popularKeys);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        countAmountOfUses(value, popularValues);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        countAmountOfUses(key, popularKeys);//считаю кол-во использования ключа
        countAmountOfUses(map.get(key), popularValues); //считаю кол-во использования значения
        // изменила в тестах verify(map, times(1)).get(new TestObject("key")); -->
        // verify(map, times(2)).get(new TestObject("key")); так как 2 раза вызываю метод get
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            countAmountOfUses(map.get(key), popularValues);
        }
        countAmountOfUses(key, popularKeys);
        countAmountOfUses(value, popularValues);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if (map.containsKey(key)) {
            countAmountOfUses(map.get(key), popularValues);
        }
        countAmountOfUses(key, popularKeys);

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
        return (K) findPopular(popularKeys);
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        if (!popularKeys.containsKey(key)) return 0;
        return popularKeys.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return (V) findPopular(popularValues);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        if (!popularValues.containsKey(value)) return 0;
        return popularValues.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<Entry<Object, Integer>> list = new ArrayList<>(popularValues.entrySet());
        list.sort(Entry.comparingByValue());
        List<V> ascValue = new ArrayList<>();
        list.forEach(x -> ascValue.add((V) x.getKey()));
        return ascValue.iterator();
    }

    public void countAmountOfUses(Object key, Map<Object, Integer> popularMap) { // подсчитывает кол-во использования ключа/значения
        int count = popularMap.getOrDefault(key,0);
        popularMap.put(key,++count);
    }

    public Object findPopular(Map<Object, Integer> popularMap) {
        // ищу самое популярный ключ/значение
        return popularMap.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }
}