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
        return new CustomItr();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new CustomItr(CustomItr.EVEN_START_POS, CustomItr.DOUBLE_STEP);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new CustomItr(CustomItr.ODD_START_POS, CustomItr.DOUBLE_STEP);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    private class CustomItr implements Iterator<Integer> {
        // private для полей не прописывается, т.к.
        // все поля доступны только внутри класса CustomArrayWrapper
        int pos;
        int expectedModCount;
        final int step;
        final static int DEFAULT_START_POS = 0;
        final static int ODD_START_POS = 0;
        final static int EVEN_START_POS = 1;
        final static int DEFAULT_STEP = 1;
        final static int DOUBLE_STEP = 2;

        public CustomItr() {
            this(DEFAULT_START_POS, DEFAULT_STEP);
        }

        public CustomItr(int pos, int step) {
            this.pos = pos;
            this.expectedModCount = CustomArrayWrapper.this.position;
            this.step = step;
        }

        public boolean hasNext() {
            return this.pos < this.expectedModCount;
        }

        @Override
        public Integer next() {
            validate();
            Integer result = CustomArrayWrapper.this.array[this.pos];
            this.pos += this.step;
            return result;
        }

        private final void validate() {
            if (CustomArrayWrapper.this.position != this.expectedModCount)
                throw new ConcurrentModificationException();
        }

    }

}
