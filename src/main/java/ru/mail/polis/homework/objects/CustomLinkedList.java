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
        if (head == null) {
            head = new Node(value);
        } else {
            Node lastNode = head;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            lastNode.next = new Node(value);
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        checkIndex(index);
        if (index == 0) {
            head = head.next;
            return;
        }

        int currentIndex = 0;
        Node nodePreviousToOneWeDeleting = head;
        while (currentIndex < index - 1) {
            nodePreviousToOneWeDeleting = nodePreviousToOneWeDeleting.next;
            currentIndex++;
        }
        nodePreviousToOneWeDeleting.setNext(nodePreviousToOneWeDeleting.next.next);

        size--;
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (head == null || head.next == null) {
            return;
        }

        Node previousNode = null;
        for (Node currentNode = head; currentNode != null; ) {
            Node nextNode = currentNode.next;
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        head = previousNode;
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
        Node currentNode = head;
        StringBuilder result = new StringBuilder();
        while (currentNode != null) {
            result.append(currentNode.value).append(" -> ");
            currentNode = currentNode.next;
        }
        return result.append("null").toString();
    }

    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
