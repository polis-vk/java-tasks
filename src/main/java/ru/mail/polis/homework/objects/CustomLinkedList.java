package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    private int size;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.setNext(newNode);
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
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            head = head.next;
        }

        int count = 0;
        Node node = head;

        while (count != index) {
            count++;
            if (count != index) {
                node = node.next;
            }
        }

        node.setNext(node.next.next);
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
        if (head.next == null) {
            return;
        }

        Node node;
        Node newHead = null;

        while (head.next != null) {
            node = head;
            while (node.next.next != null) {
                node = node.next;
            }

            if (newHead == null) {
                newHead = node.next;
            }
            node.next.setNext(node);
            node.setNext(null);
        }

        head = newHead;
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
        StringBuilder sb = new StringBuilder();
        Node node = head;

        while (node != null) {
            sb.append(node.value).append(" -> ");
            node = node.next;
        }
        sb.append("null");

        return sb.toString();
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
