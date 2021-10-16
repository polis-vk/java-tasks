package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    int size = 0;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (head == null) {
            head = new Node(value);
        } else {
            Node currentNode = head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.setNext(new Node(value));
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
        if (index < 0 || head == null || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node currentNode = head;
        if (index == 0) {
            head = head.next;
            return;
        }
        Node prev = null;
        int i = 0;
        while (i != index) {
            prev = currentNode;
            currentNode = currentNode.next;
            i++;
        }
        prev.next = currentNode.next;
        size--;
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (head != null) {
            Node currentNode = head;
            Node prev = null;
            Node next;
            while (currentNode.next != null) {
                next = currentNode.next;
                currentNode.setNext(prev);
                prev = currentNode;
                currentNode = next;
            }
            currentNode.setNext(prev);
            head = currentNode;
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
        if (head == null) {
            return "null";
        }
        Node currentNode = head;
        StringBuilder buff = new StringBuilder(String.valueOf(head.value));
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            buff.append(" -> ");
            buff.append(currentNode.value);
        }
        return buff.append(" -> ").append("null").toString();
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
