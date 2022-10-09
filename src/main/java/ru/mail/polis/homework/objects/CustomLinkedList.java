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

    private int modCount = 0;
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
        Node newNode = new Node(value);
        if (size == 0) {
            tail = head = newNode;
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
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size - 1) {
            return tail.value;
        }
        int currentIndex = 0;
        Node currentNode = head;
        while (currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
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
        if (size < i || i < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node newNode = new Node(value);
        if (i == 0) {
            newNode.setNext(head);
            head = newNode;
        } else if (i == size) {
            tail.setNext(newNode);
            tail = newNode;
        } else {
            int currentIndex = 0;
            Node currentNode = head;
            while (currentIndex < i - 1) {
                currentNode = currentNode.next;
                currentIndex++;
            }
            newNode.setNext(currentNode.next);
            currentNode.setNext(newNode);
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
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            int currentIndex = 0;
            Node currentNode = head;
            while (currentIndex < index - 1) {
                currentNode = currentNode.next;
                currentIndex++;
            }
            currentNode.setNext(currentNode.next.next);
            if (index == size - 1) {
                tail = currentNode;
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
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node currentNode = head;
        Node newHead = null;
        while (currentNode != null) {
            Node temporaryNode = new Node(currentNode.value);
            temporaryNode.setNext(newHead);
            newHead = temporaryNode;
            if (newHead.next == null) {
                tail = newHead;
            }
            currentNode = currentNode.next;
        }
        head = newHead;
        modCount += size;
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
        StringBuilder listRepresentationBuilder = new StringBuilder();
        Node currentNode = head;
        while (currentNode != null) {
            listRepresentationBuilder.append(String.format("%d -> ", currentNode.value));
            currentNode = currentNode.next;
        }
        listRepresentationBuilder.append("null");
        return listRepresentationBuilder.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<Integer> {

        private final int fixedModCount = modCount;
        private Node currentNode = head;

        @Override
        public boolean hasNext() {
            checkModCount();
            return currentNode != null;
        }

        @Override
        public Integer next() {
            checkModCount();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int nextResult = currentNode.value;
            currentNode = currentNode.next;
            return nextResult;
        }
        private void checkModCount() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
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