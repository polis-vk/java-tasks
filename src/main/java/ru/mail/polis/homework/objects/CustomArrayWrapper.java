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

    private final int[] array;          // массив
    private int position = 0;           // следующая позиция куда будет вставлен элемент
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
     * Возвращает обычный итератор.
     *
     * @return default Iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int position;
            final int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return position < array.length;
            }

            @Override
            public Integer next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (position >= array.length) {
                    throw new NoSuchElementException();
                }

                return array[position++];
            }
        };
    }

    /**
     * Реализовать метод:
     * Возвращает итератор, который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new Iterator<Integer>() {
            int position = -1;
            final int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return position + 2 < array.length;
            }

            @Override
            public Integer next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (position >= array.length) {
                    throw new NoSuchElementException();
                }

                position += 2;

                return array[position];
            }
        };
    }

    /**
     * Реализовать метод:
     * Возвращает итератор, который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new Iterator<Integer>() {
            int position = -2;
            final int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return position + 2 < array.length;
            }

            @Override
            public Integer next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (position >= array.length) {
                    throw new NoSuchElementException();
                }

                position += 2;

                return array[position];
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}