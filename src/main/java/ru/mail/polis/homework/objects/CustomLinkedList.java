package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    private Node lastNode;
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
            lastNode = head;
            size++;
            return;
        }

        lastNode.setNext(new Node(value));
        lastNode = lastNode.next;
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
        if (index >= size || index < 0 || head == null) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            if (head.next != null) {
                head = head.next;
                size--;
                return;
            }

            head = null;
            size--;
            return;
        }

        Node nextNode = head.next;
        Node beforeNode = null;
        int count = 1;
        while (nextNode.next != null) {
            if (index - 1 == count) {
                beforeNode = nextNode;
            }

            if (beforeNode != null && index + 1 == count) {
                if (count == size) {
                    beforeNode.next = null;
                    lastNode = beforeNode;
                    size--;
                    return;
                }

                beforeNode.next = nextNode;
                size--;
                return;
            }

            nextNode = nextNode.next;
            count++;
        }
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node beforeNode = null;
        Node currentNode = head;
        Node nextNode;
        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = beforeNode;
            beforeNode = currentNode;
            currentNode = nextNode;
        }
        head = beforeNode;
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

        StringBuilder stringBuilder = new StringBuilder();
        Node nextNode = head;
        while (nextNode.next != null) {
            stringBuilder.append(nextNode.value).append(" -> ");
            nextNode = nextNode.next;
        }
        stringBuilder.append(nextNode.value).append(" -> null");

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
