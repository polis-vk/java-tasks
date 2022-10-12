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
            tail = head;
        } else {
            Node node = new Node(value);
            tail.setNext(node);
            tail = node;
            if (head.next == null) {
                head.next = tail;
            }
        }
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        return getNode(index).value;
    }

    public Node getNode(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        int i = 0;
        Node node = head;
        while (i < index) {
            node = node.next;
            i++;
        }
        return node;
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
        if (i == size) {
            add(value);
            return;
        }
        Node node = new Node(value);
        if (i == 0) {
            if (head != null) {
                node.setNext(head);
            }
            head = node;
        } else if (i < size) {
            Node left = getNode(i - 1);
            Node right = getNode(i);
            node.setNext(right);
            left.setNext(node);
        } else {
            throw new IndexOutOfBoundsException();
        }
        size++;
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
        if (head == null || index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size - 1) {
            Node node = getNode(index - 1);
            node.setNext(null);
            tail = node;
            size--;
            return;
        }
        if (index == 0) {
            head = getNode(index + 1);
            size--;
            return;
        }
        Node leftNode = getNode(index - 1);
        Node rightNode = getNode(index + 1);
        leftNode.setNext(rightNode);
        size--;
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
        Node previous = null;
        Node current = head;
        while (current != null) {
            Node next = current.next;
            current.setNext(previous);
            previous = current;
            current = next;
        }
        tail = head;
        tail.setNext(null);
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
        StringBuilder stringBuilder = new StringBuilder();
        Node node = head;
        while (node != null) {
            stringBuilder.append(node.value);
            node = node.next;
            stringBuilder.append(" -> ");
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
        return new Iter(head);
    }

    private static class Iter implements Iterator<Integer> {
        Node iter;

        public Iter(Node head) {
            iter = head;
        }

        @Override
        public boolean hasNext() {
            return iter != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int value = iter.value;
            iter = iter.next;
            return value;
        }
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
}
