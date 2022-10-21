package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private int size;
    private int modCount;
    private Object[] array = {};

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(0) > 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator<E> implements Iterator<E> {
        private int pos;
        private int fixedModCount;

        @Override
        public boolean hasNext() {
            return pos < size();
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            if (pos == 0) {
                fixedModCount = modCount;
            }
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            } else if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (E) array[pos++];
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size());
    }

    @Override
    public <T> T[] toArray(T[] a) {
//        Object newArray = new Object[]{};
//        System.arraycopy(newArray, 0, a, 0, size);
//        return newArray;
        return null;
    }

    @Override
    public boolean add(E e) {
        if (array.length >= size()) {
            array = Arrays.copyOf(array, (array.length + 1) * 2);
        }
        array[size + 1] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int index = 0;
        for (Object elem: c) {
            if (index >= size()) {
                return false;
            }
            while (index <= size() && !elem.equals(array[index])) {
                index++;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        array = new Object[]{};
        size = 0;
        modCount = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index <= size()) {
            return (E) array[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
        System.arraycopy(array, 0, array, index + 1, size());
        array[index] = element;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(0)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(0)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
