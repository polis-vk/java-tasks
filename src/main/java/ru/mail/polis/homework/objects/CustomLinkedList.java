package ru.mail.polis.homework.objects;

/**
 * Реализовать все методы односвязанного списка.
 */
public class CustomLinkedList {

    private Node head;

    /**
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        if(this.head == null) {
            this.head = new Node(value);
            return;
        }
        
        Node toAdd = this.head;
        while(toAdd.next != null) {
            toAdd = toAdd.next;
        }
        
        toAdd.next = new Node(value);
    }

    /**
     * Реализовать метод:
     * Удаляет элемент в указанной позиции, при это связывая его соседние элементы друг с другом.
     * Если был передан невалидный index - надо выкинуть исключение IndexOutOfBoundsException.
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {
        if(this.head == null) {
            throw new IndexOutOfBoundsException("Empty list");
        }
        
        if(index == 0) {
            if(this.head == null) {
                throw new IndexOutOfBoundsException("No element on given position");
            }
            this.head = this.head.next;
            return;
        }
        
        Node toRemove = this.head;
        
        for(int i = 0; i < index - 1; ++i) {
            if(toRemove.next == null)
                throw new IndexOutOfBoundsException("No element on given position");
            toRemove = toRemove.next;
        }
        
        if(toRemove.next == null) {
            throw new IndexOutOfBoundsException("No element on given position");
        }
        
        toRemove.next = toRemove.next.next;      
        
    }

    /**
     * Реализовать метод:
     * Переварачивает все элементы списка.
     * Пример:
     *  Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     *  После исполнения метода последовательность должа быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        if(this.head == null || this.head.next == null) {
            return;
        }
        
        int length = 1;
        Node current = this.head;
        while(current.next != null) {
            current = current.next;
            ++length;
        }
        
        Node first;
        Node last;
        int temp;
        for(int i = 0; i < length / 2; ++i) {
            
            first = this.head;
            for(int j = 0; j < i; ++j)
                first = first.next;
            last = first;
            for(int j = i << 1; j < length - 1; ++j) {
                last = last.next;
            }
            temp = first.value;
            first.value = last.value;
            last.value = temp;
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
        if(this.head == null)
            return "null";
        
        Node node = this.head;
        StringBuilder b = new StringBuilder();
        b.append(node.value);
        b.append(" -> ");
        while(node.next != null) {
            node = node.next;
            b.append(node.value);
            b.append(" -> ");
        }
        return b.append("null").toString();
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
