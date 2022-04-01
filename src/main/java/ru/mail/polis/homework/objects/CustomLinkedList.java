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
    private int size = 0;

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
        Node node = new Node(value);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */

    private Node getNode(int index) {
        Node curNode = head;
        for (int i = 0; i != index; i++) {
            curNode = curNode.next;
        }
        return curNode;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        return getNode(index).value;
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
            throw new IndexOutOfBoundsException("index: " + i);
        }
        if (i == size) {
            add(value);
            return;
        }
        size++;
        if (i == 0) {
            Node node = new Node(value);
            node.next = head;
            head = node;
            return;
        }
        Node node = getNode(i - 1);
        Node nextNode = node.next;
        node.next = new Node(value);
        node.next.next = nextNode;
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
            throw new IndexOutOfBoundsException("index: " + index);
        }
        if (head == null) {
            return;
        }
        size--;
        if (index == 0) {
            head = head.next;
            return;
        }
        Node node = getNode(index - 1);
        node.next = node.next.next;
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
        if (head == null) {
            return;
        }
        Node nextNode;
        Node prevNode = head;
        Node curNode = head.next;
        prevNode.next = null;
        while (curNode != null) {
            nextNode = curNode.next;
            curNode.next = prevNode;
            prevNode = curNode;
            curNode = nextNode;
        }
        head = tail;
        tail = getNode(size - 1);
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
        if (head == null) {
            return "null";
        }
        StringBuilder listDescription = new StringBuilder();
        Node curNode = head;
        while (curNode != null) {
            listDescription.append(curNode.value).append(" -> ");
            curNode = curNode.next;
        }
        listDescription.append("null");
        return listDescription.toString();
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
            Node curNode = head;

            @Override
            public boolean hasNext() {
                return curNode != null;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int value = curNode.value;
                curNode = curNode.next;
                return value;
            }
        };
    }

    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
