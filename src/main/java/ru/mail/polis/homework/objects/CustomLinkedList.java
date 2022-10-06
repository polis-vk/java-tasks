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
        size++;
        modCount++;
        if (head == null) {
            head = new Node(value);
            return;
        }

        Node currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }

        currentNode.setNext(new Node(value));
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        checkIndex(index);

        int currentIndex = 0;
        Node currentNode = head;
        while (currentIndex++ != index) {
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
     * @param i     - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) {
        if (i < 0 || i > this.size()) {
            throw new IndexOutOfBoundsException(Integer.toString(i));
        }

        Node newNode = new Node(value);
        size++;
        modCount++;
        if (i == 0) {
            newNode.setNext(head);
            head = newNode;
            return;
        }

        int beforeInsertIndex = 0;
        Node currentNode = head;
        while (beforeInsertIndex++ != i - 1) {
            currentNode = currentNode.next;
        }

        newNode.setNext(currentNode.next);
        currentNode.setNext(newNode);
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
        checkIndex(index);

        size--;
        modCount++;
        if (index == 0) {
            head = head.next;
            return;
        }

        int beforeRemoveIndex = 0;
        Node currentNode = head;
        while (beforeRemoveIndex++ != index - 1) {
            currentNode = currentNode.next;
        }

        currentNode.setNext(currentNode.next.next);
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
        modCount++;
        Node newHead = null;

        while (head != null) {
            Node newNode = head;
            head = head.next;
            newNode.setNext(newHead);
            newHead = newNode;
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
        StringBuilder result = new StringBuilder();

        Node currentNode = head;
        while (currentNode != null) {
            result.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }

        return result.append("null").toString();
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
            private final int fixedModCount = CustomLinkedList.this.modCount;
            private Node iteratorHead = CustomLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return iteratorHead != null;
            }

            @Override
            public Integer next() {
                if (modCount != fixedModCount) {
                    throw new ConcurrentModificationException();
                } else if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }

                int value = iteratorHead.value;
                iteratorHead = iteratorHead.next;
                return value;
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
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
