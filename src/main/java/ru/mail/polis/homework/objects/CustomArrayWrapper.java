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
    private int position;              // следующая позиция куда будет вставлен элемент

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
        return new Iterator<Integer>() {

            int index = 0;
            int fixModCount = modCount;
            int current;
            @Override
            public boolean hasNext() {
               return index < size();
            }

            @Override
            public Integer next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }

                if (fixModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                current = array[index++];
                return current;
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
            int step  = 2;
            int pos = 1;
            int fixModCount = modCount;



            @Override
            public boolean hasNext() {
                return pos < size();
            }

            @Override
            public Integer next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }

                if (fixModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

               int value = array[pos];
               pos += step;
                return value;
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
            int step  = 2;
            int pos = 0;
             int fixModCount = modCount;

            @Override
            public boolean hasNext() {
               return pos < size();
            }

            @Override
            public Integer next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }

                if (fixModCount != modCount) {
                    throw new ConcurrentModificationException();
                }


                int value = array[pos];
                pos += step;
                return value;
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}
