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

    private static final int START_FOR_ODD_NUMBERS = 0;
    private static final int START_FOR_EVEN_NUMBERS = 1;
    private static final int STEP_BETWEEN_ODD_OR_EVEN_NUMBERS = 2;
    private final int[] array;          // массив
    private int position;               // следующая позиция куда будет вставлен элемент

    private int modCount;

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

            private int index = 0;
            private int fixedModCount = modCount;
            @Override
            public boolean hasNext() {
                return index != size();
            }

            @Override
            public Integer next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                } else if (hasNext()) {
                    return array[index++];
                } else{
                    throw new NoSuchElementException();
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
        return new Iterator<Integer>() {

            private int index = START_FOR_EVEN_NUMBERS;
            private int fixedModCount = modCount;
            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public Integer next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                } else if (hasNext()) {
                    int value = array[index];
                    index += STEP_BETWEEN_ODD_OR_EVEN_NUMBERS;
                    return value;
                } else{
                    throw new NoSuchElementException();
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
        return new Iterator<Integer>() {

            private int index = START_FOR_ODD_NUMBERS;
            private int fixedModCount = modCount;
            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public Integer next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                } else if (hasNext()) {
                    int value = array[index];
                    index += 2;
                    return value;
                } else{
                    throw new NoSuchElementException();
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
