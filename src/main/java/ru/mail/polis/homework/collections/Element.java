package ru.mail.polis.homework.collections;

import java.util.List;

public class Element<K, V> {
    private K key;
    private int keyPopularity;
    private V value;
    private int valuePopularity;

    Element(K key, V value) {
        this.key = key;
        keyPopularity = 1;

        this.value = value;
        valuePopularity = 1;
    }

    public static <K, V> Element findElementByKey(List<Element<K, V>> elementSet, Object key) {
        if (elementSet != null) {
            for (Element<K, V> element : elementSet) {
                if (element.getKey().equals(key)) {
                    return element;
                }
            }
        }
        return null;
    }

    public static <K, V> Element findElementByValue(List<Element<K, V>> elementSet, Object value) {
        if (elementSet != null) {
            for (Element<K, V> element : elementSet) {
                if (element.getValue() != null && element.getValue().equals(value)) {
                    return element;
                }
            }
        }
        return null;
    }

    public static <K, V> void addElement(List<Element<K, V>> elementSet, Object key, Object value) {
        elementSet.add((Element<K, V>) new Element<>(key, value));
    }

    public void useKey() {
        if (this != null) {
            keyPopularity++;
        }
    }

    public void useValue() {
        if (this != null) {
            valuePopularity++;
        }
    }

    public int getKeyPopularity() {
        return keyPopularity;
    }

    public int getValuePopularity() {
        return valuePopularity;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "\n" + "Key: " + key.toString() + " with popularity " + keyPopularity + " | " + "Value: " + value.toString() + " with popularity " + valuePopularity;
    }

    public void setValue(V value) {
        this.value = value;
        valuePopularity = 0;
    }
}
