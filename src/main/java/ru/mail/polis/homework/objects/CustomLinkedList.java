package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private static final String TO_STRING_SEPARATOR = " -> ";

    private Node head;
    private Node tail;
    private int size;
    private int modCount;

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
        if (tail == null) {
            // Then head is also null
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
        size++;
        modCount++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        checkIndexInclusive(index);
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
        checkIndexExclusive(i);

        if (i == 0) {
            Node newNode = new Node(value);
            newNode.next = head;
            head = newNode;
        } else {
            int index = 1;
            Node current = head;
            Node next = head.next;

            while (index < i) {
                current = next;
                next = next.next;
                index++;
            }

            current.next = new Node(value);
            current.next.next = next;

            if (next == null) {
                tail = current.next;
            }
        }

        size++;
        modCount++;
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
        checkIndexInclusive(index);

        if (index == 0) {
            head = head.next;
        } else {
            int i = 1;
            Node current = head;

            while (i < index) {
                current = current.next;
                i++;
            }

            if (index != size - 1) {
                current.next = current.next.next;
            } else {
                current.next = null;
                tail = current;
            }
        }

        size--;
        modCount++;
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
        if (size <= 1) {
            return;
        }

        Node current = head.next;
        Node temp;

        tail = head;
        tail.next = null;
        Node lastToLink = tail;

        while (current.next != null) {
            temp = current.next;

            current.next = lastToLink;
            lastToLink = current;

            current = temp;
        }

        head = current;
        head.next = lastToLink;
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
        StringBuilder sb = new StringBuilder();

        Node current = head;

        while (current != null) {
            sb.append(current.value).append(TO_STRING_SEPARATOR);
            current = current.next;
        }

        return sb.append("null").toString();
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
            private final int fixedModCount = modCount;
            private Node current = head;

            @Override
            public boolean hasNext() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException("You can't modify list while iterating!");
                }
                return current != null;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    int value = current.value;
                    current = current.next;
                    return value;
                }
                throw new NoSuchElementException("No more elements left in the list");
            }
        };
    }

    private static class Node {
        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

    private void checkIndexExclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of size of the array!");
        }
    }

    private void checkIndexInclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of size of the array!");
        }
    }

    private Node getNode(int index) {
        int i = 0;
        Node current = head;

        while (i < index) {
            current = current.next;
            i++;
        }

        return current;
    }
}
