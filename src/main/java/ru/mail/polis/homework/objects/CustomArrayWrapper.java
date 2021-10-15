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
    private final static int ODD_ITERATOR_START_POSITION = 0;
    private final static int EVEN_ITERATOR_START_POSITION = 1;
    private final static int PARITY_PRESERVING_STEP = 2;
    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент
    private int modCount;               // количество изменений (add/edit)

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
        return new CustomIterator();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new CustomIterator(EVEN_ITERATOR_START_POSITION, PARITY_PRESERVING_STEP);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new CustomIterator(ODD_ITERATOR_START_POSITION, PARITY_PRESERVING_STEP);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class CustomIterator implements Iterator<Integer> {
        static final int DEFAULT_START_POSITION = 0;
        static final int DEFAULT_STEP = 1;
        private int index;
        private final int expectedModCount = modCount;
        private final int step;

        CustomIterator(int startPosition, int step) {
            this.step = step;
            index = startPosition - step;
        }

        public CustomIterator() {
            this(DEFAULT_START_POSITION, DEFAULT_STEP);
        }

        public boolean hasNext() {
            return index + step < array.length;
        }

        public Integer next() {
            checkForModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            index += step;
            return array[index];
        }

        private void checkForModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
