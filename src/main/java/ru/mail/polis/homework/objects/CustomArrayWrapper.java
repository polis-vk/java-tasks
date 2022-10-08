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
    private boolean isChanged = false;  // флаг для вызова ConcurrentModificationException

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
        isChanged = true;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
        isChanged = true;
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
        isChanged = false;
        return new Iterator<Integer>() {

            int next = 0;

            @Override
            public boolean hasNext() {
                return (next < size());
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                else if (isChanged) {
                    throw new ConcurrentModificationException();
                }
                else {
                    Integer ans = array[next];
                    next++;
                    return ans;
                }
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
        isChanged = false;
        return new Iterator<Integer>() {

            int next = 1;

            @Override
            public boolean hasNext() {
                return (next < size());
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                else if (isChanged) {
                    throw new ConcurrentModificationException();
                }
                else {
                    Integer ans = array[next];
                    next = next + 2;
                    return ans;
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
        isChanged = false;
        return new Iterator<Integer>() {

            int next = 0;

            @Override
            public boolean hasNext() {
                return (next < size());
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                else if (isChanged) {
                    throw new ConcurrentModificationException();
                }
                else {
                    Integer ans = array[next];
                    next = next + 2;
                    return ans;
                }
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}
