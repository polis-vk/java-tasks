package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private int length;


    private Node findNode(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }


    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        return length;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (length == 0) {
            head = new Node(value);
        } else {
            Node next = new Node(value);
            findNode(length - 1).setNext(next);
        }
        length++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index < length) {
            return findNode(index).value;
        }
        throw new IndexOutOfBoundsException(index);
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
        if (i > length || i < 0) {
            throw new IndexOutOfBoundsException(i);
        }

        if (i == 0) {
            Node newNode = new Node(value);
            newNode.setNext(head);
            head = newNode;
        } else {
            Node current = findNode(i - 1);
            Node temp = current.next;
            Node newNode = new Node(value);

            current.setNext(newNode);
            newNode.setNext(temp);
        }
        length++;
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
        if (index >= length || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == 0) {
            head = head.next;
        } else {
            Node current = findNode(index - 1);
            Node removing = current.next;
            Node temp = removing.next;
            current.setNext(temp);
        }
        length--;
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
        Node currentNode = head.next;
        Node previousNode = head;
        previousNode.setNext(null);
        Node tempNode;

        for (int i = 0; i < length - 1; i++) {
            tempNode = currentNode.next;
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = tempNode;
        }
        head = previousNode;
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
        if (length == 0) {
            return "null";
        }

        StringBuilder string = new StringBuilder();
        Node current = head;

        for (int i = 0; i < length; i++) {
            string.append(current.value);
            string.append(" -> ");
            current = current.next;
        }
        string.append("null");
        return string.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            public Integer next() {
                if (hasNext()) {
                    int value = current.value;
                    current = current.next;
                    return value;
                }
                throw new NoSuchElementException();
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
