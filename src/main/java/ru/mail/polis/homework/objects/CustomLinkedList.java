package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.lang.IndexOutOfBoundsException;
import java.util.NoSuchElementException;

/**
 * 13 тугриков
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node tail;
    private Node head;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        int sizeOfList = 0;
        Node currentNode = head;
        while (currentNode != null) {
            sizeOfList += 1;
            currentNode = currentNode.next;
        }
        return sizeOfList;
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
            tail = node;
            head = node;
        }
        tail.setNext(node);
        tail = node;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        int currentPosition = 0;
        Node currentNode = head;
        while (true) {
            if (currentPosition == index) {
                return currentNode.value;
            }
            currentPosition += 1;
            currentNode = currentNode.next;
        }
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
            throw new IndexOutOfBoundsException("Index out of range: " + i);
        }
        if (i == 0) {
            Node node = new Node(value);
            node.setNext(head);
            head = node;
        }
        int currentPosition = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (currentPosition == i - 1) {
                Node node = new Node(value);
                node.setNext(currentNode.next);
                currentNode.setNext(node);
                break;
            }
            currentPosition += 1;
            currentNode = currentNode.next;
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
        if (index <= 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        if (index == 0) {
            head = head.next;
        }
        int currentPosition = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (currentPosition == index - 1) {
                currentNode.setNext(currentNode.next.next);
                break;
            }
            currentPosition += 1;
            currentNode = currentNode.next;
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
        Node temp;
        Node prev = null;
        Node currentNode = head;
        while (currentNode != null) {
            temp = currentNode.next;
            currentNode.next = prev;
            prev = currentNode;
            currentNode = temp;
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
        if (head == null) {
            return "null";
        }
        StringBuilder resultList = new StringBuilder();
        Node currentNode = head;
        do {
            resultList.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        } while (currentNode != null);
        return resultList.append("null").toString();
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
            Node prevNode;

            public boolean hasNext() {
                if (currentNode != null) {
                    return true;
                } else {
                    return false;
                }
            }

            public Integer next() {
                if (hasNext()) {
                    prevNode = currentNode;
                    currentNode = prevNode.next;
                    return (int) prevNode.value;
                }
                throw new NoSuchElementException();
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
