package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;

    private Integer size = 0;

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
        add(size, value);
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        int i = 0;
        for (Integer integer : this) {
            if (i == index) {
                return integer;
            }
            i++;
        }
        throw new IndexOutOfBoundsException();
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
        if (i < 0 || i > size) { // Проверка корректности индекса
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }
        size++;
        if (i == 0) { // При нулевом индексе задаётся новая голова списка
            Node oldHead = head;
            head = new Node(value);
            head.setNext(oldHead);
            return;
        }
        int counter = 0; // Иначе итерируемся, пока не доберёмся до требуемого индекса
        Node current = head;
        Node previous = null;
        while (counter < i) {
            previous = current;
            current = current.next;
            counter++;
        }
        previous.setNext(new Node(value));
        previous.next.setNext(current);
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
        if (index < 0 || index >= size) { // Проверка корректности индекса
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        size--;
        if (index == 0) { // При нулевом индексе задаётся новая голова списка
            head = head.next;
            return;
        }
        int counter = 0; // Иначе итерируемся, пока не доберёмся до требуемого индекса
        Node current = head;
        Node previous = null;
        while (counter < index) {
            previous = current;
            current = current.next;
            counter++;
        }
        previous.setNext(current.next);
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
        if (size == 0) { // При пустом списке делать нечего
            return;
        }
        Node current = head;
        int i = 0;
        while (current.next != null) {
            add(0, current.next.value);
            removeElement(i + 2);
            i++;
        }
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
        StringBuilder ans = new StringBuilder();
        for (Integer integer : this) {
            ans.append(integer).append(" -> ");
        }
        return ans + "null";
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

            Node next = head;

            @Override
            public boolean hasNext() {
                return (next != null);
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                else {
                    Integer ans = next.value;
                    next = next.next;
                    return ans;
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
