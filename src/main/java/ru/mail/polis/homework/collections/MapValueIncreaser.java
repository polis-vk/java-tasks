package ru.mail.polis.homework.collections;

import java.util.Map;

public class MapValueIncreaser<T> {
    public Pair<T, Integer> mapIncrease(T key, Map<T, Integer> map, Pair<T, Integer> mostPopular) {
        if (map.containsKey(key)) {
            map.replace(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
        if (mostPopular == null || map.get(key) > mostPopular.getSecond().orElse(0)) {
            mostPopular = new Pair<>(key, map.get(key));
        }
        return mostPopular;
    }
}
