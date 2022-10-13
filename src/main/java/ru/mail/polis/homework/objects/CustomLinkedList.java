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
        if (head == null) {
            head = new Node(value);
            tail = head;
        } else {
            tail.setNext(new Node(value));
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
        checkIndex(index);

        if (index == size - 1) {
            return tail.value;
        }

        int currentPosition = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (currentPosition == index) {
                break;
            }
            currentNode = currentNode.next;
            currentPosition++;
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
     * @param i     - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) {
        Node currentNode = head;
        int currentPosition = 0;

        if (i == 0) {
            head = new Node(value);
            head.setNext(currentNode);
        } else if (i == size) {
            tail.setNext(new Node(value));
            tail = tail.next;
        } else if (i < size && i > 0) {
            while (currentNode != null) {
                if (currentPosition == i - 1) {
                    Node temp = currentNode.next;
                    currentNode.setNext(new Node(value));
                    currentNode.next.next = temp;
                    break;
                }
                currentNode = currentNode.next;
                currentPosition++;
            }
        } else {
            throw new IndexOutOfBoundsException(i);
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
        checkIndex(index);
        if (size == 0) {
            return;
        }

        if (index == 0) {
            head = head.next;
        } else {
            Node currentNode = head;
            int counter = 0;
            while (currentNode != null) {
                if (counter == index - 1) {
                    currentNode.next = currentNode.next.next;
                    break;
                }
                currentNode = currentNode.next;
                counter++;
            }
            if (index == size - 1) {
                tail = currentNode;
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
     */ // 1 2 3 4 5 6 7
    // 2 1 3 4 5 6 7
    //
    public void revertList() {
        Node secondNewHead = null;
        Node currentNode = null;
        tail = head;

        while (head != null) {
            currentNode = head;
            head = head.next;
            currentNode.setNext(secondNewHead);
            secondNewHead = currentNode;
        }

        head = secondNewHead;
        modCount++;
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
        if (size == 0) {
            return "null";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            Node currentNode = head;
            while (currentNode != null) {
                stringBuilder.append(currentNode.value + " -> ");
                currentNode = currentNode.next;
            }
            return stringBuilder.append("null").toString();
        }
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
            private int fixedModCount = modCount;
            private Node position = head;

            @Override
            public boolean hasNext() {
                return (position != null);
            }

            @Override
            public Integer next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                if (position == null) {
                    throw new NoSuchElementException();
                }
                int digitValue = position.value;
                position = position.next;
                return digitValue;
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
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
