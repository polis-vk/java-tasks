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
    boolean iterating = false;
    private final int[] array;          // массив
    private int position = 0;               // следующая позиция куда будет вставлен элемент

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        if(!iterating){
            checkIndex(position);
            array[position] = value;
            position++;
        }else{
            throw new ConcurrentModificationException();
        }
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
        iterating = true;
        return new Iterator<Integer>() {
            int readPosition = 0;
            @Override
            public boolean hasNext() {
                return readPosition < size();
            }

            @Override
            public Integer next() {
                return get(readPosition++);
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
        iterating = true;
        return new Iterator<Integer>() {
            int readPosition = 1;
            @Override
            public boolean hasNext() {
                return readPosition < size();
            }

            @Override
            public Integer next() {
                int result = get(readPosition);
                readPosition += 2;
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
        iterating = true;
        return new Iterator<Integer>() {
            int readPosition = 0;
            @Override
            public boolean hasNext() {
                return readPosition < size();
            }

            @Override
            public Integer next() {
                int result = get(readPosition);
                readPosition += 2;
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
