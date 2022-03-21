package ru.mail.polis.homework.objects;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 13 тугриков
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;
    private Node lastNode;
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
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        }else {
            lastNode.setNext(newNode);
        }

        lastNode = newNode;
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
            throw new IndexOutOfBoundsException();
        }

        return getNodeByIndex(index).value;
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
            throw new IndexOutOfBoundsException();
        }

        Node getNodeByIndex = getNodeByIndex(i - 1);
        if (getNodeByIndex == null) {
            Node newNode = new Node(value);
            newNode.setNext(head);
            head = newNode;
            size++;
            return;
        }
        if(i == size){
            add(value);
            return;
        }

        Node newNode= new Node(value);
        newNode.setNext(getNodeByIndex.next);
        getNodeByIndex.setNext(newNode);
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
        if (index >= size || index < 0 || head == null) {
            throw new IndexOutOfBoundsException();
        }

        Node beforeNode = getNodeByIndex(index - 1);
        if (beforeNode == null) {
            head = head.next;
            size--;
            return;
        }

        Node nextNode = beforeNode.next.next;
        beforeNode.setNext(nextNode);
        size--;

        if (size - 1 == index) {
            lastNode = beforeNode;
        }
    }

    private Node getNodeByIndex(int index) {
        if(index < 0){
            return null;
        }

        Node nextNode = head;
        int count = 0;
        while (nextNode.next != null) {
            if(count == index){
                break;
            }

            nextNode = nextNode.next;
            count++;
        }

        return nextNode;
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
        Node beforeNode = null;
        Node currentNode = head;
        Node nextNode;
        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = beforeNode;
            beforeNode = currentNode;
            currentNode = nextNode;
        }
        head = beforeNode;
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
        if (head == null) {
            return "null";
        }

        StringBuilder stringBuilder = new StringBuilder();
        Node nextNode = head;
        while (nextNode.next != null) {
            stringBuilder.append(nextNode.value).append(" -> ");
            nextNode = nextNode.next;
        }
        stringBuilder.append(nextNode.value).append(" -> null");

        return stringBuilder.toString();
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

    private class CustomLinkedListIterator implements Iterator<Integer> {
        private Node lastNode = head;

        @Override
        public boolean hasNext() {
            return lastNode != null;
        }

        @Override
        public Integer next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }

            int result = lastNode.value;
            lastNode = lastNode.next;
            return result;
        }
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
