package ru.mail.polis.homework.collections;

import java.util.Comparator;
import java.util.Map;

class ValueComparator implements Comparator {
    private Map<Object, Integer> map;

    public ValueComparator(Map<Object, Integer> map) {
        this.map = map;
    }

    public int compare(Object m1, Object m2) { //элементы мапы сравниваются по значению
        if (map.get(m1) >= map.get(m2)) {
            return 1;
        } else {
            return -1;
        }
    }
}
