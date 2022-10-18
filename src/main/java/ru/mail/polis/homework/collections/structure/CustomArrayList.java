package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
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
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private static final String CONCURRENT_MODIFICATION = "It's prohibited to modify list while iterating!";
    private static final String NO_ELEMENT = "There is no such element left!";
    private static final String ILLEGAL_STATE = "It's not permitted to do this!";

    private final E[] data;
    private int size;
    private int modCount;

    public CustomArrayList() {
        data = (E[]) new Object[16];
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
        return new IteratorImpl(0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Object[] array;

        if (a.length < size) {
            array = new Object[size];
        } else {
            array = a;
        }

        System.arraycopy(data, 0, array, 0, size);

        return (T[]) array;
    }

    @Override
    public boolean add(E e) {
        if (size >= data.length) {
            expandArray();
        }
        data[size] = e;
        size++;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                remove(i);
                modCount++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return true;
        }

        for (Object o : c) {
            if (!contains(o)) {
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
        checkIndexInclusive(index);

        if (c.isEmpty()) {
            return false;
        }

        int fullSize = size + c.size();
        int startIndex = index + c.size();

        while (fullSize > data.length) {
            expandArray();
        }

        for (int i = index, j = startIndex; j < fullSize; i++, j++) {
            data[j] = data[i];
        }

        int i = index;

        for (E e : c) {
            data[i] = e;
            i++;
        }

        size = fullSize;
        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean hasChanged = false;

        for (int i = 0; i < size; ) {
            if (c.contains(data[i])) {
                hasChanged = true;
                remove(i);
                modCount++;
            } else {
                i++;
            }
        }

        return hasChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean hasChanged = false;

        for (int i = 0; i < size; ) {
            if (!c.contains(data[i])) {
                hasChanged = true;
                remove(i);
                modCount++;
            } else {
                i++;
            }
        }

        return hasChanged;
    }

    @Override
    public void clear() {
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        checkIndexExclusive(index);
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        E prev = defaultSet(index, element);
        modCount++;
        return prev;
    }

    // This is concurrent modification free set method
    public E defaultSet(int index, E element) {
        checkIndexExclusive(index);
        E prev = data[index];
        data[index] = element;
        return prev;
    }

    @Override
    public void add(int index, E element) {
        defaultAdd(index, element);
        modCount++;
    }

    // This is concurrent modification free add method
    public void defaultAdd(int index, E element) {
        checkIndexInclusive(index);

        if (size >= data.length) {
            expandArray();
        }

        for (int i = size; i > index; i--) {
            swap(i, i - 1);
        }

        data[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        E element = defaultRemove(index);
        modCount++;
        return element;
    }

    // This is concurrent modification free remove method
    public E defaultRemove(int index) {
        checkIndexExclusive(index);

        E element = data[index];

        for (int j = index + 1; j < size; j++) {
            swap(j, j - 1);
        }

        size--;

        return element;
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
        return new ListIteratorImpl(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkIndexInclusive(index);

        return new ListIteratorImpl(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndexExclusive(fromIndex);
        checkIndexInclusive(toIndex);

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("Start bound can't be greater than end bound!");
        }

        List<E> list = new ArrayList<>();

        for (int i = fromIndex; i < toIndex; i++) {
            list.add(data[i]);
        }

        return list;
    }

    private class IteratorImpl implements Iterator<E> {
        private final int fixedModCount = modCount;
        protected int cursor;

        public IteratorImpl(int startBound) {
            this.cursor = startBound;
        }

        @Override
        public boolean hasNext() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
            return cursor < size;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E element = data[cursor];
                cursor++;
                return element;
            }
            throw new NoSuchElementException(NO_ELEMENT);
        }
    }

    private class ListIteratorImpl extends IteratorImpl implements ListIterator<E> {

        private final static int MODIFICATION_INDICATOR = -1;

        private final int startBound;

        private final int fixedModCount = modCount;

        private int lastReturnedElementIndex;

        public ListIteratorImpl(int startBound) {
            super(startBound);
            this.startBound = startBound;
        }

        @Override
        public E next() {
            lastReturnedElementIndex = cursor;
            return super.next();
        }

        @Override
        public boolean hasPrevious() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
            return cursor > startBound;
        }

        @Override
        public E previous() {
            if (hasPrevious()) {
                cursor--;
                lastReturnedElementIndex = cursor;
                return data[cursor];
            }
            throw new NoSuchElementException(NO_ELEMENT);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastReturnedElementIndex == MODIFICATION_INDICATOR) {
                throw new IllegalStateException(ILLEGAL_STATE);
            }

            defaultRemove(lastReturnedElementIndex);
            cursor = lastReturnedElementIndex;
            lastReturnedElementIndex = MODIFICATION_INDICATOR;
        }

        @Override
        public void set(E e) {
            if (lastReturnedElementIndex == MODIFICATION_INDICATOR) {
                throw new IllegalStateException(ILLEGAL_STATE);
            }

            defaultSet(lastReturnedElementIndex, e);
            lastReturnedElementIndex = MODIFICATION_INDICATOR;
        }

        @Override
        public void add(E e) {
            defaultAdd(cursor, e);
            cursor++;
            lastReturnedElementIndex = MODIFICATION_INDICATOR;
        }
    }

    private void expandArray() {
        System.arraycopy(data, 0, data, 0, size * 2);
    }

    private void swap(int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void checkIndexExclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There is no element with index " + index + "!");
        }
    }

    private void checkIndexInclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("There is no element with index " + index + "!");
        }
    }
}
