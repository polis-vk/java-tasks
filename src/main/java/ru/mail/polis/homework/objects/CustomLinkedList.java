package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 13 тугриков
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node lastNode;
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
            lastNode = head;
        } else {
            lastNode.setNext(new Node(value));
            lastNode = lastNode.next;
        }
        size++;
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
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException(i);
        }

        if (i == size) {
            add(value);
            return;
        }

        Node bufferNode = head;
        if (i == 0) {
            head = new Node(value);
            head.setNext(bufferNode);
            size++;
            return;
        }

        int index = 0;
        Node currentNode = head;
        while (index < size) {
            if (index == i - 1) {
                bufferNode = currentNode.next;
                currentNode.setNext(new Node(value));
                currentNode.next.setNext(bufferNode);
                size++;
                return;
            }
            currentNode = currentNode.next;
            index++;
        }
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
            throw new IndexOutOfBoundsException(index);
        }

        int i = 0;
        Node currentNode = head;
        while (i < size) {
            if (i == index - 1) {
                currentNode.setNext(currentNode.next.next);
                size--;
                return;
            }
            currentNode = currentNode.next;
            i++;
        }
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
        Node currentNode = head.next;
        Node previousNode = head;
        Node bufferNode;
        previousNode.setNext(null);
        while (currentNode != null) {
            bufferNode = currentNode.next;
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = bufferNode;
        }
        head = previousNode;
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
        StringBuilder listDescription = new StringBuilder();
        if (head == null) {
            listDescription.append("null");
            return listDescription.toString();
        }

        listDescription.append(head.value).append(" -> ");
        Node currentNode = head;
        while (currentNode.next != null) {
            listDescription.append(currentNode.next.value).append(" -> ");
            currentNode = currentNode.next;
        }
        listDescription.append("null");
        return listDescription.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            Node currentNode = head;
            Node previousNode;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    previousNode = currentNode;
                    currentNode = previousNode.next;
                    return previousNode.value;
                }
                throw new NoSuchElementException();
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
