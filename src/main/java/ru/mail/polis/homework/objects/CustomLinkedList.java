package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node last;
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
            last = head;
        } else {
            Node newNode = new Node(value);
            last.setNext(newNode);
            last = newNode;
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
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        if (index == size() - 1) {
            return last.value;
        } else {
            int counter = 0;
            for (Integer integer : this) {
                if (counter == index) {
                    return integer;
                }
                counter++;
            }
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
        int counter = 0;
        Node currentNode = head;
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException(String.valueOf(i));
        } else if (i == 0) {
            head = new Node(value);
            head.next = currentNode;
        } else if (i == size()) {
            last.next = new Node(value);
            last = last.next;
        } else {
            while (currentNode != null) {
                counter++;
                if (counter == i) {
                    Node newNode = new Node(value);
                    newNode.next = currentNode.next;
                    currentNode.next = newNode;
                    break;
                }
                currentNode = currentNode.next;
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
        if (size() == 0) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        int counter = 0;
        Node currentNode = head;
        Node previousNode = null;
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        } else if (index == 0) {
            head = currentNode.next;
            size--;
        } else {
            while (currentNode != null) {
                if (counter == index) {
                    previousNode.next = currentNode.next;
                    size--;
                    if (index == size()) {
                        last = previousNode;
                    }
                    break;
                }
                previousNode = currentNode;
                currentNode = currentNode.next;
                counter++;
            }
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
        last = head;
        Node currentNode = head;
        Node previousNode = null;

        while (currentNode != null) {
            Node tempNode = currentNode.next;

            currentNode.setNext(previousNode);
            previousNode = currentNode;
            head = currentNode;

            currentNode = tempNode;
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
        if (size() == 0) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Node currentNode = head;
        while (currentNode.next != null) {
            sb.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        sb.append(currentNode.value).append(" -> ").append("null");
        return sb.toString();
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
                    Node returningNode = currentNode;
                    currentNode = currentNode.next;
                    return returningNode.value;
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
