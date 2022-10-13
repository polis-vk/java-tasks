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

    private int countOfIter;

    public CustomArrayWrapper(int size) {
        this.array = new int[size];
    }

    public void add(int value) {
        checkIndex(position);
        array[position] = value;
        position++;
        countOfIter++;
    }

    public void edit(int index, int value) {
        checkIndex(index);
        array[index] = value;
        countOfIter++;
    }

    public int get(int index) {
        checkIndex(index);
        return array[index];
    }

    public int getCountOfIter() {
        return countOfIter;
    }

    public int size() {
        return array.length;
    }

    enum Types {
        DEFAULT,
        EVEN,
        ODD
    }

    public class ArrayIterator implements Iterator<Integer> {
        private final int[] elementsArr;
        private int currentIndex;
        private final int indexSteps;

        private final Types type;

        private final int countOfIter = getCountOfIter();


        public ArrayIterator(int[] elementsArr, int currentIndex, int indexSteps, Types type) {
            this.elementsArr = elementsArr;
            this.currentIndex = currentIndex;
            this.indexSteps = indexSteps;
            this.type = type;
        }

        public boolean hasNext() {
            return currentIndex < array.length;
        }

        public Integer next() {
            if (countOfIter != getCountOfIter()) {
                throw new ConcurrentModificationException();
            } else {
                if (type == Types.DEFAULT) {
                    if (hasNext()) {
                        return elementsArr[currentIndex++];
                    }
                }
                if (type == Types.EVEN) {
                    if (hasNext()) {
                        int element = elementsArr[currentIndex];
                        currentIndex = currentIndex + indexSteps;
                        return element;
                    }
                }
                if (type == Types.ODD) {
                    if (hasNext()) {
                        int element = elementsArr[currentIndex];
                        currentIndex = currentIndex + indexSteps;
                        return element;
                    }
                }
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * Реализовать метод:
     * Возврящает обычный итератор.
     *
     * @return default Iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new ArrayIterator(array, 0, 1, Types.DEFAULT);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new ArrayIterator(array, 1, 2, Types.EVEN);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new ArrayIterator(array, 0, 2, Types.ODD);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }
}
