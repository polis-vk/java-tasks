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

    private static final int POSITION_FOR_DEFAULT_AND_ODD = 0;
    private static final int POSITION_FOR_EVEN= 1;
    private static final int STEP_FOR_DEFAULT = 1;
    private static final int STEP_FOR_EVEN_AND_ODD = 2;


    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    private int modificationCounter;    // количество изменений

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
        modificationCounter++;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
        modificationCounter++;
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
        return new SpecialIterator(POSITION_FOR_DEFAULT_AND_ODD, STEP_FOR_DEFAULT);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new SpecialIterator(POSITION_FOR_EVEN, STEP_FOR_EVEN_AND_ODD);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new SpecialIterator(POSITION_FOR_DEFAULT_AND_ODD, STEP_FOR_EVEN_AND_ODD);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class SpecialIterator implements Iterator<Integer> {
        private final int fixedModificationCounter = modificationCounter;
        int position;
        int step;

        public SpecialIterator(int position, int step) {
            this.position = position;
            this.step = step;
        }

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if (modificationCounter != fixedModificationCounter) {
                throw new ConcurrentModificationException();
            }
            int next = get(position);
            position += step;
            return next;
        }
    }
}
