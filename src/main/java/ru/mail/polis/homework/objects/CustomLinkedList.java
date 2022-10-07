package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 15 тугриков
 * Реализовать все методы односвязного списка.
 */
public class CustomLinkedList implements Iterable<Integer> {
    /*
     * Если хранить не только голову, но и конец, то можно за константное время добавлять элементы в конец, не переби-
     * рая все элементы. То же самое касается размера.
     */
    private Node listHead;
    private Node listLastNode;
    private int modCount;
    private int listSize;

    /**
     * 1 тугрик
     * Возвращает количество элементов в списке
     *
     * @return size
     */
    public int size() {
        /*
         * Значение размера содержится в отдельном поле. Его и выводим, если хотим получить значение количества
         * элементов.
         */
        return listSize;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Добавляет элемент в односвязный список.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {
        /*
         * Если головы еще нет, то создадаим ее и скажем, что это так же и последнее звено. Далее, если голова уже есть,
         * то создаем следующие звенья.
         */
        if (listHead == null) {
            listHead = new Node(value);
            listLastNode = listHead;
        } else {
            listLastNode.setNext(new Node(value));
            listLastNode = listLastNode.next;
        }

        //Отслеживаем размер. и изменения.
        listSize++;
        modCount++;
    }

    /**
     * 2 тугрика
     * Метод должен вернуть число на соответствующем индексе.
     *
     * @param index - index of Node.
     */
    public int get(int index) {
        /*
         * Если индекс отрицательный или превышает значение размера коллекции, то выкидываем исключение.
         */
        if (index >= listSize || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        //Последовательно проходимся до необходимого звена с головы и возвращаем значение.
        Node currentNode = listHead;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
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
        /*
         * Проверка на валидность индекса. Выбрасываем исключени, если невалидный.
         */
        if (i > listSize || i < 0) {
            throw new IndexOutOfBoundsException("i");
        }
        /*
         * Создаем текущее звено, которое изначально является головным. Далее, если нужно добавить элемент на место
         * головного звена (i == 0), то создаем новое звено в "голове" и делаем следующим для него прошлую "голову".
         * Если нужно вставить элемент не в голову то ищем звено, стоящее перед необходимым и производим перетасовку
         * ссылок.
         */
        Node currentNode = listHead;
        if (i == 0) {
            listHead = new Node(value);
            listHead.next = currentNode;
        } else {

            // Поиск звена перед звеном с необходимым индексом.
            for (int j = 0; j < i - 1; j++) {
                currentNode = currentNode.next;
            }

            // Создаем новое звено, и внедряем его в нужное место, заменяя ссылки на звенья.
            Node newNode = new Node(value);
            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }

        // Отслеживание размера и изменений списка.
        listSize++;
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
        /*
         * Проверка на валидность индекса и на удаление пустого списка.
         */
        if (listSize == 0 || index >= listSize || index < 0) {
            throw new IndexOutOfBoundsException("i");
        }

        // Алгоритм удаления элементов.
        if (index == 0) {

            // Если удаляем голову, то переноси голову на следующее звено.
            listHead = listHead.next;
        } else {

            // Поиск звена перед звеном с необходимым индексом.
            Node beforeNode = listHead;
            for (int j = 0; j < index - 1; j++) {
                beforeNode = beforeNode.next;
            }

            // Если удаляется последнее звено, то заменяем также соответствующее поле класса.
            if (beforeNode.next == listLastNode) {
                listLastNode = beforeNode;
            }
            beforeNode.next = beforeNode.next.next;
        }

        // Отслеживание изменений и размера.
        listSize--;
        modCount++;
    }

    /**
     * 2 тугрика
     * Реализовать метод:
     * Переворачивает все элементы списка.
     * Пример:
     * Исходная последовательность списка "1 -> 2 -> 3 -> 4 -> null"
     * После исполнения метода последовательность должна быть такой "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {
        /*
         * Назначаем текущее и предыдущее звено (для алгоритма). tempNode -  для переопределеня головы и конца.
         */
        Node previousNode = null;
        Node currentNode = listHead;
        Node tempNode = listLastNode;
        listLastNode = listHead;

        // Пок не дойдет до конца списка заменяем ссылки, инвертируем список.
        while (currentNode != null) {
            Node nextNode = currentNode.next;
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        // Переопределяем голову.
        listHead = tempNode;
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
        /*
         * Начинаем преобразование с головы, далее проходя по всему списку конкатенируем предыдущую строчку.
         */
        Node currentNode = listHead;
        String totalMessage = "";
        while (currentNode != null) {
            totalMessage = totalMessage.concat(currentNode.value + " -> ");
            currentNode = currentNode.next;
        }

        // В случае для пустого массива выведется только null.
        return totalMessage.concat("null");
    }

    /**
     * 3 тугрика
     * Возвращает итератор, который умеет только итерироваться. БЕЗ удаления!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new LinkedListIterator();
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

    private class LinkedListIterator implements Iterator<Integer> {
        /*
         * Определим переменную фиксирования изменений, а также ссылку на node-ы, чтобы производить итерирование по
         * ссылкам. Можно было реализовывать через метод get, но тогда сложно была бы O(n), поэтому решил реализовать
         * именно так.
         */
        final int fixedModCount = modCount;
        Node node = listHead;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Integer next() {
            /*
             * Если есть изменения - выбрасываем исключение.
             */
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            // Если дальше нельзя итерироваться (включая случай пустого списка), то выбрасываем также исключение.
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            /*
             * Если все успешно - вытаскиваем значение из текущей node (далее его возвращаем), итерируемся к следующей
             * node.
             */
            int nodeValue = node.value;
            node = node.next;
            return nodeValue;
        }
    }
}
