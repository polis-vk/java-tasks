package ru.mail.polis.homework.objects;

import java.util.Arrays;
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

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
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
        // Суть initialArray в том, что он является копией изначального массива
        // Каждая проверка на изменение основного массива во время итерации будет происходить с помощью него
        return new Iterator<Integer>() {
            int index = 0;
            final int[] initialArray = Arrays.copyOf(array, array.length);

            @Override
            public boolean hasNext() {
                if (!Arrays.equals(array, initialArray)) {
                    throw new ConcurrentModificationException();
                }

                return index < initialArray.length;
            }

            @Override
            public Integer next() {
                if (!Arrays.equals(array, initialArray)) {
                    throw new ConcurrentModificationException();
                }

                if (index >= array.length) {
                    throw new NoSuchElementException();
                }

                return array[index++];
            }
        };
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new Iterator<Integer>() {
            int index = 1;
            final int[] initialArray = Arrays.copyOf(array, array.length);

            @Override
            public boolean hasNext() {
                if (!Arrays.equals(array, initialArray)) {
                    throw new ConcurrentModificationException();
                }

                return index < initialArray.length;
            }

            @Override
            public Integer next() {
                if (!Arrays.equals(array, initialArray)) {
                    throw new ConcurrentModificationException();
                }

                if (index >= array.length) {
                    throw new NoSuchElementException();
                }

                int result = array[index];
                index += 2;

                return result;
            }
        };
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new Iterator<Integer>() {
            int index = 0;
            final int[] initialArray = Arrays.copyOf(array, array.length);

            @Override
            public boolean hasNext() {
                if (!Arrays.equals(array, initialArray)) {
                    throw new ConcurrentModificationException();
                }

                return index < initialArray.length;
            }

            @Override
            public Integer next() {
                if (!Arrays.equals(array, initialArray)) {
                    throw new ConcurrentModificationException();
                }

                if (index >= array.length) {
                    throw new NoSuchElementException();
                }

                int result = array[index];
                index += 2;

                return result;
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}
