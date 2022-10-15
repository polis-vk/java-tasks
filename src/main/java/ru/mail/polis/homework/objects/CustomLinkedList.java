package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private int modCount;
    private Node head;
    private Node tail;
    private int listSize;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        return listSize;
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
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        listSize++;
        modCount++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        checkInclusiveIndex(index);
        if (index == size() - 1) {
            return tail.value;
        }

        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.value;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список на заданную позицию.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param i - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) {
        checkIndex(i);
        if (i == 0) {
            Node newNode = new Node(value);
            newNode.setNext(head);
            head = newNode;
        } else {
            Node temp = head;
            for (int k = 0; k < i - 1; k++) {
                temp = temp.next;
            }
            Node newNode = new Node(value);
            newNode.setNext(temp.next);
            temp.setNext(newNode);
        }
        listSize++;
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
        checkInclusiveIndex(index);
        Node temp = head;
        if (index == 0) {
            head = temp.next;
        } else {
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }

            if (temp.next == tail) {
                tail = temp;
            }
            temp.setNext(temp.next.next);
        }
        listSize--;
        modCount++;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        tail = head;
        Node previousNode = null;
        Node currentNode = head;
        Node next = null;

        while (currentNode != null) {
            next = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = next;
        }
        head = previousNode;
        modCount++;
    }

    /**
     * 1 тугрик
     * Метод выводит всю последовательность хранящуюся в списке начиная с head.
     * Формат вывода:
     *  - значение каждой Node должно разделяться " -> "
     *  - последовательность всегда заканчивается на null
     *  - если в списке нет элементов - верните строку "null"
     *
     * @return - String with description all list
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node currentNode = head;

        while (currentNode != null) {
            stringBuilder.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        stringBuilder.append("null");
        return stringBuilder.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new LinkedListIterator();
    }

    private void checkIndex(int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of range!");
        }
    }

    private void checkInclusiveIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of range!");
        }
    }

    private class LinkedListIterator implements Iterator<Integer> {

        private Node head = CustomLinkedList.this.head;
        private final int fixedModCount = modCount;

        @Override
        public boolean hasNext() {
            return head != null;
        }

        @Override
        public Integer next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (head == null) {
                throw new NoSuchElementException();
            }

            Integer currentElement = head.value;
            head = head.next;
            return currentElement;
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
