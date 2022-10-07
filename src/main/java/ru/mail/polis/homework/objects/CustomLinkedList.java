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
    private int size;

    private int modCounter;

    public CustomLinkedList() {
        head = null;
        tail = null;
        size = 0;
        modCounter = 0;
    }

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
        if (size == 0) {
            head = new Node(value);
            tail = head;
        } else {
            Node tmp = tail;
            tail = new Node(value);
            tmp.next = tail;
        }
        size++;
        modCounter++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (!checkIndexValidity(index)) {
            throw new IndexOutOfBoundsException();
        }
        Node it = head;
        int i = index;
        while (i-- > 0) {
            it = it.next;
        }
        return it.value;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список на заданную позицию.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(index);
     *
     * @param index - index
     * @param value - data for create Node.
     */
    public void add(int index, int value) {
        if (index == size) {
            add(value);
        } else {
            if (!checkIndexValidity(index)) {
                throw new IndexOutOfBoundsException(index);
            }
            Node beforeHead = new Node(0);
            beforeHead.next = head;
            Node it = findNode(beforeHead, index);
            Node elem = new Node(value);
            elem.next = it.next;
            it.next = elem;
            if (index == 0) {
                head = elem;
            }
            size++;
        }
        modCounter++;
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
        if (!checkIndexValidity(index)) {
            throw new IndexOutOfBoundsException(index);
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node beforeHead = new Node(0);
            beforeHead.next = head;
            Node it = findNode(beforeHead, index);
            it.next = it.next.next;
            if (index == size - 1) {
                tail = it;
            }
        }
        size--;
        modCounter++;
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
        if (size > 1) {
            tail = head;
            Node it1 = head.next;
            Node it2 = it1.next;
            while (it2 != null) {
                it1.next = head;
                head = it1;
                it1 = it2;
                it2 = it2.next;
            }
            it1.next = head;
            head = it1;
            tail.next = null;
        }
        modCounter++;
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
        Node it = head;
        StringBuilder builder = new StringBuilder();
        while (it != null) {
            builder.append(it.value).append(" -> ");
            it = it.next;
        }
        builder.append("null");
        return builder.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Integer> {

        private Node cur;
        private final int fixedModCounter = modCounter;

        public LinkedListIterator() {
            cur = head;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Integer next() {
            if (fixedModCounter != modCounter) {
                throw new ConcurrentModificationException();
            }

            if (cur == null) {
                throw new NoSuchElementException();
            }
            int val = cur.value;
            cur = cur.next;
            return val;
        }
    }

    private Node findNode(Node start, int index) {
        Node it = start;
        int i = index;
        while (i-- > 0) {
            it = it.next;
        }
        return it;
    }

    private boolean checkIndexValidity(int index) {
        return index >= 0 && index < size;
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
