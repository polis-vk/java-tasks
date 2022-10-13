package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node tail;
    private int modCount = 0;
    private int size = 0;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
        modCount++;
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException(index);
        }
        if (index == size - 1) {
            return tail.value;
        }
        Node last = head;
        for (int i = 0; i < index; i++) {
            last = last.next;
        }
        return last.value;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список на заданную позицию.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param i     - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) {
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException(i);
        }
        Node newNode = new Node(value);
        Node buffer;
        Node last = head;
        if (i == 0) {
            buffer = head;
            head = newNode;
            head.next = buffer;
        } else {
            for (int j = 1; j <= i; j++) {
                if (j == i) {
                    buffer = last.next;
                    last.next = newNode;
                    newNode.next = buffer;
                    if (size == i) {
                        tail = tail.next;
                    }
                }
                last = last.next;
            }
        }
        modCount++;
        size++;
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
    public void removeElement(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index);
        }
        Node last = head;
        if (index == 0) {
            head = head.next;
            tail = head;
        } else {
            for (int j = 1; j <= index; j++) {
                if (j == index && size() - 1 > index) {
                    last.next = last.next.next;
                }
                last = last.next;
                if (index - 1 == j && size() - 1 == index) {
                    last.next = null;
                    tail = last;
                }
            }
        }
        modCount++;
        size--;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node last = null;
        Node buffer = head;
        Node newHead = head;
        while (buffer != null) {
            Node nextElement = buffer.next;
            buffer.next = last;
            last = buffer;
            buffer = nextElement;
            head = last;
        }
        tail = newHead;
        tail.next = null;
        modCount++;
    }

    /**
     * 1 тугрик
     * Метод выводит всю последовательность хранящуюся в списке начиная с head.
     * Формат вывода:
     * - значение каждой Node должно разделяться " -> "
     * - последовательность всегда заканчивается на null
     * - если в списке нет элементов - верните строку "null"
     *
     * @return - String with description all list
     */
    @Override
    public String toString() {
        Node last = head;
        StringBuilder fullList = new StringBuilder();
        while (last != null) {
            fullList = fullList.append(last.value + " -> ");
            last = last.next;
        }
        fullList = fullList.append("null");
        return fullList.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        int fixModCount = modCount;
        Iterator<Integer> iterator = new Iterator<Integer>() {
            Node node = head;

            @Override
            public boolean hasNext() {
                if (node == null) {
                    return false;
                }
                return true;
            }

            @Override
            public Integer next() {
                if (fixModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (hasNext()) {
                    int next = node.value;
                    node = node.next;
                    return next;
                }
                throw new NoSuchElementException();
            }
        };
        return iterator;
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
}
