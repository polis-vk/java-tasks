package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MultiSet<E> {
    private Map<E, Integer> elementsCounter;

    public MultiSet(List<E> elements) {
        elementsCounter = new HashMap<>();
        for (E e : elements) {
            add(e);
        }
    }

    public void add(E element) {
        if (!elementsCounter.containsKey(element)) {
            elementsCounter.put(element, 1);
        } else {
            elementsCounter.put(element, elementsCounter.get(element) + 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiSet<?> multiSet = (MultiSet<?>) o;
        return Objects.equals(elementsCounter, multiSet.elementsCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementsCounter);
    }
}
