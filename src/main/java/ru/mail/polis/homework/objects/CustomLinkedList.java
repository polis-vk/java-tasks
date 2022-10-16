package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {
    private int modCount = 0;
    private Node head;
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
        if(head == null) {
            head = new Node(value);
            size++;
            return;
        }
        Node currentNode = head;
        while(currentNode.next != null) {
            currentNode = currentNode.next;
        }
        currentNode.next = new Node(value);
        size++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index
     */
    public int get(int index) {
        Node currentNode = head;
       if(index >= size || index < 0) {
           throw new IndexOutOfBoundsException();
        } else {
           for(int i = 0; i < index; i++) {
               currentNode = currentNode.next;
           }
           return currentNode.value;
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
        if(i >= size || i < 0) {
            if(i == size) {
                add(value);
                return;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        } else {
            if(i == 0) {
                head = new Node(value);
                head.setNext(currentNode);
                size++;
                return;
            }
            for(int j = 0; j < i - 1; j++) {
                currentNode = currentNode.next;
            }
            Node newNode = new Node(value);
            newNode.setNext(currentNode.next);
            currentNode.setNext(newNode);
            size++;
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
        Node currentNode = head;
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if(index == 0) {
                head = currentNode.next;
                size--;
                return;
            }
            for(int j = 0; j < index - 1; j++) {
                currentNode = currentNode.next;
            }
            currentNode.setNext(currentNode.next.next);
            size--;
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
        Node newHead = new Node(get(size - 1));
        Node currentNode = newHead;
        for(int i = size - 2; i >= 0; i--) {
            currentNode.setNext(new Node(get(i)));
            currentNode = currentNode.next;
        }
        head = newHead;
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
        if(size == 0) {
            return "null";
        }
        StringBuilder result = new StringBuilder();
        result.append(head.value);

        Node currentNode = head;
        while(currentNode.next != null) {
            currentNode = currentNode.next;
            result.append(" -> ");
            result.append(currentNode.value);
        }
        result.append(" -> ");
        result.append(currentNode.next);
        return result.toString();
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iter();
    }
    private class Iter implements Iterator<Integer> {
        int position;
        int fixedModCount = modCount;
        @Override
        public boolean hasNext() {
            return position < size;
        }
        @Override
        public Integer next() {
            if(fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if(position >= size) {
                throw new NoSuchElementException();
            }
            return get(position++);
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
