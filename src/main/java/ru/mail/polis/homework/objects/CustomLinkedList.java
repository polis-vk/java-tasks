package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    private int elCount = 0;

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
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new Node(value);
        }
        elCount++;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || index >= elCount) {
            throw new IndexOutOfBoundsException();
        }
        Node cur = head;
        if (index == 0) {
            if (cur.next != null) {
                head = cur.next;
            } else {
                head = null;
            }
        } else {
            for (int i = 0; i < index - 1; i++) {
                cur = cur.next;
            }
            if (index == elCount - 1) {
                cur.next = null;
            } else {
                cur.next = cur.next.next;
            }
        }
        elCount--;
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node cur = head;
        Node prev = null;
        Node next;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        head = prev;
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
        StringBuilder buf = new StringBuilder();
        Node cur = head;
        for (int i = 0; i < elCount; i++) {
            buf.append(cur.value).append(" -> ");
            cur = cur.next;
        }
        return buf.append("null").toString();
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