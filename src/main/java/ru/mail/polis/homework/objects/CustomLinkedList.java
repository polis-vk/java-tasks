package ru.mail.polis.homework.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {
    private Node head;
    private Node tail;
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
        size++;
        if (this.head == null) {
            this.head = new Node(value);
            tail = head;
            return;
        }
        tail.setNext(new Node(value));
        tail = tail.next;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(index);
        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
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
            throw new IndexOutOfBoundsException(i);
        }
        if (i == 0) {
            size++;
            Node firstNode = new Node(value);
            firstNode.setNext(head);
            head = firstNode;
            return;
        }
        if (i == size) {
            this.add(value);
            return;
        }
        Node previousNode = head;
        Node nextNode;
        for (int j = 0; j < i - 1; j++) {
            previousNode = previousNode.next;
        }
        nextNode = previousNode.next;
        previousNode.setNext(new Node(value));
        previousNode.next.setNext(nextNode);
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
        if (index == 0) {
            head = head.next;
            size--;
            return;
        }
        Node previousNode = head;
        for (int i = 0; i < index - 1; i++) {
            previousNode = previousNode.next;
        }
        previousNode.setNext(previousNode.next.next);
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
        Node previousNode = head;
        Node currentNode = previousNode.next;
        Node nextNode;
        previousNode.next = null;
        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }
        head = previousNode;
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
        StringBuilder str = new StringBuilder("");
        if (head == null) {
            return "null";
        }
        Node currentNode = head;
        while (currentNode.next != null) {
            str.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        str.append(currentNode.value).append(" -> null");
        return str.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Integer next() {
                if (currentNode == null) {
                    throw new NoSuchElementException();
                }
                int currentValue = currentNode.value;
                currentNode = currentNode.next;
                return currentValue;
            }
        };
    }

    private static class Node {
        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
