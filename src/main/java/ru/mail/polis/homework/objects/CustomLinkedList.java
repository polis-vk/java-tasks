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
    private int modCount = 0;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        int size = 0;
        Node temp = head;
        while (temp != null) {
            size++;
            temp = temp.next;
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
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            Node tail = head;
            while (tail.next != null) {
                tail = tail.next;
            }
            tail.setNext(node);
        }
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
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        int value = 0;
        Node temp = head;
        for (int i = 0; i < size(); i++) {
            if (i == index) {
                value = temp.value;
				break;
            }
            temp = temp.next;
        }
        return value;
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
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }
        Node node = new Node(value);
        if (i == 0) {
            node.next = head;
            head = node;
        } else {
            Node previous = head;
            for (int j = 0; j < i - 1; j++) {
                previous = previous.next;
            }
            Node after = previous.next;
            node.setNext(after);
            previous.setNext(node);
        }
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
        if (size() == 0 || index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node previous = head;
            for (int j = 0; j < index - 1; j++) {
                previous = previous.next;
            }
            Node delete = previous.next;
            Node after = delete.next;
            previous.setNext(after);
            delete.setNext(null);
        }
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
        Node current = head;
        Node previous = null;
        Node after;
        while (current != null) {
            after = current.next;
            current.next = previous;
            previous = current;
            current = after;
        }
        head = previous;
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
        StringBuilder result = new StringBuilder();
        Node temp = head;
        while (temp != null) {
            result.append(temp.value).append(" -> ");
            temp = temp.next;
        }
        result.append("null");
        return result.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Integer> {

        private Node head = CustomLinkedList.this.head;
        private final int fixedModCount = modCount;

        Itr() {}

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
