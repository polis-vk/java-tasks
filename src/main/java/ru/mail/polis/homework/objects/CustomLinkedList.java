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
        Node nodeToAdd = new Node(value);
        if (head == null) {
            head = nodeToAdd;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.setNext(nodeToAdd);
        }
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        return getNode(index).value;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список на заданную позицию.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param index - index
     * @param value - data for create Node.
     */
    public void add(int index, int value) {
        Node nodeToAdd = new Node(value);
        if (index == 0) {
            nodeToAdd.next = head;
            head = nodeToAdd;
        } else {
            Node previous = getNode(index - 1);
            Node current = previous.next;
            previous.next = nodeToAdd;
            nodeToAdd.next = current;
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
        if (index == 0) {
            checkNodeAndIndex(head, index);
            head = head.next;
        } else {
            Node previous = getNode(index - 1);
            Node nodeToRemove = previous.next;
            checkNodeAndIndex(nodeToRemove, index);
            previous.setNext(nodeToRemove.next);
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
        Node previous = null;
        Node current = head;
        while (current != null) {
            Node next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        head = previous;
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
        String separator = " -> ";
        String nullWord = "null";
        Node current = head;
        StringBuilder result = new StringBuilder();
        while (current != null) {
            result.append(current.value);
            result.append(separator);
            current = current.next;
        }
        result.append(nullWord);
        return result.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator() {
            private final int expectedSize = size;
            private Node current = head;

            @Override
            public boolean hasNext() {
                checkForConcurrentModification();
                return current != null;
            }

            @Override
            public Integer next() {
                checkForConcurrentModification();
                if (current == null) {
                    throw new NoSuchElementException();
                }
                int value = current.value;
                current = current.next;
                return value;
            }

            private void checkForConcurrentModification() {
                if (size != expectedSize) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    private Node getNode(int index) {
        checkNodeAndIndex(head, index);
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
            checkNodeAndIndex(current, index);
        }
        return current;
    }

    private void checkNodeAndIndex(Node nodeToCheck, int index) {
        if (nodeToCheck == null || index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
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
