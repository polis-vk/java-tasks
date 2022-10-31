package ru.mail.polis.homework.collections.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private static final int CAPACITY = 16;

    private E[] array;
    private int size;
    private int modCount;

    public CustomArrayList() {
        size = 0;
        array = (E[]) new Object[CAPACITY];
    }

    public CustomArrayList(E[] array) {
        this.array = array;
        this.size = array.length;
    }

    public CustomArrayList(Collection<E> collections) {
        array = (E[]) collections.toArray();
        size = array.length;
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
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object value : c) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAll(size, c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        checkIndex(index);

        if (size - array.length <= c.size()) {
            resizeSpecificCount(c.size());
        }
        System.arraycopy(array, index, array, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, array, index, c.size());
        size += c.size();
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                remove(i--);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retained = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i--);
                retained = true;
            }
        }
        return retained;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        checkInclusiveIndex(index);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        checkInclusiveIndex(index);
        E oldValue = array[index];
        array[index] = element;
        modCount++;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        if (array.length == size) {
            resize();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkInclusiveIndex(index);
        E value = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        modCount++;
        return value;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i] == o) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ArrayListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ArrayListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkInclusiveIndex(toIndex);
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        E[] newArray = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(array, fromIndex, newArray, 0, newArray.length);
        return new CustomArrayList<>(newArray);
    }

    private void resizeSpecificCount(int numberElements) {
        int length = array.length + numberElements;
        int capacity = length + (length / 2) + 1;
        E[] newArray = (E[])new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void resize() {
        resizeSpecificCount(0);
    }

    private void checkIndex(int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of range!");
        }
    }

    private void checkInclusiveIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of range!");
        }
    }

    private class ArrayListIterator implements ListIterator<E> {

        private int fixedModCount = modCount;
        private int position;

        public ArrayListIterator (int index) {
            position = index;
        }

        public ArrayListIterator () {
            this(0);
        }

        @Override
        public boolean hasNext() {
            checkModCount();
            return position < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[position++];
        }

        public boolean hasPrevious() {
            checkModCount();
            return position > 0;
        }

        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return array[--position];
        }

        public int nextIndex() {
            return position;
        }

        public int previousIndex() {
            return position - 1;
        }

        @Override
        public void remove() {
            checkModCount();
            CustomArrayList.this.remove(position);
            position--;
            fixedModCount = modCount;
        }

        @Override
        public void set(E e) {
            checkModCount();
            CustomArrayList.this.set(position, e);
            fixedModCount = modCount;
        }

        @Override
        public void add(E e) {
            checkModCount();
            CustomArrayList.this.add(position, e);
            position++;
            fixedModCount = modCount;
        }

        private void checkModCount() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
