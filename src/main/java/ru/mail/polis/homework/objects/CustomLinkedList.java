package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
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
    private int modCount = 0;

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
            last.setNext(new Node(value));
            last = last.next;
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
        checkElementIndex(index);

        return getNode(index).value;
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
        if (i < 0 || i > size) {    // Можно добавить в конец массива, поэтому checkElementIndex() не подходит
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        if (i == size) {
            add(value);
            return;
        }

        if (i == 0) {
            Node newNode = new Node(value);
            newNode.setNext(head);
            head = newNode;
        } else {
            Node node = getNode(i - 1);
            Node nextNode = node.next;
            node.setNext(new Node(value));
            node.next.setNext(nextNode);
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
        checkElementIndex(index);

        if (index == 0) {
            head = head.next;
        } else {
            Node previousNode = getNode(index - 1);
            previousNode.setNext(previousNode.next.next);

            if (index == size - 1) {
                last = previousNode;
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
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node previousNode = null;
        Node currentNode = head;
        Node nextNode;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        last = head;
        head = previousNode;
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
        StringBuilder resultString = new StringBuilder();
        Node currentNode = head;

        while (currentNode != null) {
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
        return new CustomLinkedListIterator();
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
    }

    private Node getNode(int index) {
        Node currentNode = head;
        for (int i = 0; i != index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    private static class Node {

        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private class CustomLinkedListIterator implements Iterator<Integer> {

        private int position;
        private Node currentNode = head;
        private final int fixedModCount = modCount;

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public Integer next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (position >= size) {
                throw new NoSuchElementException();
            }

            int value = currentNode.value;
            currentNode = currentNode.next;
            position++;

            return value;
        }
    }
}
