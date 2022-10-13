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
        Iterator<Integer> iterator = Arrays.stream(array).iterator();
        return iterator;
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new ItrTypes("even");
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new ItrTypes("odd");
    }

    private class ItrTypes implements Iterator<Integer> {
        final int fixModCount = modCount;
        String typeIterator;
        public ItrTypes(String typeIterator) {
            this.typeIterator = typeIterator;
        }
        Iterator<Integer> iterator = Arrays.stream(array).iterator();
        boolean isOddIndex = true;

        @Override
        public boolean hasNext() {
            if (iterator.hasNext()) {
                if (typeIterator.equals("even")) {
                    iterator.next();
                }
                if (isOddIndex) {
                    isOddIndex = false;
                    return true;
                }
                if (typeIterator.equals("odd")) {
                    iterator.next();
                }
                return iterator.hasNext();
            }
            return false;
        }

        @Override
        public Integer next() {
            if (fixModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (position > size()) {
                throw new NoSuchElementException();
            }
            return iterator.next();
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

}
