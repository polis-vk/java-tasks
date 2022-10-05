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
    private Node last;
    private int size = 0;
    private int modCount;

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
        if (size() == 0) {
            head = new Node(value);
            last = head;
        } else {
            last.setNext(new Node(value));
            last = last.next;
        }
        size++;
        modCount++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        Node currNode = head;
        for (int i = 1; i <= index; i++) {
            currNode = currNode.next;
        }
        return currNode.value;
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
        if (i > size()) {
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }
        if (i == 0) {
            Node tempNode = head;
            head = new Node(value);
            head.next = tempNode;
        } else {
            Node currNode = head;
            for (int j = 1; j < i; j++) {
                currNode = currNode.next;
            }
            Node tempNode = currNode.next;
            currNode.next = new Node(value);
            currNode.next.next = tempNode;
        }
        size++;
        modCount++;
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
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        Node currNode = head;
        for (int i = 1; i < index; i++) {
            currNode = currNode.next;
        }
        if (index == 0) {
            head = currNode.next;
        }
        if (index < size() - 1) {
            currNode.next = currNode.next.next;
        } else {
            currNode.next = null;
            last = currNode;
        }
        size--;
        modCount--;
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
        Node newHead = new Node(get(size() - 1));
        Node currNode = newHead;
        for (int i = size() - 2; i > -1; i--) {
            currNode.next = new Node(get(i));
            if (i == 0) {
                last = currNode.next;
            }
            currNode = currNode.next;
        }
        head = newHead;
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
        StringBuilder sb = new StringBuilder();
        Node node = head;
        for (int i = 1; i <= size(); i++) {
            sb.append(node.value).append(" -> ");
            node = node.next;
        }
        sb.append("null");
        return sb.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator {

        private int pos;
        private final int fixedModCount = modCount;

        @Override
        public boolean hasNext() {
            return pos < size();
        }

        @Override
        public Integer next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            } else if (pos >= size()) {
                throw new NoSuchElementException();
            }
            int element = get(pos);
            pos++;
            return element;
        }
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