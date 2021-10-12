package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList {

    private Node head;
    private Node last;
    private int size = 0;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node addedNode = new Node(value);
        if (size == 0) {
            head = addedNode;
        } else {
            last.setNext(addedNode);
        }
        last = addedNode;
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node previous = head;
        for (int i = 1; i < index; i++) {
            previous = previous.next;
        }
        previous.setNext(previous.next.next);
        size--;
    }

    /**
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node previous = head;
        Node current = head.next;
        Node tmp = last;
        last = head;
        head = tmp;
        previous.setNext(null);
        for (int i = 1; i < size; i++) {
            Node next = current.next;
            current.setNext(previous);
            previous = current;
            current = next;
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
        if (size == 0) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(String.valueOf(head.value));
        sb.append(" -> ");
        Node current = head;
        for (int i = 1; i < size; i++) {
            current = current.next;
            sb.append(current.value);
            sb.append(" -> ");
        }
        sb.append("null");
        return sb.toString();
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
