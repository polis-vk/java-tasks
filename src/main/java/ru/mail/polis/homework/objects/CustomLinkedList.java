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
        if (head == null){
            head = new Node(value);
        } else {
            Node current = head;
            while (current.next != null){
                current = current.next;
            }
            current.next = new Node(value);
        }
        size ++;
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if (index > size || index < 0){
            throw new IndexOutOfBoundsException();
        }

        Node previous = head;
        Node current = head;
        if (index == 0){
            head = null;
            size --;
        }
        for (int i = 0; current != null && i != index; i++ ){
            previous = current;
            current = current.next;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        previous.setNext(current.next);
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node current = head;
        Node previous = null;
        while (current != null){
            Node temp = current.next;
            current.setNext(previous);
            previous = current;
            current = temp;
        }
        head = previous;
    }

    /**
     * Метод выводит всю последовательность хранящуюся в списке начиная с head.
     * Формат вывода:
     *  - значение каждой Node должно разделяться " -> "
     *  - последовательность всегда заканчивается на null
     *  - если в списке нет элементов - верните строку "null"
     *  return "1 -> 2 -> 3 -> null";
     *
     * @return - String with description all list
     */
    @Override
    public String toString() {
        if (head == null){
            return "null";
        }
        String result = "";
        Node current = head;
        for (int i = 0; current != null; i++ ){
            result =  result + current.value + " -> ";
            current = current.next;
        }
        return result = result + "null";
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
