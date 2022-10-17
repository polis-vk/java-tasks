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
        if (size == 0) {
            head = new Node(value);
            tail = head;
            size++;
            return;
        }

        Node newNode = new Node(value);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == size - 1) {
            return tail.value;
        }
        Node current = nodeFind(index);

        return current.value;
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
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException(i);
        }

        if (size == 0 || i == size - 1) {
            add(value);
            return;
        }

        if (i == 0) {
            Node current = new Node(value);
            current.next = head;
            head = current;
            size++;
            return;
        }

        Node current = nodeFind(i - 1);
        Node tmp = current.next;
        current.next = new Node(value);
        current.next.next = tmp;
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

        Node current = nodeFind(index - 1);
        current.next = current.next.next;

        if (index == size - 1) {
            tail = current;
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
        if (head.next != null) {
            tail = head;
            Node currentNode = head.next;
            head.next = null;

            while (currentNode != null) {
                Node next = currentNode.next;
                currentNode.next = head;
                head = currentNode;
                currentNode = next;
            }
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
        StringBuilder stringBuilder = new StringBuilder();
        Node currentNode = head;
        while (currentNode != null) {
            stringBuilder.append(currentNode.value)
                    .append(" -> ");
            currentNode = currentNode.next;
        }
        stringBuilder.append("null");

        return stringBuilder.toString();
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
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                int data = currentNode.value;
                currentNode = currentNode.next;
                return data;
            }
        };
    }

    public Node nodeFind(int index) {
        Node current = head;
        for (int j = 0; j < index; j++) {
            current = current.next;
        }
        return current;
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
