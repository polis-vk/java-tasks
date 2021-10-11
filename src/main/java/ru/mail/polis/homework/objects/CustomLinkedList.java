package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    private Node last;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (head == null) {
            head = new Node(value);
            last = head;
        } else {
            last.setNext(new Node(value));
            last = last.getNext();
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
        if (index < 0 || head == null) {
            throw new IndexOutOfBoundsException();
        }

        Node nodeBeforeEl = head;
        for (int i = 0; i < index - 1; i++) {
            if (nodeBeforeEl == null) {
                throw new IndexOutOfBoundsException();
            }
            nodeBeforeEl = nodeBeforeEl.getNext();
        }

        Node currNode = nodeBeforeEl.getNext();
        if (currNode == null) {
            if (index == 0) {
                //Case for list consists of 1 element
                head = null;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
        nodeBeforeEl.setNext(currNode.getNext());
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node currNode = head;
        Node prevNode = null;
        while (currNode != null) {
            Node next = currNode.getNext();
            currNode.setNext(prevNode);
            prevNode = currNode;
            currNode = next;
        }

        head = prevNode;
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
        String res = "";
        Node currNode = head;
        while (currNode != null) {
            res += currNode.getValue() + " -> ";
            currNode = currNode.getNext();
        }
        res += "null";
        return res;
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

        public Node getNext() {
            return next;
        }

        public int getValue() {
            return value;
        }
    }
}
