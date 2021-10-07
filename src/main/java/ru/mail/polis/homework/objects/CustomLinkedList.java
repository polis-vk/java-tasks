package ru.mail.polis.homework.objects;

import java.util.ArrayList;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;

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
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || head == null) {
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
        if (curIndex != index) {
            throw new IndexOutOfBoundsException("Invalid index when removing list's element");
        }
        prev.next = nodeForRemoving.next;
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
        Node end = head;
        ArrayList<Integer> listValues = new ArrayList<>();
        while (end != null) {
            listValues.add(end.value);
            end = end.next;
        }
        Node cur = head;
        for (int i = listValues.size() - 1; i >= 0; --i) {
            cur.value = listValues.get(i);
            cur = cur.next;
        }
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
        Node cur = head;
        StringBuffer listInfo = new StringBuffer();
        while (cur != null) {
            listInfo.append(cur.value + " -> ");
            cur = cur.next;
        }
        listInfo.append("null");
        return listInfo.toString();
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
