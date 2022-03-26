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

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {

        if (head == null) {
            return 0;
        }

        int size = 1;
        Node nextNode = head.next;

        while (nextNode != null) {
            size++;
            nextNode = nextNode.next;
        }

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

        Node currentNode = head;
        Node nextNode = new Node(value);
        nextNode.setNext(null);

        if (head == null) {
            head = nextNode;
        } else {
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.setNext(nextNode);
        }
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        if (index > this.size() - 1) {
            throw new IndexOutOfBoundsException();
        }

        int indexOfCurrentNode = 0;
        Node currentNode = head;

        while(true) {
            if (indexOfCurrentNode == index) {
                return currentNode.value;
            }
            indexOfCurrentNode++;
            currentNode = currentNode.next;
        }
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
        Node currentNode = head;
        Node newNode = new Node(value);
        int nodeIndex = 0;

        if (i == 0) {
            newNode.setNext(head);
            head = newNode;
            return;
        }

        while (nodeIndex <= i) {
            if (i > this.size()) {
                throw new IndexOutOfBoundsException();
            }

            if (nodeIndex + 1 == i) {
                Node tempNode = currentNode.next;
                currentNode.setNext(newNode);
                newNode.setNext(tempNode);
                break;
            }
            currentNode = currentNode.next;
            nodeIndex++;
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
        if (index > this.size() - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            head = head.next;
            return;
        }

        Node currentNode = head;
        Node nextNode = head.next;
        int indexOfNode = 0;

        while (indexOfNode != index) {
            if (indexOfNode + 1 == index) {
                currentNode.setNext(nextNode.next);
                break;
            }
            currentNode = currentNode.next;
            nextNode = nextNode.next;
            indexOfNode++;
        }


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
        if (head == null) {
            return;
        }

        CustomLinkedList revertLinkedList = new CustomLinkedList();
        Stack<Integer> stack = new Stack<Integer>();
        Node currentNode = head;


        while(currentNode != null) {
            stack.push(currentNode.value);
            currentNode = currentNode.next;
        }

        head = new Node(stack.pop());
        currentNode = head;
        while(!stack.empty()) {
            currentNode.setNext(new Node(stack.pop()));
            currentNode = currentNode.next;
        }
        currentNode.setNext(null);
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

        StringBuilder result = new StringBuilder("");
        Node currentNode = head;

        while (currentNode != null) {
            result.append(currentNode.value);
            result.append(" -> ");
            currentNode = currentNode.next;
        }

        return result.append("null").toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        Iterator<Integer> iterator = new Iterator<Integer>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Integer next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }

                Integer result = current.value;
                current = current.next;
                return result;
            }
        };
        return iterator;
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
