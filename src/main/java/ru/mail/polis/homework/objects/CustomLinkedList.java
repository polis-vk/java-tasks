package ru.mail.polis.homework.objects;

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
        size++;

        if (head == null) {
            head = new Node(value);
            head.setNext(null);
            tail = head;
            return;
        }

        Node temp = new Node(value);
        tail.setNext(temp);
        tail = temp;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {

        if (head == null || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size - 1) {
            tail.getValue();
        }

        Node cur = head;
        int count = 0;

        while (cur != null) {
            if (count == index) {
                return cur.getValue();
            }
            count++;
            cur = cur.getNext();
        }

        return -1;
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

        if (i > size || i < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (head == null && i == 0) {
            add(value);
            return;
        } else if (head != null && i == 0) {
            Node newNode = new Node(value);
            newNode.setNext(head);
            head = newNode;
            size++;
            return;
        } else if (head == null && i != 0) {
            return;
        }

        Node cur = head;
        int count = 0;

        while (cur != null) {
            if (count == i - 1) {
                break;
            }
            count++;
            cur = cur.getNext();
        }

        Node newNode = new Node(value);
        Node temp = cur.getNext();

        cur.setNext(newNode);
        newNode.setNext(temp);
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0 && size == 1) {
            size--;
            head = tail = null;
            return;
        } else if (index == 0) {
            size--;
            head = head.getNext();
            return;
        }

        Node cur = head;
        int count = 0;

        while (cur != null) {
            if (count == index - 1) {
                break;
            }
            count++;
            cur = cur.getNext();
        }

        Node target = cur.getNext();
        cur.setNext(target.getNext());

        if (index == size - 1) {
            tail = cur;
        }

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

        Node current = head;
        Node next = null;
        Node prev = null;
        tail = head;

        while (current != null) {
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }

        head = prev;
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

        StringBuilder ret = new StringBuilder();
        Node cur = head;

        while (cur != null) {
            ret.append(cur.getValue());
            ret.append(" -> ");
            cur = cur.getNext();
        }

        ret.append("null");
        return ret.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {

        Iterator<Integer> it = new Iterator<Integer>() {

            private int currentIndex = 0;
            Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {

                if (current == null) {
                    throw new NoSuchElementException();
                }

                Integer ret = current.value;
                current = current.getNext();
                return ret;
            }
        };

        return it;
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

        public Node getNext() {
            return next;
        }

        public int getValue() {
            return value;
        }
    }
}
