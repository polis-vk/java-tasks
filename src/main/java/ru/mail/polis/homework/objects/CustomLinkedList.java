package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */

public class CustomLinkedList {
    private final static String TO_STRING_SEPARATOR = " -> ";
    private final static String EMPTY_ELEMENT = "null";

    private Node head;
    private Node lastNode;
    private int size = 0;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (head == null) {
            lastNode = head = new Node(value);
        } else {
            lastNode.setNext(new Node(value));
            lastNode = lastNode.next;
        }
        size++;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        isValidIndex(index);
        if (index == 0) {
            head = head.next;
            size--;
            return;
        }
        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        current.setNext(current.next.next);
        size--;
    }

    private void isValidIndex(int index) {
        if (size == 0 || index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void revertList() {
        Node current = head;
        Node prevNode = null;
        Node next;
        while (current.next != null) {
            next = current.next;
            current.setNext(prevNode);
            prevNode = current;
            current = next;
        }
        head = current;
        head.setNext(prevNode);
    }

    /**
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
        if (size == 0) {
            return EMPTY_ELEMENT;
        }
        StringBuilder result = new StringBuilder();
        Node current = head;
        while (current != null) {
            result.append(current.value).append(TO_STRING_SEPARATOR);
            current = current.next;
        }
        return result.append(EMPTY_ELEMENT).toString();
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
