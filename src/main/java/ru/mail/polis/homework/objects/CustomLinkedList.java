package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    private int size = 0;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        size++;
        if (head == null) {
            head = new Node(value);
            return;
        }
        findTail(head).setNext(new Node(value));
    }


    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || index >= size || head == null) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        Node current = head;
        Node previous = null;
        while (i != index) {
            previous = current;
            current = current.next;
            i++;
        }
        if (previous == null) {
            head = head.next;
            return;
        }
        previous.setNext(current.next);
        size--;
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (head == null) {
            return;
        }
        Node current = head;
        Node previous = null;
        Node next;
        while (current.next != null) {
            next = current.next;
            current.setNext(previous);
            previous = current;
            current = next;
        }
        current.setNext(previous);
        head = current;
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
        if (head == null) {
            return "null";
        }
        Node current = head;
        StringBuilder sb = new StringBuilder(String.valueOf(head.value));
        while (current.next != null) {
            current = current.next;
            sb.append(" -> ").append(current.value);
        }
        return sb.append(" -> ").append("null").toString();
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

    private Node findTail(Node head) {
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }
}
