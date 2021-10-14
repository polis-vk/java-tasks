package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList {

    private static final String ITEM_SEPARATOR = " -> ";
    private static final String NULL_ITEM = "null";
    private Node head;
    private Node last;
    private int length = 0;
    /**
     * [x] Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (head == null) {
            head = new Node(value);
            last = head;
            length++;
            return;
        }
        last.next = new Node(value);
        last = last.next;
        length++;
    }

    /**
     * [x] Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            if (head.next == null) {
                last = null;
            }
            head = head.next;
            length--;
            return;
        }
        Node previous = head;
        int i = 1;
        for (; i < index; i++) {
            previous = previous.next;
        }
        if (previous.next.next == null) {
            last = previous;
        }
        previous.next = previous.next.next;
        length--;
    }

    /**
     * [x] Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node previous = null;
        for (Node current = head; current != null; ) {
            final Node next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        head = previous;
    }

    /**
     * [x] Метод выводит всю последовательность хранящуюся в списке начиная с head.
     * Формат вывода:
     * - значение каждой Node должно разделяться " -> "
     * - последовательность всегда заканчивается на null
     * - если в списке нет элементов - верните строку "null"
     *
     * @return - String with description all list
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Node current = head; current != null; current = current.next) {
            builder.append(current.value);
            builder.append(ITEM_SEPARATOR);
        }
        builder.append(NULL_ITEM);
        return builder.toString();
    }

    private static class Node {
        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
