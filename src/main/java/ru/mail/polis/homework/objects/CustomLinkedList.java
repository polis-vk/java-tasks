package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {
    private Node root = null;
    private Node head = null;
    int size = 0;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязны список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        Node node = new Node(value);
        if(root == null) root = node;
        if(head == null) head = root;
        else{
            head.setNext(node);
            head = node;
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
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if(index == 0) root = root.next;
        Node node = root;
        for(int i = 1; i < index; i++){
            node = node.next;
        }
        node.next = node.next.next;
        size--;
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        Node current = root;
        Node next = current.next;
        while(next != null){
            if(current == root){
                current.next = null;
                head = current;
            }
            current = next;
            next = current.next;
        }
    }

    /**
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
        StringBuilder sb = new StringBuilder();
        Node head = root;
        for (int i = 0; i < size; i++){
            sb.append(head.value).append(" -> ");
            head = head.next;
        }

        return sb.append("null").toString();
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
