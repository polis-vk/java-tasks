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
    private int modCount = 0;

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
        modCount++;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
        modCount++;
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
        return new GeneralIter(1);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new GeneralIter(0);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class Iter implements Iterator<Integer> {
        int position = 0;
        int mc = modCount;

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (mc != modCount) {
                throw new ConcurrentModificationException();
            }
            if (position >= array.length) {
                throw new NoSuchElementException();
            }
            int elem = array[position];
            position += 1;
            return elem;
        }
    }

    //я хотела оба итератора совместить в один, но не знаю, как это сделать...
    private class GeneralIter implements Iterator<Integer> {
        int position;
        int mc = modCount;

        public GeneralIter(int position) {
            this.position = position;
        }

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (mc != modCount) {
                throw new ConcurrentModificationException();
            }
            if (position >= array.length) {
                throw new NoSuchElementException();
            }
            int elem = array[position];
            position += 2;
            return elem;
        }
    }
}
