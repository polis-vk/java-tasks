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
        return new Iterator<Integer>() {

            private int currentIndex = 0;
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return array.length > currentIndex;
            }

            @Override
            public Integer next() {
                if (cursor != currentIndex) {
                    throw new ConcurrentModificationException();
                }
                cursor++;
                return hasNext() ? array[currentIndex++] : null;
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
        return new Iterator<>() {
            private int currentIndex = 1;
            private boolean flag = true;
            private int cursor = 1;

            @Override
            public boolean hasNext() {
                return array.length > currentIndex + 2;
            }

            @Override
            public Integer next() {
                if (cursor != currentIndex) {
                    throw new ConcurrentModificationException();
                }
                if (flag) {
                    flag = false;
                    cursor+=2;
                    return hasNext() ? array[currentIndex] : null;
                }
                else {
                    cursor += 2;
                    currentIndex += 2;
                    return hasNext() ? array[currentIndex] : null;
                }
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
        return new Iterator<>() {
            private int currentIndex = 0;
            private boolean flag = true;
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return array.length > currentIndex + 2;
            }

            @Override
            public Integer next() {
                if (cursor != currentIndex) {
                    throw new ConcurrentModificationException();
                }
                if (flag) {
                    flag = false;
                    cursor++;
                    return hasNext() ? array[currentIndex] : null;
                }
                cursor++;
                return hasNext() ? array[currentIndex += 2] : null;
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }


}
