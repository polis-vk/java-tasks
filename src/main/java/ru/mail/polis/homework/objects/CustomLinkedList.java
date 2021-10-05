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
        if (head == null) {
            head = new Node(value);
            return;
        }
        Node tmpNode = head;
        while (tmpNode.next != null) {
            tmpNode = tmpNode.next;
        }
        tmpNode.setNext(new Node(value));
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head = head.next;
        }
        Node tmpNode = head;
        for (int i = 0; i < index - 1; i++) {
            if (tmpNode.next == null) {
                throw new IndexOutOfBoundsException();
            }
            tmpNode = tmpNode.next;
        }
        if (tmpNode.next == null) {
            throw new IndexOutOfBoundsException();
        }
        tmpNode.setNext(tmpNode.next.next);
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
        Node tmpNode = head;
        Node prev = null;
        Node next = tmpNode.next;
        while (next.next != null) {
            tmpNode.setNext(prev);
            prev = tmpNode;
            tmpNode = next;
            next = next.next;
        }
        tmpNode.setNext(prev);
        next.setNext(tmpNode);
        head = next;
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
        StringBuilder stringBuilder = new StringBuilder();
        Node tmpNode = head;
        while (tmpNode != null) {
            stringBuilder.append(tmpNode.value);
            stringBuilder.append(" -> ");
            tmpNode = tmpNode.next;
        }
        stringBuilder.append("null");
        return stringBuilder.toString();
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
