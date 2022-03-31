package ru.mail.polis.homework.objects;

import java.util.Iterator;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private int size = 0;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        getLastNode().setNext(newNode);
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node node = head;
        int currentIndex = 0;
        while (node.next != null) {
            if (currentIndex == index) {
                break;
            }

            node = node.next;
            currentIndex++;
        }

        return node.value;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список на заданную позицию.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param i - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) {
        if (i > size || i < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(value);
        if (i - 1 >= 0) {
            Node node = head;
            int currentIndex = 0;
            while (node.next != null) {
                if (currentIndex == i - 1) {
                    break;
                }

                node = node.next;
                currentIndex++;
            }

            newNode.setNext(node.next);
            node.setNext(newNode);
            size++;
            return;
        }

        newNode.setNext(head);
        head = newNode;
        size++;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index >= size || index < 0 || head == null) {
            throw new IndexOutOfBoundsException();
        }

        if (index - 1 >= 0) {
            Node node = head;
            int currentIndex = 0;
            while (node.next != null) {
                if (currentIndex == index - 1) {
                    break;
                }

                node = node.next;
                currentIndex++;
            }

            node.setNext(node.next.next);
            size--;
            return;
        }

        head = head.next;
        size--;
    }

    private Node getLastNode() {
        Node node = head;
        while (node.next != null) {
            node = node.next;
        }

        return node;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {

    }

    /**
     * 1 тугрик
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

        StringBuilder createListString = new StringBuilder();
        Node node = head;
        while (node.next != null) {
            createListString.append(node.value + " -> ");
            node = node.next;
        }
        createListString.append(node.value + " -> null");

        return createListString.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return null;
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
