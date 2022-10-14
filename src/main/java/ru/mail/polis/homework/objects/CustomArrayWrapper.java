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
    private static final boolean EVEN_ITERATOR = true;
    private static final boolean ODD_ITERATOR = false;

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
        return new ArrayWrapperIterator();
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит только четные элементы.
     *
     * @return Iterator for EVEN elements
     */
    public Iterator<Integer> evenIterator() {
        return new ArrayWrapperIterator(EVEN_ITERATOR);
    }

    /**
     * Реализовать метод:
     * Возвращает итератор который проходит нечетные элементы
     *
     * @return Iterator for ODD elements
     */
    public Iterator<Integer> oddIterator() {
        return new ArrayWrapperIterator(ODD_ITERATOR);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    class ArrayWrapperIterator implements Iterator<Integer>{
        private static final int CLASSIC_STEP = 1;
        private static final int SPECIAL_STEP = 2;
        private static final int EVEN_POS = 1;
        private static final int ODD_POS = 0;
        private static final int CLASSIC_POS = 0;

        private int position;
        private final int fixedModCount;
        private final int step;

        ArrayWrapperIterator(){
            this.position = CLASSIC_POS;
            this.fixedModCount = modCount;
            this.step = CLASSIC_STEP;
        }

        ArrayWrapperIterator(boolean isEven){
            this.fixedModCount = modCount;
            this.step = SPECIAL_STEP;
            if(isEven){
                this.position = EVEN_POS;
            }
            else{
                this.position = ODD_POS;
            }

        }

        @Override
        public boolean hasNext() {
            return position < array.length;
        }

        @Override
        public Integer next() {
            if(fixedModCount != modCount){
                throw new ConcurrentModificationException();
            }
            position += step;

            return array[position - step];
        }
    }

}
