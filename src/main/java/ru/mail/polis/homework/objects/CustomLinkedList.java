package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;
    private int size = 0;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node addedNode = new Node(value);
        if (head == null) {
            head = addedNode;
        } else if (head.next == null) {
            head.setNext(addedNode);
        } else {
            Node currentNode = head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.setNext(addedNode);
        }
        size++;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        int i = 0;
        Node currentNode = head;
        if (index == 0) {
            head = null;
            size--;
        }
        while (currentNode.next != null) {
            if (index - 1 == i) {
                currentNode.next = currentNode.next.next;
                size--;
                break;
            }
            currentNode = currentNode.next;
            i++;
        }
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * <p>
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node currentNode = head;
        Node tail = null;

        if (head == null) {
            return;
        }
        if (head.next == null) {
            return;
        }
        while (currentNode != null) {
            Node prev = new Node(currentNode.value);
            prev.setNext(tail);
            tail = prev;
            if (currentNode.next == null) {
                head = prev;
            }
            currentNode = currentNode.next;
        }
    }

    /**
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
        if (head == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Node node = head;
        while (node != null) {
            sb.append(node.value);
            sb.append(" -> ");
            if (node.next == null) {
                sb.append("null");
            }
            node = node.next;
        }
        return sb.toString();
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
}
