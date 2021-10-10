package ru.mail.polis.homework.objects;

import java.util.LinkedList;

/**
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList {

    private Node head;
    private int size;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (head == null) {
            head = new Node(value);
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new Node(value);
        size++;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при этом связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || head == null || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = head;
        for (int currentIndex = 0; current.next != null; currentIndex++, current = current.next) {
            if (currentIndex + 1 == index) {
                current.next = current.next.next;
                size--;
                return;
            }
        }
    }

    /**
     * Реализовать метод:
     * Переворачивает все элемента списка.
     * Пример:
     * исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (head != null && head.next != null) {
            Node reversedSlice = null;
            for (Node current = head; current != null; ) {
                Node next = current.next;
                current.next = reversedSlice;
                reversedSlice = current;
                current = next;
            }
            head = reversedSlice;
        }
    }

    /**
     * Метод выводит всю последовательность, хранящуюся в списке начиная с head.
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
        StringBuilder res = new StringBuilder();
        for (Node current = head; current != null; current = current.next) {
            res.append(current.value).append(current.next != null ? " -> " : "");
        }
        return res + " -> null";
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
