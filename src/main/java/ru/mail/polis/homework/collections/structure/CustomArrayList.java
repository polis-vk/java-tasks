package ru.mail.polis.homework.collections.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private static final int INITIAL_CAPACITY = 10;
    private static final int GROW_FACTOR = 2;

    private E[] data;
    private int size = 0;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    public CustomArrayList(int initialCapacity) {
        data = (E[]) new Object[initialCapacity];
    }

    public CustomArrayList() {
        this(INITIAL_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        // TODO.
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO.
        return null;
    }

    @Override
    public Object[] toArray() {
        // TODO.
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO.
        return null;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO.
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO.
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // TODO.
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO.
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO.
        return false;
    }

    @Override
    public void clear() {
        // TODO.
    }

    @Override
    public E get(int index) {
        checkIndexBounds(index);
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndexBounds(index);
        modCount++;
        E result = get(index);

        data[index] = element;
        return result;
    }

    @Override
    public void add(int index, E element) {
        if (index != size) {
            checkIndexBounds(index);
        }
        modCount++;

        // Увеличиваем емкость, если больше нет места.
        if (size == data.length) {
            data = grow(data.length * GROW_FACTOR);
        }

        // Сдвигаем существующие элементы.
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndexBounds(index);
        modCount++;
        E result = data[index];

        data[index] = null;
        size--;
        System.arraycopy(data, index + 1, data, index, size - index);
        return result;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        // TODO.
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // TODO.
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // TODO.
        return null;
    }

    /**
     * Увеличить емкость содержащего элементы массива. Причем, если новое значение ёмкости будет меньше или равно
     * исходной - ничего не произойдет. Если же оно будет больше исходной - выделится новая память, куда переместятся
     * все элементы.
     * @param newCapacity - новое значение ёмкости коллекции.
     * @return Массив новой (или старой ёмкости), содержащий предыдущие элементы.
     */
    @SuppressWarnings("unchecked")
    private E[] grow(int newCapacity) {
        if (newCapacity <= data.length) {
            return data;
        }

        E[] newArray = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newArray, 0, size);
        return newArray;
    }

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " за пределами массива.");
        }
    }
}
