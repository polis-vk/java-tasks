package ru.mail.polis.homework.collections;

import java.util.Map;

public final class MapValueIncreaser {
    public static <T> Pair<T, Integer> mapIncrease(T key, Map<T, Integer> map, Pair<T, Integer> mostPopular) {
        map.compute(key, (k, v) -> (v == null) ? 1 : v + 1);

        int tempValue = map.get(key);

        if (mostPopular == null || tempValue > mostPopular.getSecond().orElse(0)) {
            mostPopular = new Pair<>(key, tempValue);
        }
        return mostPopular;
    }
}
