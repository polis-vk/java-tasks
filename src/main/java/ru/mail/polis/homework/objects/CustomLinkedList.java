package ru.mail.polis.homework.objects;

import java.util.NoSuchElementException;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    
    private int size;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (this.head == null) {
            this.head = new Node(value);
            return;
        }

        Node toAdd = this.head;
        while (toAdd.next != null) {
            toAdd = toAdd.next;
        }

        toAdd.next = new Node(value);
        ++this.size;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        Node prevNode = this.head; // узел предшествующий искомому

        if (index == 0) {
            this.head = this.head.next;
        } else {
            for (int i = 1; i < index; ++i) {
                prevNode = prevNode.next;
            }
            prevNode.next = prevNode.next.next;
        }
        --this.size;
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (this.head == null || this.head.next == null) {
            return;
        }

        int length = 1;
        Node current = this.head;
        while (current.next != null) {
            current = current.next;
            ++length;
        }

        Node first;
        Node last;
        int temp;
        for (int i = 0; i < length / 2; ++i) {

            first = this.head;
            for (int j = 0; j < i; ++j)
                first = first.next;
            last = first;
            for (int j = i << 1; j < length - 1; ++j) {
                last = last.next;
            }
            temp = first.value;
            first.value = last.value;
            last.value = temp;
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
        // небольшая проверка чтоб не создавать лишних объектов
        if (this.head == null)
            return "null";

        StringBuilder b = new StringBuilder();
        for (Node node = this.head; node != null; node = node.next) {
            b.append(node.value).append(" -> ");
        }
        return b.append("null").toString();
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
