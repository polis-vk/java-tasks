
import java.util.*;
import java.util.stream.Collectors;


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
    private final Map<K, Integer> keyMapPopularity;
    private final Map<V, Integer> valueMapPopularity;

    public PopularMap(){
        this.map = new HashMap<>();
        keyMapPopularity = new HashMap<>();
        valueMapPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map){
        this.map = map;
        keyMapPopularity = new HashMap<>();
        valueMapPopularity = new HashMap<>();
    }

    private void updateKey(K key){
        if (keyMapPopularity.containsKey(key)) {
            keyMapPopularity.put(key, keyMapPopularity.get(key) + 1);
        } else {
            keyMapPopularity.put(key, 1);
        }
    }

    private void updateValue(V value){
        if (valueMapPopularity.containsKey(value)) {
            valueMapPopularity.put(value, valueMapPopularity.get(value) + 1);
        } else {
            valueMapPopularity.put(value, 1);
        }
    }

    @Override
    public int size(){
        return map.size();
    }

    @Override
    public boolean isEmpty(){
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key){
        updateKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value){
        updateValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key){
        updateKey((K) key);
        V tmpV = map.get(key);
        updateValue(tmpV);
        return tmpV;
    }

    @Override
    public V put(K key, V value){
        updateKey(key);
        V tmpV = map.put(key, value);
        if (tmpV != null) {
            updateValue(tmpV);
        }
        updateValue(value);
        return tmpV;
    }

    @Override
    public V remove(Object key){
        updateKey((K) key);
        V removed = map.remove(key);
        if (removed != null) {
            updateValue(removed);
        }
        return removed;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m){
        map.putAll(m);
    }

    @Override
    public void clear(){
        map.clear();
    }

    @Override
    public Set<K> keySet(){
        return map.keySet();
    }

    @Override
    public Collection<V> values(){
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet(){
        return map.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey(){
        return keyMapPopularity.entrySet()
                .stream()
                .max(Comparator.comparing(Entry::getValue))
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key){
        return keyMapPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue(){
        return valueMapPopularity
                .entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value){
        return valueMapPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator(){
        return valueMapPopularity
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }
}