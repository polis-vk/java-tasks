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

    private static final int EVEN_ITERATOR_START_POSITION = 1;
    private static final int ODD_ITERATOR_START_POSITION  = 0;
    private static final int PARITY_KEEPING_STEP = 2;

    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    private int modCount;

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        modCount++;
        position++;
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
        return new ConfigurableIterator();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new ConfigurableIterator(EVEN_ITERATOR_START_POSITION, PARITY_KEEPING_STEP);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new ConfigurableIterator(ODD_ITERATOR_START_POSITION, PARITY_KEEPING_STEP);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class ConfigurableIterator implements Iterator<Integer> {
        static final int DEFAULT_POSITION = 0;
        static final int DEFAULT_STEP = 1;
        int iteratorPosition;
        final int fixedModCount = modCount;
        final int step;

        public ConfigurableIterator(int iteratorPosition, int step) {
            this.iteratorPosition = iteratorPosition;
            this.step = step;
        }

        public ConfigurableIterator() {
            this(DEFAULT_POSITION, DEFAULT_STEP);
        }

        @Override
        public boolean hasNext() {
            return iteratorPosition < array.length;
        }

        @Override
        public Integer next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (iteratorPosition >= size()) {
                throw new NoSuchElementException();
            }
            int prevPosition = iteratorPosition;
            iteratorPosition += step;
            return array[prevPosition];
        }
    }
}
