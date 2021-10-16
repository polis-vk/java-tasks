package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    private int length;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node newNode = new Node(value);
        newNode.value = value;
        if (head != null) {
            Node node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.setNext(newNode);
        } else {
            head = newNode;
        }
        length++;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        if (index == 0) {
            head = node.next;
            length--;
            return;
        }
        for (int i = 0; i < index - 1; ++i) {
            node = node.next;
        }
        node.setNext(node.next.next);
        length--;
    }


    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node previousNode = null;
        Node lastNode = head;
        while (lastNode != null) {
            Node next = lastNode.next;
            lastNode.setNext(previousNode);
            previousNode = lastNode;
            lastNode = next;
        }
        head = previousNode;
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
        if (length == 0) {
            return "null";
        }
        StringBuilder result = new StringBuilder();
        Node node = this.head;
        while (node  != null) {
            result.append(node.value).append(" -> ");
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
    }
}
