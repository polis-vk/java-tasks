package ru.mail.polis.homework.objects;

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        int size = 0;
        Node tmpNode = head;
        while (tmpNode.next != null) {
            size += 1;
            tmpNode = tmpNode.next;
        }
        return size + 1;
    }

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (isEmpty()) {
            head = new Node(value);
            return;
        }
        Node currentNode = head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
        currentNode.next = new Node(value);
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.value;
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
        if (i > size() || i < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }
        if (i == 0) {
            Node newNode = new Node(value);
            newNode.setNext(head);
            head = newNode;
        } else {
            Node currentNode = head;
            for (int j = 0; j < i - 1; j++) {
                currentNode = currentNode.next;
            }
            Node temp = currentNode.next;
            Node newNode = new Node(value);

            currentNode.setNext(newNode);
            newNode.setNext(temp);
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
        if (isEmpty() || index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        if (index == 0) {
            head = head.next;
            return;
        }
        Node curNode = head;
        for (int i = 0; i != index - 1; i++) {
            curNode = curNode.next;
        }
        curNode.next = curNode.next.next;

    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (isEmpty()) {
            return;
        }
        Node currentNode = head;
        Node NewNode = new Node(currentNode.value);
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            Node tmpNode = new Node(currentNode.value);
            tmpNode.next = NewNode;
            NewNode = tmpNode;
        }
        head = NewNode;

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
        if (isEmpty()) {
            return ("null");
        }
        StringBuilder strNode = new StringBuilder();
        Node currentNode = head;
        while (currentNode != null) {
            strNode.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        strNode.append("null");
        return strNode.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node currentNode = head;
            Integer NodeValue;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                NodeValue = currentNode.value;
                currentNode = currentNode.next;
                return NodeValue;
            }
        };
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
