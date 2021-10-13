package ru.mail.polis.homework.objects;

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
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.next = newNode;
        }
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (head == null || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node prev = null;
        Node current = head;
        for (int i = 0; i < index; i++) {
            prev = current;
            current = current.next;
            if (current == null) {
                throw new IndexOutOfBoundsException();
            }
        }
        if (prev != null) {
            prev.setNext(current.next);
        } else {
            head = head.next;
        }
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node current = head;
        if (current == null) {
            return;
        }
        Node next = current.next;
        Node prev;
        current.next = null;
        while (next != null) {
            prev = current;
            current = next;
            next = current.next;
            current.setNext(prev);
        }
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
        Node node = head;
        StringBuilder result = new StringBuilder();
        while (node != null) {
            result.append(node.value);
            result.append(" -> ");
            node = node.next;
        }
        result.append("null");
        return result.toString();
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

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }
}
