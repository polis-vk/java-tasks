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
        if (head == null) {
            head = new Node(value);
            size++;
            return;
        }

        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        node.setNext(new Node(value));
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            head = null;
            size--;
            return;
        }

        Node node = head;
        for (int i = 0; i < index - 1; i++) {
            node = node.next;
        }
        node.setNext(node.next.next);
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
        if (size == 0 || size == 1) {
            return;
        }

        Node oldNode = head;
        Node prevNewNode = new Node(oldNode.value);
        for (int i = 0; i < size - 1; i++) {
            oldNode = oldNode.next;
            Node newNode = new Node(oldNode.value);
            newNode.setNext(prevNewNode);
            prevNewNode = newNode;
        }
        head = prevNewNode;
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
        StringBuilder resString = new StringBuilder();
        Node node = head;
        if (size > 0) {
            while (node.next != null) {
                resString.append(node.value).append(" -> ");
                node = node.next;
            }
            resString.append(node.value).append(" -> ");
        }
        return resString + "null";
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
