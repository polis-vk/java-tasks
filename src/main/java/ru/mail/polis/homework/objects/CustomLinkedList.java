package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;

    public static void main(String[] args) {
        CustomLinkedList linkedList = new CustomLinkedList();

        try {
            linkedList.removeElement(0);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(ex);
        }
        System.out.println(linkedList);
        linkedList.revertList();
        System.out.println(linkedList);


        linkedList.add(1);
        linkedList.revertList();
        System.out.println(linkedList);

        linkedList.removeElement(0);
        System.out.println(linkedList);


        linkedList.add(2);
        linkedList.add(3);
        System.out.println(linkedList);

        linkedList.removeElement(0);
        System.out.println(linkedList);
        linkedList.add(10);
        System.out.println(linkedList);
        linkedList.removeElement(1);
        System.out.println(linkedList);

        linkedList.add(2);
        linkedList.add(9);
        System.out.println(linkedList);
        linkedList.revertList();
        System.out.println(linkedList);
    }

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node nodeToInsert = new Node(value);
        if (head == null) {
            head = nodeToInsert;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = nodeToInsert;
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
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node prev = head;
        Node current = head;
        for (int i = 0; current != null && i != index; i++) {
            prev = current;
            current = current.next;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head = head.next;
        }
        prev.setNext(current.next);
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if (head == null) {
            return;
        }
        Node current = head;
        Node prev = null;
        while (current != null) {
            Node tmp = current.next;
            current.setNext(prev);
            prev = current;
            current = tmp;
        }
        head = prev;
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
        StringBuilder builder = new StringBuilder();
        Node current = head;
        while (current != null) {
            builder.append(current.value + " -> ");
            current = current.next;
        }
        builder.append("null");
        return builder.toString();
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
