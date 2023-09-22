
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
    private Map<K,Integer> popularityKeyMap = new HashMap<>();
    private Map<V,Integer> popularityValueMap = new HashMap<>();
    private K mostPopularKey;
    private V mostPopularValue;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    public <T> T increase(Map<T, Integer> Map, T increaseObject, T mostPopularObject)
    {
        if(increaseObject != null
                && Map.merge(increaseObject, 1, Integer::sum) > Map.getOrDefault(mostPopularObject,0))
            mostPopularObject = increaseObject;
        return mostPopularObject;
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
        mostPopularKey = increase(popularityKeyMap, (K)key, mostPopularKey );
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        mostPopularValue = increase(popularityValueMap, (V)value, mostPopularValue );
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        mostPopularKey = increase(popularityKeyMap, (K)key, mostPopularKey );
        mostPopularValue = increase(popularityValueMap, value, mostPopularValue);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V temp = map.put(key,value);
        mostPopularKey = increase(popularityKeyMap, (K)key, mostPopularKey );
        mostPopularValue = increase(popularityValueMap, value, mostPopularValue);
        mostPopularValue = increase(popularityValueMap, temp, mostPopularValue);
        return temp;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        mostPopularKey = increase(popularityKeyMap, (K)key, mostPopularKey );
        mostPopularValue = increase(popularityValueMap, value, mostPopularValue);
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
     */
    public K getPopularKey() {
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularityKeyMap.getOrDefault(key,0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularityValueMap.getOrDefault(value,0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return popularityValueMap.entrySet()
                .stream().sorted(Comparator.comparingInt(Entry::getValue))
                .map(Entry::getKey).iterator();
    }
}