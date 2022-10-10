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
    private static final int STARTING_POSITION_FOR_EVEN_NUMBERS = 1;
    private static final int EVEN_AND_ODD_STEP = 2;
    private static final int DEFAULT_VALUE_FOR_COUNTER_OF_MODIFICATIONS = 0;

    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    private int counterOfModifications;

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
        counterOfModifications = DEFAULT_VALUE_FOR_COUNTER_OF_MODIFICATIONS;
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
        counterOfModifications++;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
        counterOfModifications++;
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
        return new CustomArrayWrapperIterator();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new CustomArrayWrapperIterator(STARTING_POSITION_FOR_EVEN_NUMBERS, EVEN_AND_ODD_STEP);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new CustomArrayWrapperIterator(EVEN_AND_ODD_STEP);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class CustomArrayWrapperIterator implements Iterator<Integer> {
        private static final int DEFAULT_STEP_OF_ITERATOR = 1;
        private static final int DEFAULT_STARTING_POSITION = 0;

        private int currentPosition;
        private final int step;
        private final int numberOfModifications;

        public CustomArrayWrapperIterator() {
            this(DEFAULT_STARTING_POSITION, DEFAULT_STEP_OF_ITERATOR);
        }

        public CustomArrayWrapperIterator(int step) {
            this(DEFAULT_STARTING_POSITION, step);
        }

        public CustomArrayWrapperIterator(int startPosition, int step) {
            currentPosition = startPosition;
            this.step = step;
            numberOfModifications = counterOfModifications;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < array.length;
        }

        @Override
        public Integer next() {
            if (numberOfModifications != counterOfModifications) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Integer value = array[currentPosition];
            currentPosition += step;
            return value;
        }
    }
}
