package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node first = null;
    private Node last;
    private int size;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (first == null) {
            first = new Node(value);
            first.next = null;
            last = first;
        } else {
            Node curr = last;
            last = new Node(value);
            curr.next = last;
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
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            first = null;
            last = null;
            size--;
            return;
        }

        Node prev = null;
        Node current = first;

        int i = 0;

        // Ищем элемент по заданному индексу
        while (i != index) {
            prev = current;
            current = current.next;
            i++;
        }

        // Рассматриваем несколько случаев, удаляя таким образом элемент
        if (last == current) {
            last = prev;
            last.next = null;
        } else if (first == current) {
            first = first.next;
        } else {
            prev.next = current.next;
        }

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
        if (size < 2) {
            return;
        }

        if (size == 2) {
            Node temp = last;
            first.next = null;
            last = first;
            first = temp;
            first.next = last;
            return;
        }

        Node prev = first;
        Node curr = prev.next;
        Node temp;
        prev.next = null;

        // Меняем ссылки у элементов со следующего на предыдущий от начального до конечного
        do {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        } while (curr != null);

        // Меняем местами первый и последний элементы и их ссылки
        temp = last;
        last = first;
        last.next = null;
        first = temp;
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
        StringBuilder builder = new StringBuilder();

        Node current = first;
        while (current != null) {
            builder.append(current.value);
            builder.append(" -> ");
            current = current.next;
        }

        if (builder.toString().isEmpty()) {
            return "null";
        }

        return builder.append("null").toString();
    }

    private static class Node {
        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
