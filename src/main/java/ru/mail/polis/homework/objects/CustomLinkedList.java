package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
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
        int cnt = 0;
        Node nowNode = head;
        while (nowNode != null) {
            nowNode = nowNode.next;
            cnt++;
        }
        return cnt;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node lastNode = getLastNode();
        if (lastNode == null) {
            head = new Node(value);
        } else {
            lastNode.setNext(new Node(value));
        }
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        return this.getNode(index).value;
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
        Node newNode = new Node(value);
        if (i == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node prevNode = getNode(i - 1);
            newNode.setNext(prevNode.next);
            prevNode.setNext(newNode);
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
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            if (head == null) {
                throw new IndexOutOfBoundsException();
            }
            head = head.next;
        } else {
            Node prevNode = getNode(index - 1);

            if (prevNode.next == null) {
                throw new IndexOutOfBoundsException();
            }

            prevNode.setNext(prevNode.next.next);
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
        Node nowNode = head;
        Node newNextNode = null;
        while (nowNode != null) {
            Node tmp = nowNode.next;
            nowNode.setNext(newNextNode);
            newNextNode = nowNode;
            nowNode = tmp;
        }
        head = newNextNode;
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
        StringBuilder ans = new StringBuilder();
        for (int i : this) {
            ans.append(i);
            ans.append(" -> ");
        }
        ans.append("null");
        return ans.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new CustomIterator(head);
    }

    private Node getLastNode() {
        if (head == null) {
            return null;
        }

        Node nowNode = head;

        while (nowNode.next != null) {
            nowNode = nowNode.next;
        }

        return nowNode;
    }

    private Node getNode(int id) {
        int cnt = 0;
        Node nowNode = head;
        while (cnt < id) {
            if (nowNode == null) {
                throw new IndexOutOfBoundsException();
            }
            nowNode = nowNode.next;
            cnt++;
        }
        if (nowNode == null) {
            throw new IndexOutOfBoundsException();
        }

        return nowNode;
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

    private static class CustomIterator implements Iterator<Integer> {
        Node pointer;

        public CustomIterator(Node head) {
            this.pointer = head;
        }

        @Override
        public boolean hasNext() {
            return this.pointer != null;
        }

        @Override
        public Integer next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            int ans = this.pointer.value;
            this.pointer = this.pointer.next;
            return ans;
        }
    }
}
