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
    private Node tail;

    private int size = 0;
    private int modCount = 0;

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
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
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
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size() - 1) {
            return tail.value;
        }

        Node currNode = head;

        for (int currInx = 0; currInx < index; currInx++) {
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
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(value);

        if (i == 0) {
            if (size() == 0) {
                add(value);
            } else {
                newNode.setNext(head);
                head = newNode;
            }
        } else if (i == size) {
            tail.setNext(newNode);
            tail = newNode;
        } else {
            Node currNode = head;
            for (int currInx = 0; currInx < i - 1; currInx++) {
                currNode = currNode.next;
            }
            newNode.setNext(currNode.next);
            currNode.setNext(newNode);
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
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            head = head.next;

            if (head == null) {
                tail = null;
            }
        } else {
            Node currNode = head;
            for (int i = 0; i < index - 1; i++) {
                currNode = currNode.next;
            }

            if (index == size() - 1) {
                tail = currNode;
            } else {
                currNode.setNext(currNode.next.next);
            }
        }
        size--;
        modCount++;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     * сходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node currNode = head;
        Node nodeHead = null;
        Node tmpNode;

        while (currNode != null) {
            tmpNode = new Node(currNode.value);
            tmpNode.setNext(nodeHead);
            nodeHead = tmpNode;

            if (nodeHead.next == null) {
                tail = nodeHead;
            }
            currNode = currNode.next;
        }

        head = nodeHead;
        modCount += size;
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
        StringBuilder stringList = new StringBuilder();
        Node currNode = head;

        for (int i = 0; i < size(); i++) {
            stringList.append(currNode.value).append(" -> ");
            currNode = currNode.next;
        }
        stringList.append("null");

        return stringList.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new CustomLinkedListIterator();
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

    private class CustomLinkedListIterator implements Iterator<Integer> {

        private Node currNode;
        private final int fixedModCount;

        CustomLinkedListIterator() {
            this.currNode = head;
            this.fixedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        @Override
        public Integer next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int nextResult = currNode.value;
            currNode = currNode.next;

            return nextResult;
        }
    }
}
