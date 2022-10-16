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
        if (head == null) {
            head = new Node(value);
            tail = head;
            size++;
            return;
        }
        tail.setNext(new Node(value));
        tail = tail.getNext();
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index == size() - 1) {
            return tail.value;
        }
        if (size() == 0) {
            throw new IndexOutOfBoundsException();
        } else if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return getNode(index).getValue();
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
        if (i > size() || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node currentNode = head;
        if (i == 0) {
            head = new Node(value);
            head.setNext(currentNode);
            size++;
            return;
        }
        if (i == size()) {
            add(value);
        } else if (i < size()) {
            currentNode = getNode(i - 1);
            Node tempNode = currentNode.getNext();
            currentNode.setNext(new Node(value));
            currentNode.getNext().setNext(tempNode);
            size++;
        }
    }

    private Node getNode(int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return head;
        }
        if (index == size() - 1) {
            return tail;
        }
        int pointer = 0;
        Node pointerNode = head;
        while (pointerNode != null) {
            if (pointer == index) {
                break;
            }
            pointerNode = pointerNode.getNext();
            pointer++;
        }
        return pointerNode;
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
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            return;
        }
        if (index == 0) {
            head = head.getNext();
            size--;
            return;
        }
        Node currentNode = getNode(index - 1);
        currentNode.setNext(currentNode.getNext().getNext());
        tail = currentNode;
        size--;
        if (index == size() - 1) {
            tail = currentNode;
            size--;
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
        Node current;
        Node previous = null;
        tail = head;
        while (head != null) {
            current = head;
            head = head.getNext();
            current.setNext(previous);
            previous = current;
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
