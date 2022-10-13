package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;

    private int currentSize;

    public CustomLinkedList() {
        head = null;
    }

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        return currentSize;
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
            head = new Node(value, null);
            currentSize++;
        } else {
            Node tempNode = head;
            Node newNode = new Node(value, null);
            while (tempNode.getNext() != null) {
                tempNode = tempNode.getNext();
            }
            tempNode.setNext(newNode);
            currentSize++;
        }
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (size() == 0) {
            throw new NoSuchElementException();
        } else if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return Objects.requireNonNull(getNode(index)).getValue();
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
        if (i == 0) {
            Node newNode = new Node(value);
            if (head != null) {
                newNode.setNext(head);
                head = newNode;
            } else {
                head = newNode;
            }
            currentSize++;
            return;
        }
        if (i == size()) {
            add(value);
        } else if (i < size()) {
            Node newNode = new Node(value);
            Node rightNode = getNode(i);
            Node leftNode = getNode(i - 1);
            newNode.setNext(rightNode);
            assert leftNode != null;
            leftNode.setNext(newNode);
            currentSize++;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getNode(int index) {
        if (index == 0) {
            return head;
        }
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int pointer = 0;
        Node pointerNode = head;
        while (pointer <= index) {
            if (pointer == index) {
                return pointerNode;
            } else {
                pointerNode = pointerNode.getNext();
                pointer++;
            }
        }
        return null;
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
            head = getNode(index + 1);
            currentSize--;
            return;
        }
        if (index == size() - 1) {
            getNode(index - 1).setNext(null);
            currentSize--;
            return;
        }
        Node leftNode = getNode(index - 1);
        Node rightNode = getNode(index + 1);
        leftNode.setNext(rightNode);
        currentSize--;
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
        Node previous = null;
        Node next;
        while (current != null) {
            next = current.getNext();
            current.setNext(previous);
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
        if (head == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Node current = head;
        while (current != null) {
            stringBuilder.append(current).append("-> ");
            current = current.getNext();
        }
        return stringBuilder + "null";
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new ListIterator(head);
    }

    private static class ListIterator implements Iterator<Integer> {

        private Node current;

        public ListIterator(Node first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Integer next() {
            if (hasNext()) {
                Integer item = current.getValue();
                current = current.getNext();
                return item;
            }
            throw new NoSuchElementException();
        }
    }

    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value + " ";
        }
    }
}
