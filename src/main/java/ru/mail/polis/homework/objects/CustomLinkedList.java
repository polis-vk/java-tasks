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

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        int size = 0;
        Node tmpNode = head;
        while (tmpNode != null) {
            ++size;
            tmpNode = tmpNode.next;
        }
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
        if (head == null) {
            head = newNode;
            tail = head;
        }
        tail.setNext(newNode);
        tail = newNode;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (size() <= index || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        Node newNode = head;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode.value;
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
        if (size() < i || i < 0) {
            throw new IndexOutOfBoundsException(i);
        }
        if (i == 0) {
            Node newNode = new Node(value);
            newNode.setNext(head);
            head = newNode;
        } else {
            Node tmpNode = head;
            Node newNode = new Node(value);
            for (int j = 0; j < i - 1; j++) {
                if (tmpNode == null) {
                    break;
                }
                tmpNode = tmpNode.next;
            }
            newNode.setNext(tmpNode.next);
            tmpNode.setNext(newNode);
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
        if (size() <= index || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node tmpNode = head;
            for (int i = 0; i < index - 1; i++) {
                if (tmpNode == null) {
                    break;
                }
                tmpNode = tmpNode.next;
            }
            tmpNode.setNext(tmpNode.next.next);
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
        Node tmpNode;
        Node nextNode = head.next;
        Node prevNode = head;
        prevNode.setNext(null);
        while (nextNode != null) {
            tmpNode = nextNode.next;
            nextNode.setNext(prevNode);
            prevNode = nextNode;
            nextNode = tmpNode;
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
        StringBuilder listToString = new StringBuilder();
        Node tmpNode = head;
        do {
            listToString.append(tmpNode.value);
            listToString.append(" -> ");
            tmpNode = tmpNode.next;
        } while (tmpNode != null);
        return listToString.append("null").toString();
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
            Node prevNode = head;
            Node nextNode;

            @Override
            public boolean hasNext() {
                if (prevNode != null) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    nextNode = prevNode;
                    prevNode = nextNode.next;
                    return (int) nextNode.value;
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
