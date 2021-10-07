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
            private int index;
            private final int savedPosition = position;

            @Override
            public boolean hasNext() {
                if (savedPosition != position) {
                    throw new ConcurrentModificationException();
                }
                    return index < position;
            }

            @Override
            public Integer next() {
                if (savedPosition != position) {
                    throw new ConcurrentModificationException();
                }
                if (index > position) {
                    throw new IndexOutOfBoundsException();
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
            private final int savedPosition = position;
            private int indexPosition = 1;

            @Override
            public boolean hasNext() {
                if (savedPosition != position) {
                    throw new ConcurrentModificationException();
                }
                return indexPosition < position;
            }

            @Override
            public Integer next() {
                if (savedPosition != position) {
                    throw new ConcurrentModificationException();
                }
                if (indexPosition > position) {
                    throw new IndexOutOfBoundsException();
                }
                Integer temp = array[indexPosition];
                indexPosition += 2;
                return temp;
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
            private final int savedPosition = position;
            private int indexPosition = 0;

            @Override
            public boolean hasNext() {
                if (savedPosition != position) {
                    throw new ConcurrentModificationException();
                }
                return indexPosition < position;
            }

            @Override
            public Integer next() {
                if (savedPosition != position) {
                    throw new ConcurrentModificationException();
                }
                if (indexPosition > position) {
                    throw new IndexOutOfBoundsException();
                }
                Integer temp = array[indexPosition];
                indexPosition += 2;
                return temp;
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}
