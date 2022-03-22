package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 13 тугриков
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        int iterator = 0;
        Node curNode = head;
        while (curNode != null) {
            curNode = curNode.next;
            iterator++;
        }
        return iterator;
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
        } else {
            Node curNode = head;
            while (curNode.next != null) {
                curNode = curNode.next;
            }
            curNode.next = new Node(value);
        }
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
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
        Node curNode = head;
        if (i == 0) {
            head = new Node(value);
            head.next = curNode;
        } else {
            int iterator = 0;
            while (curNode != null) {
                if (iterator == i - 1) {
                    break;
                }
                curNode = curNode.next;
                iterator++;
            }
            if (curNode == null) {
                throw new IndexOutOfBoundsException(String.valueOf(i));
            }
            Node bufNode = curNode.next;
            curNode.next = new Node(value);
            curNode.next.next = bufNode;
        }
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
        Node curNode = head;
        if (index == 0 && head != null) {
            head = head.next;
        } else {
            int iterator = 0;
            while (curNode != null) {
                if (iterator == index - 1) {
                    break;
                }
                curNode = curNode.next;
                iterator++;
            }
            if (curNode == null || curNode.next == null) {
                throw new IndexOutOfBoundsException(String.valueOf(index));
            }
            curNode.next = curNode.next.next;
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
        if (head != null) {
            Node curNode = head;
            Node curNewNode = new Node(curNode.value);
            while (curNode.next != null) {
                curNode = curNode.next;
                Node bufNewNode = new Node(curNode.value);
                bufNewNode.next = curNewNode;
                curNewNode = bufNewNode;
            }
            head = curNewNode;
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
        StringBuilder res = new StringBuilder();
        Node curNode = head;
        while (curNode != null) {
            res.append(curNode.value).append(" -> ");
            curNode = curNode.next;
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
            Node curNode = head;

            @Override
            public boolean hasNext() {
                return curNode != null;
            }

            @Override
            public Integer next() {
                if (curNode == null) {
                    throw new NoSuchElementException();
                }
                int res = curNode.value;
                curNode = curNode.next;
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
