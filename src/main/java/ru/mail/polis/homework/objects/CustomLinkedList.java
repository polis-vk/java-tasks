package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node last;
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
        if (head == null) {
            head = new Node(value);
            last = head;
            size++;
            return;
        }
        last.next = new Node(value);
        last = last.next;
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
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        Node current = head;
        int iterator = 0;
        while (iterator < index) {
            current = current.next;
            iterator++;
        }
        return current.value;
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
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }
        Node current = head;
        if (i == 0) {
            head = new Node(value);
            head.next = current;
        } else {
            int iterator = 0;
            while (iterator < i - 1) {
                current = current.next;
                iterator++;
            }
            Node bufNode = current.next;
            current.next = new Node(value);
            current.next.next = bufNode;
            if (i == size - 1) {
                last = current.next;
            }
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
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        if (head.next == null) {
            head = null;
            last = null;
        } else if (index == 0) {
            head = head.next;
        } else {
            Node current = head;
            int iterator = 0;
            while (iterator < index - 1) {
                current = current.next;
                iterator++;
            }
            current.next = current.next.next;
            if (index == size - 1) {
                last = current;
            }
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
        if (head == null) {
            return;
        }
        Node current = head;
        Node currentNew = new Node(current.value);
        while (current.next != null) {
            current = current.next;
            Node bufNewNode = new Node(current.value);
            bufNewNode.next = currentNew;
            currentNew = bufNewNode;
        }
        head = currentNew;
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
        if (head == null) {
            return "null";
        }
        StringBuilder res = new StringBuilder();
        Node current = head;
        while (current != null) {
            res.append(current.value).append(" -> ");
            current = current.next;
        }
        res.append("null");
        return res.toString();
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
            Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int res = current.value;
                current = current.next;
                return res;
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
