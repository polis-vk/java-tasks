package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static final Object[] EMPTY_ARRAY = {};

    private E[] array;
    private int size;

    private int modCount;

    public CustomArrayList() {
        this(INITIAL_CAPACITY);
    }

    public CustomArrayList(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException();
        }
        this.array = (E[]) new Object[initialSize];
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
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length != size()) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size());
        return a;
    }

    @Override
    public boolean add(E e) {
        ensureCapacity(size + 1);
        array[size++] = e;
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
        for (Object element : c) {
            if (contains(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int newSize = a.length;
        ensureCapacity(size + newSize);
        System.arraycopy(a, 0, array, size, newSize);
        size += newSize;
        return newSize != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkRange(index);
        Object[] a = c.toArray();
        int newSize = a.length;
        ensureCapacity(size + newSize);
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(array, index, array, index + newSize, numMoved);
        }
        System.arraycopy(a, 0, array, index, newSize);
        size += newSize;
        return newSize != 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean flag = false;
        for (Object elem : c) {
            while (remove(elem)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean flag = false;
        for (int i = size() - 1; i >= 0; i--) {
            if (!c.contains(get(i))) {
                remove(i);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void clear() {
        modCount++;
        for (int i = 0; i < size(); i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        checkRange(index);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        checkRange(index);
        modCount++;
        array[index] = element;
        return array[index];
    }

    @Override
    public void add(int index, E element) {
        checkRange(index);
        ensureCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkRange(index);
        modCount++;
        E removedElement = array[index];
        System.arraycopy(array, index + 1, array, index, size() - index);
        size--;
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (o == null && array[i] == null || array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size() - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size() - 1; i >= 0; i--) {
                if (o.equals(array[i])) {
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
        checkRange(index);
        return new CustomListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkRange(fromIndex);
        checkRange(toIndex);
        List<E> subList = new CustomArrayList<>();
        for (int i = fromIndex; i <= toIndex; i++) {
            subList.add(get(i));
        }
        return subList;
    }

    public void ensureCapacity(int minCapacity) {
        int minExpand = (array != EMPTY_ARRAY) ? 0 : INITIAL_CAPACITY;
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private class CustomListIterator implements ListIterator<E> {
        private int index;
        private int lastIndex = -1;
        int expectedModCount = modCount;

        CustomListIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastIndex = index;
            index++;
            return array[lastIndex];
        }

        @Override
        public E previous() {
            checkForComodification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            int i = index - 1;
            index = i;
            lastIndex = i;
            return array[--i];
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
            checkForComodification();
            CustomArrayList.this.remove(lastIndex);
            index = lastIndex;
            lastIndex = -1;
            expectedModCount = modCount;
        }

        @Override
        public void set(E e) {
            checkForComodification();
            CustomArrayList.this.set(lastIndex, e);
            expectedModCount = modCount;
        }

        @Override
        public void add(E e) {
            checkForComodification();
            int i = index;
            CustomArrayList.this.add(i, e);
            index = i + 1;
            lastIndex = -1;
            expectedModCount = modCount;
        }

        private void checkForComodification() {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException();
        }
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        if (minCapacity - array.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int oldCapacity = array.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        array = Arrays.copyOf(array, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    private void checkRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
