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
 * <p>
 * Задание оценивается в 10 тугриков
 */
@SuppressWarnings("unchecked")
public class CustomArrayList<E> implements List<E> {
    private static final int INIT_SIZE = 16;

    private int size;
    private int modCount;
    private E[] array;

    public CustomArrayList() {
        size = 0;
        array = (E[]) new Object[INIT_SIZE];
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
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] resArray = new Object[size];
        System.arraycopy(array, 0, resArray, 0, size);
        return resArray;
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
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAll(size, c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int colSize = c.size();
        ensureCapacity(colSize - 1);
        checkIndex(index, true);
        size += colSize;
        if (size - (index + colSize) < 0) {
            return false;
        }
        System.arraycopy(array, index + colSize - colSize, array, index + colSize, size - (index + colSize));
        for (E element : c) {
            array[index++] = element;
        }
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean wasRemoved = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                remove(i--);
                wasRemoved = true;
            }
        }
        return wasRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean wasRemoved = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i--);
                wasRemoved = true;
            }
        }
        return wasRemoved;
    }

    @Override
    public void clear() {
        size = 0;
        modCount++;
        Arrays.fill(array, null);
    }

    @Override
    public E get(int index) {
        checkIndex(index, false);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index, false);
        E prevValue = array[index];
        array[index] = element;
        modCount++;
        return prevValue;
    }

    @Override
    public void add(int index, E element) {
        ensureCapacity(0);
        checkIndex(index, true);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index, false);
        modCount++;
        E element = array[index];
        System.arraycopy(array, index + 1, array, index, size - (index + 1));
        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
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
        return new CustomListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex >= toIndex) {
            throw new IllegalArgumentException();
        }
        checkIndex(fromIndex, false);
        checkIndex(toIndex, true);
        List<E> list = new CustomArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            list.add(array[i]);
        }
        return list;
    }

    private class CustomIterator implements Iterator<E> {
        protected int prevModCount = modCount;
        protected int currentIndex;

        public CustomIterator() {
            this.currentIndex = 0;
        }

        public CustomIterator(int startIndex) {
            this.currentIndex = startIndex;
        }

        @Override
        public boolean hasNext() {
            checkModCount();
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[currentIndex++];
        }

        protected void checkModCount() {
            if (prevModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class CustomListIterator extends CustomIterator implements ListIterator<E> {
        private final int startIndex;

        public CustomListIterator() {
            super(0);
            this.startIndex = 0;
        }

        public CustomListIterator(int startIndex) {
            super(startIndex);
            this.startIndex = startIndex;
        }

        @Override
        public boolean hasPrevious() {
            checkModCount();
            return currentIndex > startIndex;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return array[--currentIndex];
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            checkModCount();
            CustomArrayList.this.remove(currentIndex);
            prevModCount = modCount;
        }

        @Override
        public void set(E e) {
            checkModCount();
            CustomArrayList.this.set(currentIndex, e);
            prevModCount = modCount;
        }

        @Override
        public void add(E e) {
            checkModCount();
            CustomArrayList.this.add(currentIndex++, e);
            prevModCount = modCount;
        }
    }

    private void ensureCapacity(int index) {
        while (array.length - size <= index) {
            array = Arrays.copyOf(array, (array.length * 3 / 2) + 1);
        }
    }

    private void checkIndex(int index, boolean inclusive) {
        if (!(0 <= index && index < size + (inclusive ? 1 : 0))) {
            throw new IndexOutOfBoundsException();
        }
    }
}
