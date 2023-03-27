package ru.mail.polis.homework.collections;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Element<K,V> {
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

    public static <K, V>Element findElementByKey(List<Element<K, V>> elementSet, Object key) {
        if(elementSet != null) {
            for (Element<K, V> element : elementSet) {
                if (element.getKey().equals(key)) {
                    return element;
                }
            }
        }
        return null;
    }

    public static <K, V> Element findElementByValue(List<Element<K, V>> elementSet, Object value) {
        if(elementSet != null) {
            for (Element<K, V> element : elementSet) {
                if (element.getValue() != null && element.getValue().equals(value)) {
                    return element;
                }
            }
        }
        return null;
    }

    public static <K, V> void useElements(List<Element<K, V>> elementSet, K key, V value) {
        for (Element<K, V> element: elementSet) {
            if(element.getKey().equals(key) && element.getValue().equals(value)) {
                element.useKey();
                element.useValue();
                return;
            }
        }
    }

    public static <K, V> void addElement(List<Element<K, V>> elementSet, Object key, Object value) {
            elementSet.add((Element<K, V>) new Element<>(key, value));
//
//        Element<K, V> newElement = (Element<K, V>) new Element<>(key, value);
//        for (Element<K, V> element : elementSet) {
//            if(element.getKey().equals(newElement.getKey())) {
//                element.useValue();
//                if(element.getValue() == null) {
//                    element.setValue((V) value);
//                }
//                return;
//            }
//        }
//        elementSet.add(newElement);
    }

//    public static <K, V> Element<K, V> findElement(List<Element<K, V>> elementSet, Object key, Object value) {
//        Element<K, V> newElement = (Element<K, V>) new Element<>(key, value);
//        for (Element<K, V> element : elementSet) {
//            if(element.getKey().equals(newElement.getKey())) {
//                return element;
//            }
//        }
//        return null;
//    }

    public void useKey() {
        if(this != null) {
            keyPopularity++;
        }
    }

    public void useValue() {
        if(this != null) {
            valuePopularity++;
        }
    }

//    @Override
//    public int compareTo(Element<K, V> element) {
//        if(this.getValuePopularity() < element.getValuePopularity()) {
//            return -1;
//        } else {
//            if (this.getValuePopularity() > element.getValuePopularity()) {
//                return 1;
//            }
//        }
//        return 0;
//    }

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
    }
}
