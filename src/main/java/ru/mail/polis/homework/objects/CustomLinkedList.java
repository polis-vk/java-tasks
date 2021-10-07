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
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.setNext(new Node(value));
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
        Node temp = head;
        Node previous = head;
        if (temp == null) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        while (i < index) {
            previous = temp;
            temp = temp.next;
            if (temp == null) {
                throw new IndexOutOfBoundsException();
            }
            i++;
        }
        if (previous == temp) {
            head = temp.next;
        } else if (temp.next == null) {
            previous.setNext(null);
        } else {
            previous.setNext(temp.next);
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
        revertRecursion(null, head);
    }

    private void revertRecursion(Node prev, Node cur) {
        if (cur.next != null) {
            revertRecursion(cur, cur.next);
        } else {
            head = cur;
        }
        cur.setNext(prev);
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
        if (head == null) {
            return "null";
        }
        StringBuilder result = new StringBuilder(String.valueOf(head.value));
        result.append(" -> ");
        Node temp = head;
        while (temp.next != null) {
            result.append(temp.next.value).append(" -> ");
            temp = temp.next;
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
