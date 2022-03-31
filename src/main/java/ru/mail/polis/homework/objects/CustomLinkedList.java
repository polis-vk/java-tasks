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
        if (head == null) {
            head = new Node(value);
            tail = head;
            size++;
            return;
        }
        Node newNode = new Node(value);
        tail.setNext(newNode);
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
            throw new IndexOutOfBoundsException("Переданный индекс не валиден: " + i);
        }
        if (i == size - 1) {
            add(value);
            size++;
            return;
        }
        Node newNode = new Node(value);
        if (i == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node parent = getNodeByIndex(i - 1);
            newNode.setNext(parent.next);
            parent.setNext(newNode);
        }
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
            throw new IndexOutOfBoundsException("Переданный индекс не валиден: " + index);
        }
        if (index == 0) {
            head = head.next;
            size--;
            return;
        }
        Node prevNode = getNodeByIndex(index - 1);
        if (index == size - 1) {
            prevNode = tail;
            size--;
            return;
        }
        prevNode.next = prevNode.next.next;
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
        Node currentNode = head.next;
        int currentIndex = 1;
        while (currentNode != null) {
            removeElement(currentIndex);
            currentIndex++;
            add(0, currentNode.value);
            currentNode = currentNode.next;
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
        if (head == null) {
            return "null";
        }
        Node currentNode = head;
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < size; i++) {
            resultString.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        return resultString.append("null").toString();
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
                    throw new NoSuchElementException("Невозможно найти этот элемент");
                }
                int value = currentNode.value;
                currentNode = currentNode.next;
                return value;
            }
        };
    }

    private Node getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Переданный индекс не валиден: " + index);
        }
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
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
