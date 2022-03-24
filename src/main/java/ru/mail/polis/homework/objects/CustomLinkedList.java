package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
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
        if (head == null) {
            head = new Node(value);
            return;
        }
        Node curNode = head;
        while (curNode.next != null) {
            curNode = curNode.next;
        }
        curNode.next = new Node(value);
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        Node curNode = head;
        for (int i = 0; i != index; i++) {
            curNode = curNode.next;
        }
        return curNode.value;
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
        size++;
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("index: " + i);
        }
        Node curNode = head;
        if (i == 0) {
            head = new Node(value);
            head.setNext(curNode);
            return;
        }
        for (int curIndex = 0; curIndex != i - 1; curIndex++) {
            curNode = curNode.next;
        }
        Node nextNode = curNode.next;
        curNode.next = new Node(value);
        curNode.next.next = nextNode;
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
        Node curNode = head;
        for (int i = 0; i != index - 1; i++) {
            curNode = curNode.next;
        }
        curNode.next = curNode.next.next;
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
        Node curNode;
        Node prevNode = head;
        Node nextNode = head.next;
        prevNode.setNext(null);
        while (nextNode != null) {
            curNode = nextNode.next;
            nextNode.setNext(prevNode);
            prevNode = nextNode;
            nextNode = curNode;
        }
        head = prevNode;
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
        StringBuilder outputList = new StringBuilder();
        Node curNode = head;
        while (curNode != null) {
            outputList.append(curNode.value).append(" -> ");
            curNode = curNode.next;
        }
        outputList.append("null");
        return outputList.toString();
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
            int curIndex;

            @Override
            public boolean hasNext() {
                return curNode != null;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                curIndex = curNode.value;
                curNode = curNode.next;
                return curIndex;
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
