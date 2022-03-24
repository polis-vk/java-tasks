package ru.mail.polis.homework.objects;

import java.awt.font.LineMetrics;
import java.util.Iterator;
import java.lang.IndexOutOfBoundsException;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node tail;
    private int size = 0;

    public Node getNodeByIndex(int index) {
        Node node = head;
        for (int i = 0; i<index; i++) {
            node = node.next;
        }
        return node;
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
        Node node = new Node(value);
        if (tail == null) {
            head = node;
        } else {
            tail.setNext(node);
        }
        tail = node;
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
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        return getNodeByIndex(index).value;
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
            throw new IndexOutOfBoundsException("Index out of range: " + i);
        }
        if (i == 0) {
            Node node = new Node(value);
            node.setNext(head);
            head = node;
            size++;
            if (tail == null) {
                tail = head;
            }
            return;
        }
        Node node = new Node(value);
        Node currentNode = getNodeByIndex(i - 1);
        node.setNext(currentNode.next);
        currentNode.setNext(node);
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
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        if (index == 0) {
            head = head.next;
        }
        Node currentNode = getNodeByIndex(index - 1);
        currentNode.setNext(currentNode.next.next);
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
        Node bufferNode;
        Node previousNode = null;
        Node currentNode = head;
        while (currentNode != null) {
            bufferNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = bufferNode;
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
        if (head == null) {
            return "null";
        }
        StringBuilder resultList = new StringBuilder();
        Node currentNode = head;
        do {
            resultList.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        } while (currentNode != null);
        resultList.append("null");
        return resultList.toString();
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

            public boolean hasNext() {
                return currentNode != null;
            }

            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int value = currentNode.value;
                currentNode = currentNode.next;
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

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
