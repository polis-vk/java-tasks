package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */


public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node tail;
    private int size;

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
        if (head == null) {
            head = new Node(value);
            tail = head;
        } else {
            Node temp = new Node(value);
            tail.setNext(temp);
            tail = temp;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
    }

    private Node getNode(int index) {
        checkIndex(index);
        if (index == size - 1) {
            return tail;
        }
        Node currentNode = head;
        for (int currentIndex = 0; currentIndex < index; currentIndex++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        return getNode(index).value;
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
            throw new IndexOutOfBoundsException(Integer.toString(i));
        }
        Node newNode = new Node(value);
        if (i == 0) {
            newNode.setNext(head);
            head = newNode;
        } else if (i == size) {
            tail.setNext(newNode);
            tail = newNode;
        } else {
            Node prevForNew = getNode(i - 1);
            newNode.setNext(prevForNew.next);
            prevForNew.setNext(newNode);
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
        checkIndex(index);
        if (index == 0) {
            head = head.next;
        } else if (index == size - 1) {
            Node prevForTail = getNode(size - 2);
            prevForTail.setNext(null);
            tail = prevForTail;
        } else {
            getNode(index - 1).setNext(getNode(index + 1));
        }
        size--;
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
        Node currentNode = head;
        Node nextNode = currentNode.next;
        Node nextNextNode = nextNode.next;
        currentNode.setNext(null);
        while (nextNextNode != null) {
            nextNode.setNext(currentNode);
            currentNode = nextNode;
            nextNode = nextNextNode;
            nextNextNode = nextNextNode.next;
        }
        nextNode.setNext(currentNode);
        tail = head;
        head = nextNode;
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
        StringBuilder stringBuilder = new StringBuilder();
        for (Node currentNode = head; currentNode != null; currentNode = currentNode.next) {
            stringBuilder.append(currentNode.value).append(" -> ");
        }
        return stringBuilder.append("null").toString();
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
            private Node currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    int value = currentNode.value;
                    currentNode = currentNode.next;
                    return value;
                } else {
                    throw new NoSuchElementException();
                }
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
