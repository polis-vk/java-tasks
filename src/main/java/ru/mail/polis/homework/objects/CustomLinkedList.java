package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private int size;
    private Node first;
    private Node last;

    public CustomLinkedList() {
        this.size = 0;
    }

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
        final Node lastElem = last;
        final Node newNode = new Node(value, null);
        last = newNode;
        if (lastElem == null) {
            first = newNode;
        } else {
            lastElem.next = newNode;
        }
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index - index what element to get
     */
    public int get(int index) {
        Node currentNode = first;
        for (int i = 0; i < index; i++) {
            if (currentNode.next != null) {
                currentNode = currentNode.next;
            } else throw new IndexOutOfBoundsException(index);
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
     * @param index - index
     * @param value - data for create Node.
     */
    public void add(int index, int value) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        if (index == 0) {
            first = new Node(value, first);
        } else if (index == size) {
            add(value);
            size--;
        } else {
                Node currentNode = first;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                Node temp = currentNode.next;
                currentNode.next = new Node(value, temp);
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
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
        if (index == 0) {
            first = first.next;
        } else {
            Node currentNode = first;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }
            if (index != size() - 1) {
                currentNode.next = currentNode.next.next;
            } else {
                currentNode.next = null;
                last = currentNode;
            }
        }
        size--;
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
        Node newFirst = last;
        Node currentNode = last;
        for (int i = size() - 2; i >= 0; i--) {
            currentNode.next = new Node(get(i), null);
            if (i == 0) {
                last = currentNode.next;
            }
            currentNode = currentNode.next;
        }
        first = newFirst;
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
        if (size() == 0) {
            return "null";
        }
        Node currentNode = first;
        StringBuilder string = new StringBuilder();
        while (currentNode != last) {
            string.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        string.append(currentNode.value).append(" -> null");
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

            private final int initialSize = size();
            private Integer index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Integer next() {
                if (initialSize != size()) {
                    throw new ConcurrentModificationException();
                }
                if (hasNext()) {
                    int node = get(index);
                    index++;
                    return node;
                } else throw new NoSuchElementException();
            }
        };
    }

    private static class Node {

        private final int value;
        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

    }
}
