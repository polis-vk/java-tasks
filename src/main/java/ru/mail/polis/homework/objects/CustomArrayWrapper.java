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

    private final int[] array;
    private int position = 0;
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
        return new DefaultIterator();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new EvenIterator();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new OddIterator();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }


    private class DefaultIterator implements Iterator<Integer> {
        /*
         * Дефолтный класс наследует два класса - итератор по четным и нечетным элементам. Введена iterationStep -
         * значение шага при инкременте индекса в next(). Алгоритм построен так, что все три класса вызывают через свои
         * конструкторы по умолчанию конструктор с параметрами класса DefaultIterator с параметрами - iterationStep -
         * шаг итерации по индексам, startPosition - стартовый индекс (для дефолтного и по нечетным - 0, по четным  - 1)
         */
        final int fixedModCount = modCount;
        int position;
        int iterationStep;

        // Поместил конструкторы рядом - исправлено.
        public DefaultIterator() {
            this(1, 0);
        }

        protected DefaultIterator(int iterationStep, int startPosition) {
            this.iterationStep = iterationStep;
            position = startPosition;
        }

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

            int arrValue = array[position];
            position += iterationStep;
            return arrValue;
        }


    }

    private class EvenIterator extends DefaultIterator {
        public EvenIterator() {
            super(2, 1);
        }
    }

    private class OddIterator extends DefaultIterator {
        public OddIterator() {
            super(2, 0);
        }
    }
}
