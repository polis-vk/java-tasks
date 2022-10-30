package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 15;
    private static final double GOLD_RATIO = 1.618D;

    private E[] data;
    private int size;
    private int modifications;

    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IndexOutOfBoundsException();
        }
        data = (E[]) new Object[capacity];
    }

    public CustomArrayList(Collection<? extends E> collection) {
        data = (E[]) collection.toArray();
        size = collection.size();
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
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomArrayListIterator();
    }

    private class CustomArrayListIterator implements Iterator<E> {
        private final int modificationsCounter = modifications;
        private int currentIteratorIndex;

        @Override
        public boolean hasNext() {
            return currentIteratorIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (modifications != modificationsCounter) {
                throw new ConcurrentModificationException();
            }
            return data[currentIteratorIndex++];
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        }
        System.arraycopy(data, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        checkNullPointerException(o);
        int position = indexOf(o);
        if (position == -1) {
            return false;
        }
        remove(position);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        int newCapacity = size + c.size();
        ensureCapacity(newCapacity);
        System.arraycopy(data, index, data, index + c.size(), size - index - 1);
        System.arraycopy((E[]) c.toArray(), 0, data, index, c.size());
        modifications++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object item : c) {
            checkNullPointerException(item);
        }
        boolean oneOrMoreElementsHaveBeenDeleted = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(data[i])) {
                oneOrMoreElementsHaveBeenDeleted = true;
                remove(i);
                i--;
            }
        }
        modifications += oneOrMoreElementsHaveBeenDeleted ? 1 : 0;
        return oneOrMoreElementsHaveBeenDeleted;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (Object item : c) {
            checkNullPointerException(item);
        }
        boolean oneOrMoreElementsHaveBeenRetained = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(data[i])) {
                oneOrMoreElementsHaveBeenRetained = true;
                remove(i);
                i--;
            }
        }
        modifications += oneOrMoreElementsHaveBeenRetained ? 1 : 0;
        return oneOrMoreElementsHaveBeenRetained;
    }

    @Override
    public void clear() {
        Arrays.fill(data, null);
        size = 0;
        modifications++;
    }

    @Override
    public E get(int index) {
        checkOutOfBoundException(index);
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        checkOutOfBoundException(index);
        checkNullPointerException(element);
        E previousElement = data[index];
        data[index] = element;
        modifications++;
        return previousElement;
    }

    @Override
    public void add(int index, E element) {
        checkOutOfBoundException(index);
        checkNullPointerException(element);
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index - 1);
        data[index] = element;
        modifications++;
        size++;
    }

    @Override
    public E remove(int index) {
        checkOutOfBoundException(index);
        E previousElement = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[size--] = null;
        modifications++;
        return previousElement;
    }

    @Override
    public int indexOf(Object o) {
        checkNullPointerException(o);
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        checkNullPointerException(o);
        int index = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(o)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkOutOfBoundException(index);
        return new CustomListIterator(index);
    }

    private class CustomListIterator implements ListIterator<E> {
        private final int startIndex;
        private int modificationsCounter = modifications;
        private int currentIndex;

        public CustomListIterator(int startIndex) {
            this.startIndex = startIndex;
            currentIndex = startIndex;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            checkModificationsCount();
            return data[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > startIndex;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            checkModificationsCount();
            return data[--currentIndex];
        }

        @Override
        public int nextIndex() {
            checkModificationsCount();
            if (currentIndex >= size) {
                return size;
            }
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            checkModificationsCount();
            if (currentIndex - 1 <= startIndex) {
                return -1;
            }
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            checkModificationsCount();
            CustomArrayList.this.remove(currentIndex);
            modificationsCounter = modifications;
        }

        @Override
        public void set(E e) {
            checkModificationsCount();
            CustomArrayList.this.set(currentIndex, e);
            modificationsCounter = modifications;
        }

        @Override
        public void add(E e) {
            checkModificationsCount();
            CustomArrayList.this.add(currentIndex, e);
            modificationsCounter = modifications;
        }

        private void checkModificationsCount() {
            if (modifications != modificationsCounter) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex < 0 || fromIndex > toIndex || fromIndex >= data.length || toIndex >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }
        int newCapacity = toIndex - fromIndex;
        E[] newList = (E[]) new Object[newCapacity];
        System.arraycopy(data, fromIndex, newList, 0, newCapacity);
        return new CustomArrayList<E>(Arrays.asList(newList));
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= data.length) {
            return;
        }
        int newCapacity = (int) (minCapacity * GOLD_RATIO);
        E[] newArray = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newArray, 0, size);
        data = newArray;
    }

    private void checkOutOfBoundException(int index) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkNullPointerException(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }
}
