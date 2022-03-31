package ru.mail.polis.homework.objects;

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node tail;
    private int size = 0;

    public void indexCheck(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    private Node findNode(int index) {
        indexCheck(index);
        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }


    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if (head == null) {
            head = new Node(value);
            tail = head;
        } else {
            tail.setNext(new Node(value));
            tail = tail.next;
        }
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        return findNode(index).value;
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
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }
        if (i == size) {
            add(value);
            return;
        }
        Node currentNode = new Node(value);
        size++;
        if (i == 0) {
            currentNode.setNext(head);
            head = currentNode;
            return;
        }
        Node previousNode = findNode(i - 1);
        currentNode.setNext(previousNode.next);
        previousNode.setNext(currentNode);

    }


    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        indexCheck(index);

        if (index == 0) {
            head = head.next;
            size -= 1;
            return;
        }
        Node currentNode = findNode(index - 1);
        currentNode.next = currentNode.next.next;
        size -= 1;
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
        Node newNode = new Node(currentNode.value);
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            Node tmpNode = new Node(currentNode.value);
            tmpNode.next = newNode;
            newNode = tmpNode;
        }
        head = newNode;
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
            Node lastReturn = head;
            private Integer nodeValue;

            @Override
            public boolean hasNext() {
                return lastReturn != null;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                nodeValue = lastReturn.value;
                lastReturn = lastReturn.next;
                return nodeValue;
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
