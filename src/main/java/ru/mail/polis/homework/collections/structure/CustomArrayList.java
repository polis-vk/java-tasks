package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private final static int DEFAULT_SIZE = 16;
    private int modCount;
    private int maxCapacity = DEFAULT_SIZE;
    private int size;
    private E[] array;

    public CustomArrayList() {
        array = (E[]) new Object[DEFAULT_SIZE];
    }

    public CustomArrayList(E[] array) {
        this.array = array;
        size = array.length;
    }

    public CustomArrayList(Collection<E> collection) {
        array = (E[]) collection.toArray();
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
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final int iteratorModCount = modCount;
            private int index;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (iteratorModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
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
        resize(1);
        array[size++] = e;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            array[--size] = null;
            modCount++;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
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
        resize(c.size());
        if (index != size) {
            System.arraycopy(array, index, array, index + c.size(), size - index);
        }
        System.arraycopy(c.toArray(), 0, array, index, c.size());
        size += c.size();
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                remove(i--);
                isChanged = true;
            }
        }
        modCount++;
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChanged = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i--);
                isChanged = true;
            }
        }
        modCount++;
        return isChanged;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        modCount++;
        size = 0;

    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldElement = array[index];
        array[index] = element;
        modCount++;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        resize(1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E element = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        modCount++;
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
        } else {
            for (int i = 0; i < size; i++) {
                if (array[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        E[] subList = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(array, fromIndex, subList, 0, toIndex - fromIndex);
        return Arrays.asList(subList);
    }

    private class CustomListIterator implements ListIterator<E> {

        private int iteratorModCount = modCount;
        private int index;

        public CustomListIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            checkModCount();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[index++];
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            checkModCount();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return array[--index];
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            checkModCount();
            CustomArrayList.this.remove(index);
            iteratorModCount = modCount;
        }

        @Override
        public void set(E e) {
            checkModCount();
            CustomArrayList.this.set(index, e);
            iteratorModCount = modCount;
        }

        @Override
        public void add(E e) {
            checkModCount();
            CustomArrayList.this.add(index++, e);
            iteratorModCount = modCount;
        }

        private void checkModCount() {
            if (iteratorModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void resize(int addableSize) {
        if (size + addableSize >= maxCapacity) {
            maxCapacity = (size + addableSize) * 2;
            Object[] newArray = new Object[maxCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = (E[]) newArray;
        }
    }

}
