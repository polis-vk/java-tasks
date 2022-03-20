package ru.mail.polis.homework.objects;

import java.util.Iterator;

/**
 * 13 тугриков
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node firstNode;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        int sizeOfList = 0;
        Node currentNode = firstNode;
        while (currentNode != null) {
            sizeOfList += 1;
            currentNode = currentNode.next;
        }
        return sizeOfList;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            firstNode = node;
        }
        head.setNext(node);
        head = node;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index < 0 || size() <= index) {
            throw  new IndexOutOfBoundsException(index);
        }
        int currentPosition = 0;
        Node currentNode = firstNode;
        while (currentNode != null) {
            if (currentPosition == index) {
                return currentNode.value;
            }
            currentPosition += 1;
            currentNode = currentNode.next;
        }
        return 0;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список на заданную позицию.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param i     - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) {
        if (i < 0 || size() < i) {
            throw new IndexOutOfBoundsException(i);
        }
        int currentPosition = 0;
        Node currentNode = firstNode;
        if (i == 0) {
            Node node = new Node(value);
            node.next = firstNode;
            firstNode = node;
        }
        while (currentNode != null) {
            if (currentPosition == i - 1) {
                Node node = new Node(value);
                node.next = currentNode.next;
                currentNode.next = node;
                break;
            }
            currentPosition += 1;
            currentNode = currentNode.next;
        }
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

    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {

    }

    /**
     * 1 тугрик
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
        StringBuilder resultList = new StringBuilder();
        if (firstNode == null) {
            return resultList.append("null").toString();
        }
        Node currentNode = firstNode;
        resultList.append(currentNode.value).append(" -> ");
        currentNode = currentNode.next;
        while (currentNode != null) {
            resultList.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        resultList.append("null");
        return resultList.toString();
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
