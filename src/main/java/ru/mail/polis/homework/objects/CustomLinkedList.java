package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private static final String DELIMITER = " -> ";
    private static final String NULL = "null";

    private Node head;
    private Node tail;
    private int countOfElements;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        countOfElements++;

        if (head == null) {
            head = new Node(value);
            tail = head;
            return;
        }
        Node prevTail = tail;
        tail = new Node(value);
        prevTail.setNext(tail);
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || index >= countOfElements) {
            throw new IndexOutOfBoundsException();
        }

        countOfElements--;
        if (index == 0) {
            head = head.next;
            return;
        }

        Node prevNode = null;
        Node nowNode = head;
        for (int i = 0; i < index; i++) {
            prevNode = nowNode;
            nowNode = nowNode.next;
        }

        prevNode.setNext(nowNode.next);
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (head == null || countOfElements == 1) {
            return;
        }

        Node nowNode = head;
        for (Node nodeForIter = head, prevNode = null; nodeForIter != null; ) {
            nowNode = nodeForIter;
            nodeForIter = nodeForIter.next;
            nowNode.setNext(prevNode);
            prevNode = nowNode;
        }

        head = nowNode;
    }

    /**
     * Метод выводит всю последовательность хранящуюся в списке начиная с head.
     * Формат вывода:
     * - значение каждой Node должно разделяться " -> "
     * - последовательность всегда заканчивается на null
     * - если в списке нет элементов - верните строку "null"
     */

    @Override
    public String toString() {
        if (head == null) {
            return NULL;
        }

        StringBuilder listToString = new StringBuilder();
        for (Node nowNode = head; nowNode != null; ) {
            listToString.append(nowNode.value);
            listToString.append(DELIMITER);
            nowNode = nowNode.next;
        }
        listToString.append(NULL);

        return listToString.toString();
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
