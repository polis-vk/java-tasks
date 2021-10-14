package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Вам придется реализовать Iterable класс CustomArrayWrapper вместе с методами которые
 * могут возвращать итераторы только по четным/нечетным позициям в массиве. Пример с классического
 * итератора можете взять из лекции. Обратите внимание, что подсчет четного или нечетного элемента
 * идет с человеческой точки зрения.
 * Пример:
 * дан массив [100, 0, 100, 0, 100]
 * тогда все элементы со значением 100 имеют нечетную позицию, а элементы = 0 - четную.
 */
public class CustomArrayWrapper implements Iterable<Integer> {

    public static final int FIRST_INDEX = 0;
    public static final int SECOND_INDEX = 1;
    public static final int SINGLE_STEP = 1;
    public static final int DOUBLE_STEP = 2;

    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    private int modCount;

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
     * [x] Реализовать метод:
     * Возвращает обычный итератор.
     *
     * @return default Iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new SubSequenceIterator();
    }

    /**
     * [x] Реализовать метод:
     * Возвращает итератор, который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new SubSequenceIterator(Parity.EVEN);
    }

    /**
     * [x] Реализовать метод:
     * Возвращает итератор, который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new SubSequenceIterator(Parity.ODD);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private enum Parity {
        ODD,
        EVEN,
    }

    private class SubSequenceIterator implements Iterator<Integer> {
        private final int step;
        private int position;
        private final int fixedModCount = modCount;

        public SubSequenceIterator(int begin, int step) {
            this.position = begin;
            this.step = step;
        }

        public SubSequenceIterator() {
            this(FIRST_INDEX, SINGLE_STEP);
        }

        public SubSequenceIterator(Parity parity) {
            this(parity == Parity.ODD ? FIRST_INDEX : SECOND_INDEX, DOUBLE_STEP);
        }

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException(
                        CustomArrayWrapper.this
                                + " has been changed "
                                + (modCount - fixedModCount)
                                + "time(s) between iterations"
                );
            }
            if (position >= array.length) {
                throw new NoSuchElementException();
            }
            return array[(position += step) - step];
        }
    }
}
