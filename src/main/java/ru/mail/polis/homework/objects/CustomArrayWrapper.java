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
        return new MIterator(Mode.USUAL);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new MIterator(Mode.EVEN);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new MIterator(Mode.ODD);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    class MIterator implements Iterator<Integer> {

        Mode mode;
        int index = 0;
        int modificationCounter;

        public MIterator(Mode mode) {
            this.mode = mode;
            if (mode == Mode.EVEN) {
                index = 1;
            }
            modificationCounter = position;
        }

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public Integer next() {
            int res;
            if (modificationCounter != position){
                throw new ConcurrentModificationException();
            }
            switch (mode) {
                case ODD:
                case EVEN:
                    res = array[index];
                    index += 2;
                    return res;

                case USUAL:
                default:
                    res = array[index];
                    index++;
                    return res;
            }
        }
    }

    private enum Mode {
        EVEN,
        ODD,
        USUAL
    }

}