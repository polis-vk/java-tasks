package ru.mail.polis.homework.objects;

import java.util.ArrayList;
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
    private int modCount = 0;
    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент

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
            private final int initialModCount = modCount;
            private int index = -1;

            @Override
            public boolean hasNext() {
                if (modCount != initialModCount) {
                    throw new ConcurrentModificationException();
                }
                return index != size() - 1;
            }

            @Override
            public Integer next() {
                if (modCount != initialModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(++index);
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
            private final int initialModCount = modCount;
            private int index = -1;

            @Override
            public boolean hasNext() {
                if (modCount != initialModCount) {
                    throw new ConcurrentModificationException();
                }
                return index + 2 < size();
            }

            @Override
            public Integer next() {
                if (modCount != initialModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                index += 2;
                return get(index);
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
            private final int initialModCount = modCount;
            private int index = -2;

            @Override
            public boolean hasNext() {
                if (modCount != initialModCount) {
                    throw new ConcurrentModificationException();
                }
                return index + 2 < size();

            }

            @Override
            public Integer next() {
                if (modCount != initialModCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                index += 2;
                return get(index);
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}
