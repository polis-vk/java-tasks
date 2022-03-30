package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
        Node currNode = head;
        while (currNode.next != null) {
            currNode = currNode.next;
        }
        currNode.setNext(newNode);
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (size <= index || index < 0) {
            throw  new IndexOutOfBoundsException(index);
        }
        Node currNode = head;
        int currIndex = 0;
        while (currIndex < index) {
            currNode = currNode.next;
            currIndex++;
        }
       return currNode.value;
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
        if (size < i || i < 0) {
            throw  new IndexOutOfBoundsException(i);
        }
        Node newNode = new Node(value);
        Node currNode = head;
        Node prevNode = null;
        int currIndex = 0;
        while (currIndex < i) {
            prevNode = currNode;
            currNode = currNode.next;
            currIndex++;
        }
        newNode.setNext(currNode);
        if (prevNode != null) {
            prevNode.setNext(newNode);
        } else {
            head = newNode;
        }
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
        if (size <= index || index < 0) {
            throw  new IndexOutOfBoundsException(index);
        }
        Node currNode = head;
        int currIndex = 0;
        Node prevNode = null;
        while (currIndex < index) {
            prevNode = currNode;
            currNode = currNode.next;
            currIndex++;
        }
        if (prevNode != null) {
            prevNode.setNext(currNode.next);
        } else {
            head = currNode.next;
        }
        size--;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     *  После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node prev = null;
        Node curr = head;
        Node next;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
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
        StringBuilder stringBuilder = new StringBuilder();
        Node currNode = head;
        if (currNode != null) {
            stringBuilder.append(currNode.value + " -> ");
            while (currNode.next != null) {
                currNode = currNode.next;
                stringBuilder.append(currNode.value + " -> ");
            }
        }

        stringBuilder.append("null");
        return stringBuilder.toString();
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
            Node curr = head;
            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    int data = curr.value;
                    curr = curr.next;
                    return data;
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
