package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        tail.setNext(null);
    }

    private Node searchSpecificNode(int index) {
        Node necessaryNode = head;
        for (int t = 0; t < index; ++t) {
            necessaryNode = necessaryNode.next;
        }
        return necessaryNode;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        return this.searchSpecificNode(index).value;
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
        if (i > size || i < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(i));
        }
        if (i == size) {
            add(value);
            return;
        }
        Node newNode = new CustomLinkedList.Node(value);
        if (i == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node preNode = searchSpecificNode(i - 1);
            Node postNode = preNode.next;
            preNode.setNext(newNode);
            newNode.setNext(postNode);
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
        if (size == 0 || index >= size || index < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        size--;
        if (index == 0) {
            head = head.next;
            return;
        }
        if (index == size - 1) {
            tail = null;
        } else {
            Node preNode = searchSpecificNode(index - 1);
            preNode.setNext(preNode.next.next);
        }
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
        int[] allValues = new int[size];
        Node currentNode = head;
        for (int i = 0; i < size; ++i) {
            allValues[i] = currentNode.value;
            currentNode = currentNode.next;
        }
        currentNode = head;
        for (int i = allValues.length - 1; i >= 0; --i) {
            currentNode.value = allValues[i];
            currentNode = currentNode.next;
        }
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
        if (size == 0) {
            return "null";
        }
        Node currentNode = head;
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            resultString.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        resultString.append("null");
        return resultString.toString();
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
            Node node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Integer value = node.value;
                node = node.next;
                return value;
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
