package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private int modCount = 0;
    private int size = 0;
    private Node head;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node nextNode = new Node(value);
        if (head != null) {
            Node node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.setNext(nextNode);
        } else {
            head = nextNode;
        }
        size++;
        modCount++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
        Node node = head;
       for (int i = 0; i < index; i++) {
           node = node.next;
       }
       return node.value;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список на заданную позицию.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param i - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) throws IndexOutOfBoundsException {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException(i);
        }
        size++;
        modCount++;
        Node node = head;
        if (i == 0) {
            head = new Node(value);
            head.setNext(node);
            return;
        }
        for (int index = 1; index < i; index++) {
            node = node.next;
        }
        Node nextNode = node.next;
        node.setNext(new Node(value));
        node.next.setNext(nextNode);
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
        size--;
        modCount++;
        Node node = head;
        if (index == 0) {
            head = head.next;
            return;
        }
        for (int i = 1; i < index; i++) {
            node = node.next;
        }
        Node nextNode = node.next.next;
        node.setNext(nextNode);
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        int[] values = new int[size];
        Node node = head;
        for (int i = size - 1; i >= 0; i--) {
            values[i] = node.value;
            node = node.next;
        }
        head = new Node(values[0]);
        node = head;
        for (int i = 1; i < values.length; i++) {
            node.setNext(new Node(values[i]));
            node = node.next;
        }
        modCount++;
    }

    /**
     * 1 тугрик
     * Метод выводит всю последовательность хранящуюся в списке начиная с head.
     * Формат вывода:
     *  - значение каждой Node должно разделяться " -> "
     *  - последовательность всегда заканчивается на null
     *  - если в списке нет элементов - верните строку "null"
     *
     * @return - String with description all list
     */
    @Override
    public String toString() {
        Node node = head;
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            sb.append(node.value).append(" -> ");
            node = node.next;
        }
        return sb.append("null").toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iter();
    }

    private static class Node {

        private int value;
        private Node next;
        public Node(int value) {
            this.value = value;
        }

        public void setNext(Node next) {
            this.next = next;
        }

    }

    private class Iter implements Iterator<Integer> {

        int modCount = CustomLinkedList.this.modCount;
        Node currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Integer next() {
            if (modCount != CustomLinkedList.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int value = currentNode.value;
            currentNode = currentNode.next;
            return value;
        }
    }
}
