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

    private static final int HONES_STARTING_INDEX = 1; // начальный индекс для прохода по чётным элементам
    private static final int DOUBLE_STEP = 2; // двойной шаг

    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    private int modCounter = 0;         // ведёт подсчёт операций

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
     * Возвращает обычный итератор.
     *
     * @return default Iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new CustomArrayWrapperIterator();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор, который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new CustomArrayWrapperIterator(HONES_STARTING_INDEX, DOUBLE_STEP);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор, который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new CustomArrayWrapperIterator(DOUBLE_STEP);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class CustomArrayWrapperIterator implements Iterator<Integer> {

        private final static int DEFAULT_INDEX = 0; // начальный индекс для создания стандартного итератора
        private final static int DEFAULT_STEP = 1; // шаг для создания стандартного итератора

        private final int step;
        // Фиксируем количество проведённых операций на момент вызова итератора
        private final int fixedModCounter = modCounter;
        private int currentIndex;

        public CustomArrayWrapperIterator(int currentIndex, int step) {
            this.currentIndex = currentIndex;
            this.step = step;
        }

        public CustomArrayWrapperIterator(int step) {
            this.currentIndex = DEFAULT_INDEX;
            this.step = step;
        }

        public CustomArrayWrapperIterator() {
            this.currentIndex = DEFAULT_INDEX;
            this.step = DEFAULT_STEP;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        @Override
        public Integer next() {
            if (fixedModCounter != modCounter) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int res = currentIndex;
            currentIndex += step;
            return array[res];
        }
    }

}
