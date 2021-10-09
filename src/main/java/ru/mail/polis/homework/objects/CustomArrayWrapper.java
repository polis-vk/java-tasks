package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Вам придется реализовать Iterable класс CustomArrayWrapper вместе с методами которые
 * могут возващать итераторы только по четным/нечетным позициям в массиве. Пример с классического
 * итератора можете взять из лекции. Обратите внимание что подсчет четного или нечетного элемента
 * идет с человеческой точки зрения.
 * Пример:
 * дан массив [100, 0 ,100, 0, 100]
 * тогда все элементы со значением 100 имеют нечетную позицию, а элементы = 0 - четную.
 */
public class CustomArrayWrapper implements Iterable<Integer> {

    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    int modCounter = 0;

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
        modCounter++;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
        modCounter++;
    }

    public int get(int index) {
        checkIndex(index);
        return array[index];
    }

    public int size() {
        return array.length;
    }

    /**
     * Реализовать метод:
     * Возврящает обычный итератор.
     *
     * @return default Iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iter();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new evenIter();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new oddIter();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Iter implements Iterator<Integer> {
        private int position = 0;
        private final int fixedModCounter = modCounter;

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (fixedModCounter != modCounter) {
                throw new ConcurrentModificationException();
            }
            if (position >= array.length) {
                throw new NoSuchElementException();
            }
            return array[position++];
        }
    }

    private class oddIter implements Iterator<Integer> {
        private int position = 0;
        private final int fixedModCounter = modCounter;

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (fixedModCounter != modCounter) {
                throw new ConcurrentModificationException();
            }
            if (position - 1 >= array.length) {
                throw new NoSuchElementException();
            }
            position += 2;
            return array[position - 2];
        }
    }

    private class evenIter implements Iterator<Integer> {
        private int position = 1;
        private final int fixedModCounter = modCounter;

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (fixedModCounter != modCounter) {
                throw new ConcurrentModificationException();
            }
            if (position - 1 >= array.length) {
                throw new NoSuchElementException();
            }
            position += 2;
            return array[position - 2];
        }
    }
}
