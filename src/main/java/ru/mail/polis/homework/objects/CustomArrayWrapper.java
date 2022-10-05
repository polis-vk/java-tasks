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
        return new EvenIter();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new OddIter();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    class Iter implements Iterator<Integer> {
        int position;
        int fixedModCount = modCount;

        @Override
        public boolean hasNext() {
            return hasNext(position);
        }

        public boolean hasNext(int p) {
            return p < array.length;
        }

        @Override
        public Integer next() {
            return next(position);
        }

        public Integer next(int p) {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext(p)) {
                throw new NoSuchElementException();
            }
            int value = array[p];
            fixPosition();
            return value;
        }

        public void fixPosition() {
            position++;
        }
    }

    private class EvenIter extends Iter {
        int evenPosition = 1;

        @Override
        public Integer next() {
            return next(evenPosition);
        }

        @Override
        public boolean hasNext() {
            return hasNext(evenPosition);
        }

        @Override
        public void fixPosition() {
            evenPosition += 2;
        }

    }

    private class OddIter extends Iter {
        int oddPosition = 0;

        @Override
        public Integer next() {
            return next(oddPosition);
        }

        @Override
        public boolean hasNext() {
            return hasNext(oddPosition);
        }

        @Override
        public void fixPosition() {
            oddPosition += 2;
        }
    }
}
