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
    private int modCount = 0;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        int size = 0;
        Node bufferNode = head;
        if (bufferNode != null) {
            size++;
            while (bufferNode.next != null) {
                bufferNode = bufferNode.next;
                size++;
            }
        }
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
        Node newNode = new Node(value);
        newNode.next = null;
        if (head == null) {
            head = newNode;
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
        modCount++;
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
        Node last = head;
        int result = last.value;
        for (int i = 0; i < index; i++) {
            last = last.next;
            result = last.value;
        }
        return result;
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
        } else if (i == 1) {
            buffer = last.next;
            last.next = newNode;
            newNode.next = buffer;
        } else {
            for (int j = 0; j < i; j++) {
                last = last.next;
                if (j == i - 2) {
                    buffer = last.next;
                    last.next = newNode;
                    newNode.next = buffer;
                }
            }
            modCount++;
        }
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
        } else if (index == 1) {
            head.next = head.next.next;
        } else {
            int counter = 0;
            last = last.next;
            while (last != null) {
                if (counter == index - 2) {
                    last.next = last.next.next;
                }
                counter++;
                last = last.next;
            }
        }
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
        while (buffer != null) {
            Node nextElement = buffer.next;
            buffer.next = last;
            last = buffer;
            buffer = nextElement;
            head = last;
        }
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
        String fullList = "";
        while (last != null) {
            fullList = fullList.concat(last.value + " -> ");
            last = last.next;
        }
        fullList = fullList.concat("null");
        return fullList;
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
