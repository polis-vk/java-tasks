package ru.mail.polis.homework.objects;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

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

    public static final int REGULAR_STEP = 1;
    public static final int STEP_WITH_SAME_PARITY = 2;


    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    private int modCounter;

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
        modCounter = 0;
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
        return new ArrayWrapperIterator(0, REGULAR_STEP);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new ArrayWrapperIterator(1, STEP_WITH_SAME_PARITY);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new ArrayWrapperIterator(0, STEP_WITH_SAME_PARITY);
    }

    public class ArrayWrapperIterator implements Iterator<Integer> {

        private final int currentModificationCount;
        private int position;
        private int step;

        public ArrayWrapperIterator(int position, int step) {
            currentModificationCount = modCounter;
            this.position = position;
            this.step = step;
        }

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (currentModificationCount != modCounter) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new IndexOutOfBoundsException();
            }

            int res = array[position];
            position += step;
            return res;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}
