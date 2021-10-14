package ru.mail.polis.homework.objects;

import java.util.ArrayList;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private int size = 0;
    private Node head;
    private static final String END_OF_LIST = "null";

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (head == null) {
            head = new Node(value);
            return;
        }
        Node nodeForInserting = head;
        while (nodeForInserting.next != null) {
            nodeForInserting = nodeForInserting.next;
        }
        nodeForInserting.setNext(new Node(value));
        ++size;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || head == null || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index when removing list's element");
        }
        if (index == 0) {
            head = head.next;
            return;
        }
        Node nodeForRemoving = head.next;
        Node prev = head;
        int curIndex = 1;
        while (curIndex != index && nodeForRemoving.next != null) {
            nodeForRemoving = nodeForRemoving.next;
            prev = prev.next;
            ++curIndex;
        }
        prev.next = nodeForRemoving.next;
        --size;
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (head == null || head.next == null) {
            return;
        }
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node nextStatusQuo = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nextStatusQuo;
        }
        head = prev;
    }

    /**
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
        if (head == null) {
            return END_OF_LIST;
        }

        Node cur = head;
        StringBuilder listInfo = new StringBuilder();
        while (cur != null) {
            listInfo.append(cur.value + " -> ");
            cur = cur.next;
        }
        listInfo.append(END_OF_LIST);
        return listInfo.toString();
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
